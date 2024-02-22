package br.com.bradesco.kit.bff.monitoring;

import br.com.bradesco.kit.bff.config.HealthCheckEndpointConfig;
import br.com.bradesco.kit.bff.exception.InfrastructureException;
import br.com.bradesco.kit.bff.model.health.HealthCheckServDepend;
import br.com.bradesco.kit.bff.model.health.HealthCheckServDependGeral;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.AbstractReactiveHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.Status;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * OPCIONAL:
 * -Pode ser removido caso entenda que nao seja necessário
 * -Lembrar de excluir também o mapeamento no arquivo application.yml - management.endpoint.health.group.readiness.include
 * <p>
 * NOTA:
 * Por default, o HealthCheck do Spring Actuator já está ativo
 * <p>
 * HealthCheck personalizado específico para obter saúde/health APENAS dos serviços dependentes que possam retornar
 * UP/DOWN. Conforme novos serviços dependentes sejam necessários, basta apenas atualizar o mapeamento no arquivo
 * application.yml.
 */
@Component("servicosDependentesHealthIndicator")
@ConditionalOnProperty(prefix = "management.endpoint.health.group.readiness", name = "include", havingValue = "servicosDependentesHealthIndicator")
public class ServicosDependHealthIndicator extends AbstractReactiveHealthIndicator {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServicosDependHealthIndicator.class);

    @Autowired
    private HealthCheckEndpointConfig healthCheckEndpointConfig;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    protected Mono<Health> doHealthCheck(Health.Builder builder) {
        HealthCheckServDependGeral servicosDependentes = verificaSaudeServicosDependentes();
        if (!servicosDependentes.isTodosServicosUP()) {
            return Mono.just(Health.down().withDetails(Map.of("statusServicosDependentes", servicosDependentes)).build());
        }
        return Mono.just(Health.up().build());
    }

    /**
     * Responsavel por requisitar endpoint /health de servicos dependentes e obter status
     *
     * @return Pair<Boolean, Map < String, String>>
     * boolean indicando se todos os serviços estão UP,
     * Map devolve status UP/DOWN e o host
     */
    private HealthCheckServDependGeral verificaSaudeServicosDependentes() {
        List<HealthCheckServDepend> listaHealthCheckServDepend = null;
        Boolean flagTodosServicosUp = false;

        if (!CollectionUtils.isEmpty(this.healthCheckEndpointConfig.getListaHealthCheckCustom())) {

            // Cria um CompletableFuture para cada URL a ser requisitada e inicia cada request de forma assíncrona
            List<CompletableFuture<ResponseEntity<HealthCheckServDepend>>> futures =
                    this.healthCheckEndpointConfig.getListaHealthCheckCustom().stream()
                            .map(servicoDependenteComHealthCheck -> CompletableFuture.supplyAsync(() ->
                                    requisitaEndpoint(servicoDependenteComHealthCheck.getUrlEndpontHealth())))
                            .collect(Collectors.toList());

            //Aguarda por todos os CompletableFutures para finalizar e então retorna
            var stream =
                    futures.stream().map(responseEntityCompletableFuture -> responseEntityCompletableFuture.exceptionally(throwable -> {
                        throw new InfrastructureException("HEALTH999", "Houve um erro ao tentar requisitar um dos " +
                                "servicos", throwable);
                    }).join());

            flagTodosServicosUp =
                    stream.allMatch(healthResponseEntity ->
                            Objects.requireNonNull(healthResponseEntity.getBody()).getHealth().getStatus().equals(Status.UP));

            listaHealthCheckServDepend = stream.map(HttpEntity::getBody).collect(Collectors.toList());
        }

        return new HealthCheckServDependGeral(listaHealthCheckServDepend, flagTodosServicosUp);

    }

    private ResponseEntity<HealthCheckServDepend> requisitaEndpoint(String urlEndpontHealth) {
        ResponseEntity<HealthCheckServDepend> response = null;
        try {
            var req = restTemplate.getForEntity(urlEndpontHealth, Health.class);
            response = new ResponseEntity<>(new HealthCheckServDepend(urlEndpontHealth, req.getBody()), req.getStatusCode());
        } catch (Exception ex) {
            LOGGER.error("Erro ao tentar requisitar endpoint /health da URL {}", urlEndpontHealth, ex);
            throw ex;
        }
        return response;
    }

}
package br.com.bradesco.kit.bff.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;


/**
 * Terá carregamento condicional se APENAS houver mapeamento no arquivo application-
 * <ambiente>.yml com a chave 'health-check-endpoint-custom'.
 */
@Configuration
@ConfigurationProperties(prefix = "health-check-endpoint-custom")
@ConditionalOnProperty(prefix = "health-check-endpoint-custom", name = "url-endpoint-health")
public class HealthCheckEndpointConfig {

    private List<UrlHealthCheckEndpoint> listaHealthCheckCustom;

    public List<UrlHealthCheckEndpoint> getListaHealthCheckCustom() {
        return listaHealthCheckCustom;
    }

    /**
     * Terá carregamento condicional se APENAS houver mapeamento no arquivo application-
     * <ambiente>.yml com as chaves 'health-check-endpoint-custom' e 'url-endpoint-health'.
     */
    @Configuration
    @ConfigurationProperties(prefix = "health-check-endpoint-custom")
    @ConditionalOnProperty(prefix = "health-check-endpoint-custom", name = "url-endpoint-health")
    public class UrlHealthCheckEndpoint {

        @Value("${url-endpoint-health}")
        private String urlEndpontHealth;

        public String getUrlEndpontHealth() {
            return urlEndpontHealth;
        }
    }
}

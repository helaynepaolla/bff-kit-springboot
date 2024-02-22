package br.com.bradesco.kit.bff.controller;

import br.com.bradesco.enge.logcloud.api.AuditLoggerFactory;
import br.com.bradesco.enge.logcloud.api.SrvCanalLoggerFactory;
import br.com.bradesco.enge.logcloud.audit.AuditLog;
import br.com.bradesco.enge.logcloud.audit.AuditLogger;
import br.com.bradesco.enge.logcloud.canal.ReturnCode;
import br.com.bradesco.enge.logcloud.canal.SrvCanalLog;
import br.com.bradesco.enge.logcloud.canal.SrvCanalLogger;
import br.com.bradesco.kit.bff.exception.BusinessException;
import br.com.bradesco.kit.bff.exception.InfrastructureException;
import br.com.bradesco.kit.bff.webclient.ExemploInterface;
import feign.FeignException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.event.Level;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api
@RestController
@RequestMapping("/v1")
public class AppController {

    public static final String MSG_ERRO = "HTTP Status code: {} - Response Code: {} - Response Msg: {}";

    //Objeto especifico para realizar log de negocio
    private static final SrvCanalLogger SRV_CANAL_LOGGER = SrvCanalLoggerFactory.getLogger(AppController.class);

    //Objeto especifico para realizar log de auditoria
    private static final AuditLogger AUDIT_LOGGER = AuditLoggerFactory.getLogger(AppController.class);
    private static final Logger LOGGER = LoggerFactory.getLogger(AppController.class);

    @Autowired
    private ExemploInterface srvInterface;

    @ApiOperation(httpMethod = "GET", value = "Sample request to SRV", produces = "application/json")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Ok")})
    @GetMapping(value = "/sample-client-req", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> reqToSrv() {

        final String responseMsg;
        final Integer responseCode;
        final String restResponse;

        SrvCanalLog srvCanalLog = null;

        //Saida para arquivo no POD - Realize o log do que eh ESSENCIAL e que APOIE DE FATO
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("Calling SRV endpoint");
        }

        try {
            restResponse = srvInterface.getClient().toString();

            //Exemplo de log de negócio - Normalmente gerado APENAS no artefato SRV - microservice
            //Deve ser gerado com o retorno especifico: SUCESSO, ERRO_NEGOCIO, etc.
            //Gerado com base no documento Especificacao de Log - Saida para o arquivo 'srvcanal.log'
            srvCanalLog = SrvCanalLog
                    .builder(ReturnCode.SUCESSO)
                    .entrada("Objeto representando Input")
                    .saida("Objeto representando Output")
                    .codigoTransacao("nomeDaTransacao OU codigoTransacaoDefinido")
                    .addMensagem("ENGE0001", "Primeira mensagem")
                    .addMensagem("EGNE0002", "Segunda mensagem")
                    .build();

            //Exemplo de log de auditoria
            //Deve ser gerado com o level especifico: INFO, ERROR, etc.
            //Saida para o arquivo 'audit.log'
            String mensagem = "Informacao util para servir como auditoria";
            AUDIT_LOGGER.log(
                    AuditLog
                            .builder()
                            .level(Level.INFO)
                            .withDescription(mensagem)
                            .build());

        } catch (FeignException ex) {
            switch (ex.status()) {
                case 500 -> {
                    responseCode = 1234;
                    responseMsg = "Infra error during request to SRV";
                    LOGGER.error(MSG_ERRO, ex.status(), responseCode, responseMsg, ex);
                    throw new InfrastructureException(responseCode, responseMsg);
                }
                case 204 -> {
                    responseCode = 4321;
                    responseMsg = "Business error during request to SRV";
                    LOGGER.error(MSG_ERRO, ex.status(), responseCode, responseMsg, ex);
                    throw new BusinessException(responseCode, responseMsg);
                }
                default -> {
                    responseCode = 4567;
                    responseMsg = "Infra error during request to SRV";
                    LOGGER.error(MSG_ERRO, ex.status(), responseCode, responseMsg, ex);
                    throw new InfrastructureException(responseCode, responseMsg);
                }
            }
        } finally {
            SRV_CANAL_LOGGER.log(srvCanalLog);
        }
        return new ResponseEntity<>(restResponse, HttpStatus.OK);
    }

    @ApiOperation(httpMethod = "GET", value = "Request GET que retorna com falha de infra", produces = "application/json")
    @GetMapping(value = "/sample-error-call", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> generateSampleInfraErr() {

        //Considerar padrão de log conforme exemplificado em generateSampleResponse()
        //Saida para arquivo no POD
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("Processing infra error excepction");
        }

        final String errCode = "ERR1234";
        final String errMsg = "Infrastructure test error";

        LOGGER.error(errCode + ": " + errMsg + " during the request.");
        throw new InfrastructureException(errCode, errMsg);
    }


    @ApiOperation(httpMethod = "GET", value = "Request GET que retorna com falha de negocio", produces = "application/json")
    @GetMapping(value = "/sample-business-error-call", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> generateSampleBusinessErr() {
        //Considerar padrão de log conforme exemplificado em generateSampleResponse()
        //Saida para arquivo no POD
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("Processing business error exception");
        }

        final String errCode = "ERR4321";
        final String errMsg = "Business test error";

        LOGGER.error(errCode + ": " + errMsg + " during the request.");
        throw new BusinessException(errCode, errMsg);
    }
}

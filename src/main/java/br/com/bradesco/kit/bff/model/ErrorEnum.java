package br.com.bradesco.kit.bff.model;

import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.EnumSet.allOf;
import static org.springframework.http.HttpStatus.*;

public enum ErrorEnum {
    ERRO_400(BAD_REQUEST, BAD_REQUEST.getReasonPhrase(), "400", "Requisição Invalida"),
    ERRO_422(UNPROCESSABLE_ENTITY, UNPROCESSABLE_ENTITY.getReasonPhrase(), "422", "Mensagem exemplo."),
    ERRO_500(INTERNAL_SERVER_ERROR, INTERNAL_SERVER_ERROR.getReasonPhrase(), "500", "Ops."),
    ERRO_503(SERVICE_UNAVAILABLE, SERVICE_UNAVAILABLE.getReasonPhrase(), "503", "Serviço Indisponível.");

    private static final Map<HttpStatus, ErrorEnum> LOOKUP_CODIGO_RETORNO = new HashMap<>();
    private static final Map<String, ErrorEnum> LOOKUP_CODIGO_ERRO = new HashMap<>();

    static {
        for (ErrorEnum e : allOf(ErrorEnum.class)) {
            LOOKUP_CODIGO_RETORNO.put(e.getCodigoRetorno(), e);
        }
    }

    static {
        for (ErrorEnum e : allOf(ErrorEnum.class)) {
            if (!StringUtils.hasLength(e.codigoErro)) {
                LOOKUP_CODIGO_ERRO.put(e.codigoErro, e);
            }
        }
    }

    private final HttpStatus codigoRetorno;
    private final String mensagem;
    private final String codigoErro;
    private final String mensagemErroDetalhada;

    ErrorEnum(HttpStatus codigoRetorno, String mensagem, String codigoErro, String mensagemErroDetalhada) {
        this.codigoRetorno = codigoRetorno;
        this.mensagem = mensagem;
        this.codigoErro = codigoErro;
        this.mensagemErroDetalhada = mensagemErroDetalhada;
    }


    public static List<ErrorEnum> getByCodigoRetorno(HttpStatus codigoRetorno) {
        return LOOKUP_CODIGO_RETORNO.values().stream().filter(m -> m.getCodigoRetorno() == codigoRetorno).collect(Collectors.toList());
    }

    public static ErrorEnum getByCodigoErro(String codigoErro) {
        return LOOKUP_CODIGO_ERRO.get(codigoErro);
    }

    public HttpStatus getCodigoRetorno() {
        return codigoRetorno;
    }

    public String getMensagem() {
        return mensagem;
    }

    public String getCodigoErro() {
        return codigoErro;
    }

    public String getMensagemErroDetalhada() {
        return mensagemErroDetalhada;
    }
}

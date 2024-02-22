package br.com.bradesco.kit.bff.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class DetalheErro implements Serializable {

    private static final long serialVersionUID = -4805736366208636653L;

    private final String codigoErro;
    @JsonProperty("mensagem")
    private final String mensagemErroDetalhada;
    private String campo;

    public DetalheErro(String codigoErro, String mensagemErroDetalhada) {
        super();
        this.codigoErro = codigoErro;
        this.mensagemErroDetalhada = mensagemErroDetalhada;
    }

    public DetalheErro(ErrorEnum errorEnum, String campo) {
        super();
        this.codigoErro = errorEnum.getCodigoErro();
        this.mensagemErroDetalhada = errorEnum.getMensagemErroDetalhada();
        this.campo = campo;
    }

    public String getCodigoErro() {
        return codigoErro;
    }

    public String getCampo() {
        return campo;
    }

    public String getMensagemErroDetalhada() {
        return mensagemErroDetalhada;
    }
}

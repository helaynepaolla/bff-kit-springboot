package br.com.bradesco.kit.bff.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

import static java.lang.String.valueOf;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErroBffResponse implements Serializable {

    private static final long serialVersionUID = 1345010900881624726L;

    private final String codigoRetorno;
    private final String mensagem;

    @JsonProperty("erros")
    private List<DetalheErro> listaErros;

    @JsonIgnore
    private ErrorEnum errorEnum;

    public ErroBffResponse(String codigoRetorno, String mensagem, List<DetalheErro> listaErros) {
        super();
        this.codigoRetorno = codigoRetorno;
        this.mensagem = mensagem;
        this.listaErros = listaErros;
    }

    public ErroBffResponse(ErrorEnum errorEnum) {
        super();
        this.codigoRetorno = valueOf(errorEnum.getCodigoRetorno().value());
        this.mensagem = errorEnum.getMensagem();
        this.errorEnum = errorEnum;
    }

    public ErroBffResponse(ErrorEnum errorEnum, List<DetalheErro> listaErros) {
        super();
        this.codigoRetorno = valueOf(errorEnum.getCodigoRetorno().value());
        this.mensagem = errorEnum.getMensagem();
        this.errorEnum = errorEnum;
        this.listaErros = listaErros;
    }

    public String getCodigoRetorno() {
        return codigoRetorno;
    }

    public String getMensagem() {
        return mensagem;
    }

    public List<DetalheErro> getListaErros() {
        return listaErros;
    }

    public ErrorEnum getErrorEnum() {
        return errorEnum;
    }
}

package br.com.bradesco.kit.bff.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "titulo",
        "autor",
        "editora",
        "valor"
})
public class LivrariaDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonProperty("id")
    private Integer idLivro;
    @JsonProperty("titulo")
    private String tituloLivro;
    @JsonProperty("autor")
    private String autor;
    @JsonProperty("editora")
    private String editora;
    @JsonProperty("valor")
    private float valorLivro;

    public LivrariaDTO() {
    }

    public LivrariaDTO(final Integer idLivro, final String tituloLivro, final String autor, final String editora, final float valorLivro) {
        this.idLivro = idLivro;
        this.tituloLivro = tituloLivro;
        this.autor = autor;
        this.editora = editora;
        this.valorLivro = valorLivro;
    }

    public Integer getIdLivro() {
        return this.idLivro;
    }

    public void setIdLivro(Integer idLivro) {
        this.idLivro = idLivro;
    }

    public String getTituloLivro() {
        return this.tituloLivro;
    }

    public void setTituloLivro(String tituloLivro) {
        this.tituloLivro = tituloLivro;
    }

    public String getAutor() {
        return this.autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getEditora() {
        return this.editora;
    }

    public void setEditora(String editora) {
        this.editora = editora;
    }

    public float getValorLivro() {
        return this.valorLivro;
    }

    public void setValorLivro(float valorLivro) {
        this.valorLivro = valorLivro;
    }

}
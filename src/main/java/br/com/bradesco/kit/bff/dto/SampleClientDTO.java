package br.com.bradesco.kit.bff.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "name",
        "location",
        "id"
})
public class SampleClientDTO implements Serializable {

    @JsonIgnore
    private final Map<String, Object> additionalProperties = new HashMap<>();
    @JsonProperty("name")
    private String name;
    @JsonProperty("location")
    private String location;
    @JsonProperty("id")
    private String id;

    /**
     * No args constructor for use in serialization
     */
    public SampleClientDTO() {
    }

    /**
     * Construtor
     *
     * @param name     nome do cliente
     * @param location localização do cliente
     * @param id       id do cliente
     */
    public SampleClientDTO(String name, String location, String id) {
        super();
        this.name = name;
        this.location = location;
        this.id = id;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("location")
    public String getLocation() {
        return location;
    }

    @JsonProperty("location")
    public void setLocation(String location) {
        this.location = location;
    }

    @JsonProperty("id")
    public String getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append('{');
        sb.append("name");
        sb.append(':');
        sb.append(((this.name == null) ? "<null>" : this.name));
        sb.append(',');
        sb.append("location");
        sb.append(':');
        sb.append(((this.location == null) ? "<null>" : this.location));
        sb.append(',');
        sb.append("id");
        sb.append(':');
        sb.append(((this.id == null) ? "<null>" : this.id));
        sb.append(',');
        if (sb.charAt((sb.length() - 1)) == ',') {
            sb.setCharAt((sb.length() - 1), '}');
        } else {
            sb.append('}');
        }
        return sb.toString();
    }

}
package edu.bell.restclient.restclient.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * Request DTO организации
 */
@Data
public class OrganisationDtoRequest {
    /**
     * Название организации
     */
    @NotBlank
    @JsonProperty
    String name;
    /**
     * ИНН
     */
    @JsonProperty
    Integer inn;
    /**
     * Поле isActive
     */
    @JsonProperty
    Boolean isActive;
}

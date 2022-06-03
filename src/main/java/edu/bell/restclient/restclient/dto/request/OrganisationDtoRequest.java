package edu.bell.restclient.restclient.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * Request DTO организации
 */
@Schema(name = "OrganisationDtoRequest",
        description = "объект с параметрами для поиска организации по нескольким параметрам",
        required = true
)
@Data
public class OrganisationDtoRequest {
    /**
     * Название организации
     */
    @Schema(description = "Название", required = true, example = "СБЕР")
    @NotBlank
    @JsonProperty
    String name;
    /**
     * ИНН
     */
    @Schema(description = "ИНН", example = "456123787")
    @JsonProperty
    Integer inn;
    /**
     * Поле isActive
     */
    @Schema(description = "Активность", example = "true")
    @JsonProperty
    Boolean isActive;
}

package edu.bell.restclient.restclient.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;

/**
 * Request DTO для фильтрации офисов
 */
@Schema(description = "Объект OfficeInListDto позволяет искать офис по нескольким параметрам ")
@Data
public class OfficeInListDto {
    /**
     * Id организации
     */
    @Schema(description = "Уникальный идентификатор организации,к которой относится офис"
            , required = true
            , example = "102"
            , minimum = "1")
    @NotNull
    @Range(min = 1, message = "id не может равняться 0")
    private Integer orgId;
    /**
     * Название офиса
     */
    @Schema(description = "Название офиса", example = "Офис Сбера1")
    private String name;
    /**
     * Телефон офиса
     */
    @Schema(description = "Номер телефона", example = "8937987456123")
    private String phone;
    /**
     * Поле isActive
     */
    @Schema(description = "Активность офиса", example = "true")
    private Boolean isActive;
}

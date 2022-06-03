package edu.bell.restclient.restclient.dto.request;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;

/**
 * Request DTO для сохранение нового офиса
 */
@Schema(description = "Запрос ,который приходит для сохранения нового офиса")
@Data
public class OfficeInSaveDto {
    /**
     * Id организации
     */
    @Schema(description = "Уникальный идентификатор организации", required = true, example = "102")
    @NotNull
    @Range(min = 1, message = "id не может равняться 0")
    private Integer orgId;
    /**
     * Название офиса
     */
    @Schema(description = "Название офиса", example = "Новый офис Сбера")
    private String name;
    /**
     * Телефон офиса
     */
    @Schema(description = "номер телефона офиса", example = "2-57-05")
    private String phone;
    /**
     * Адрес офиса
     */
    @Schema(description = "Адрес нового офиса", example = "город улица номер дома")
    private String address;
    /**
     * Поле isActive
     */
    @Schema(description = "Активность офиса", example = "true")
    private Boolean isActive;
}

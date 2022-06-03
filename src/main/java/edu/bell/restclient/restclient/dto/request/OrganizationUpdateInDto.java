package edu.bell.restclient.restclient.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * Request для обновления организации
 */
@Schema(name = "OrganizationUpdateInDto", description = "Объект с параметрами для обновления организации")
@Data
public class OrganizationUpdateInDto {
    /**
     * id организации
     */
    @Schema(description = "Уникальный идентификатор", required = true, minimum = "1", example = "101")
    @NotNull
    @Range(min = 1, message = "id не может равняться 0")
    private Integer id;
    /**
     * Название организации
     */
    @Schema(description = "Название организации", required = true, example = "BELL")
    @NotBlank(message = "Название - обязательное поле")
    private String name;
    /**
     * Полное название организации
     */
    @Schema(description = "Полное название организации", required = true, example = "BELL INTEGRATOR")
    @NotBlank
    private String fullName;
    /**
     * ИНН
     */
    @Schema(description = "ИНН", required = true, example = "123456789")
    @NotNull
    private Integer inn;
    /**
     * КПП
     */
    @Schema(description = "КПП", required = true, example = "987654")
    @NotNull
    private Integer kpp;
    /**
     * Адрес организации
     */
    @Schema(description = "Адрес", required = true, example = "Адрес")
    @NotBlank
    private String address;
    /**
     * Телефон
     */
    @Schema(description = "Номер телефона", example = "9-876-54")
    private String phone;
    /**
     * Поле isActive
     */
    @Schema(description = "Активность организации", required = true, example = "true")
    @NotNull(message = "по умолчанию true")
    private Boolean isActive;
}

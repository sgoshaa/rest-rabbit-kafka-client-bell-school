package edu.bell.restclient.restclient.dto.request;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Request DTO для сохранения новой организации
 */
@Schema(name = "OrganizationSaveInDto",
        required = true,
        description = "Объект с параметрами для сохранения новой организации"
)
@Data
public class OrganizationSaveInDto implements Serializable {
    /**
     * Название организации
     */
    @Schema(description = "Название организации", required = true, example = "Новая организация")
    @NotBlank(message = "Название - обязательное поле")
    private String name;
    /**
     * Полное название организации
     */
    @Schema(description = "Полное название ораганизации", required = true, example = "Полное название новой организации")
    @NotBlank
    private String fullName;
    /**
     * ИНН
     */
    @Schema(description = "ИНН", required = true, example = "88888888")
    @NotNull
    private Integer inn;
    /**
     * КПП
     */
    @Schema(description = "КПП", required = true, example = "55555555")
    @NotNull
    private Integer kpp;
    /**
     * Адрес организации
     */
    @Schema(description = "Адрес", required = true, example = "город улица номер дома")
    @NotBlank
    private String address;
    /**
     * номер телеофна организации
     */
    @Schema(description = "Номер телефона", example = "0-00-00")
    private String phone;
    /**
     * Поле isActive
     */
    @Schema(description = "Активность", example = "true")
    private Boolean isActive;
}

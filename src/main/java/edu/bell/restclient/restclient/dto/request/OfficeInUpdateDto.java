package edu.bell.restclient.restclient.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * Request DTO для обновления офиса
 */
@Data
@Schema(description = "Объект для обновления офиса", required = true)
public class OfficeInUpdateDto {
    /**
     * id офиса
     */
    @Schema(description = "Уникальный идентификатор", example = "101", minimum = "1", required = true)
    @NotNull
    @Range(min = 1, message = "id не может равняться 0")
    private Integer id;
    /**
     * Название офиса
     */
    @Schema(description = "Название офиса", example = "Новое название", required = true)
    @NotBlank
    private String name;
    /**
     * Адрес офиса
     */
    @Schema(description = "Новый адрес", example = "новый город новая улица новый номер дома", required = true)
    @NotBlank
    private String address;
    /**
     * Телефон офиса
     */
    @Schema(description = "Новый номер телефона", example = "2-55-06")
    private String phone;
    /**
     * Поле isActive
     */
    @Schema(description = "обновленный статус активности", example = "true")
    private Boolean isActive;
}

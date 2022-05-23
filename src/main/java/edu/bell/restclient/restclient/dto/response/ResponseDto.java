package edu.bell.restclient.restclient.dto.response;

import lombok.Data;

/**
 * Response DTO обертка для всех ответов
 */
@Data
public class ResponseDto {
    /**
     * Поле с данными
     */
    private Object data;
}

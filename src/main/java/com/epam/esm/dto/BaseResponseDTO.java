package com.epam.esm.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"status", "message", "data"})
public class BaseResponseDTO<T> {

    private int status;
    private String message;
    private T data;

    public BaseResponseDTO(int status, String responseMessage) {
        this.status = status;
        this.message = responseMessage;
    }
}
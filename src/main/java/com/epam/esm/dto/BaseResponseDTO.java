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
    /**
     status type:
     -1 -> operation failed
     0 -> operation done successfully, but no data found
     1 -> operation done successfully
     */
    private String message;
    private T data;

    public BaseResponseDTO(int status, String responseMessage) {
        this.status = status;
        this.message = responseMessage;
    }
}
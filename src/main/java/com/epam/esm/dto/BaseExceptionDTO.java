package com.epam.esm.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BaseExceptionDTO{
    private Integer HttpStatus;
    private String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer errorCode;

    public BaseExceptionDTO(Integer status, String message) {
        this.HttpStatus = status;
        this.message = message;
    }
    public BaseExceptionDTO(String message){
        this.message=message;
    }
}
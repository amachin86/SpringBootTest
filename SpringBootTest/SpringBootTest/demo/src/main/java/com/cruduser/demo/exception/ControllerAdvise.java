/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cruduser.demo.exception;

import com.cruduser.demo.dto.ErrorDTO;
import com.cruduser.demo.dto.response.APIResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestControllerAdvice
public class ControllerAdvise {
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public APIResponse<?> handleMethodArgumentException(MethodArgumentNotValidException exception){
        APIResponse<?> serviceResponse = new APIResponse<>();
        List<ErrorDTO> errors = new ArrayList<>();
        exception.getBindingResult().getFieldErrors()
                .forEach( error -> {
                    ErrorDTO errorDTO = ErrorDTO.builder()
                            .campo(error.getField())
                            .message(error.getDefaultMessage())
                            .build();
                });
        serviceResponse.setStatus("FAILED");
        serviceResponse.setErrors(errors);

           return serviceResponse;
    }

    @ExceptionHandler(value = RuntimeException.class)
    public APIResponse<?> handleServiceException(RuntimeException exception){
        APIResponse<?> serviceResponse = new APIResponse<>();
                    ErrorDTO errorDTO = ErrorDTO.builder()
                            .campo("")
                            .message(exception.getMessage())
                            .build();

        serviceResponse.setStatus("FAILED");
        serviceResponse.setErrors(Collections.singletonList(errorDTO));
        return serviceResponse;
    }

}

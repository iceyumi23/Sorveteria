package com.example.backend.controller;

import com.example.backend.dto.ErroResponse;
import com.example.backend.exception.SaborInvalidoException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/** Traduz exceções em respostas HTTP claras para o front. */
@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(SaborInvalidoException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErroResponse saborInvalido(SaborInvalidoException e) {
        return new ErroResponse(e.getMessage());
    }
}

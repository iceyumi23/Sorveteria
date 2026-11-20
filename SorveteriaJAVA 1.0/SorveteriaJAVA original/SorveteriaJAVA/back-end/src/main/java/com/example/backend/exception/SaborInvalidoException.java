package com.example.backend.exception;

/** Lançada quando o front envia um sabor que não existe no cardápio. */
public class SaborInvalidoException extends RuntimeException {

    public SaborInvalidoException(String id) {
        super("Sabor inválido: " + id);
    }
}

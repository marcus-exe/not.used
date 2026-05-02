package com.fiap.br.exception;
public class EntidadeNaoEcontradaException extends Exception{
    public EntidadeNaoEcontradaException() {
    }
    public EntidadeNaoEcontradaException(String message) {
        super(message);
    }
}
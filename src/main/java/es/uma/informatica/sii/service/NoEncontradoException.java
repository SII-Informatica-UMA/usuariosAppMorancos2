package es.uma.informatica.sii.service;

public class NoEncontradoException extends RuntimeException {


    public NoEncontradoException() {}

    public NoEncontradoException(String s) {
        super(s);
    }
}
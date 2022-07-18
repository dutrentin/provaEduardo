package br.com.poc.exception;

public class ParametroNotFoundedException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public ParametroNotFoundedException(String mensagem) {
        super(mensagem);
    }

    public ParametroNotFoundedException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}
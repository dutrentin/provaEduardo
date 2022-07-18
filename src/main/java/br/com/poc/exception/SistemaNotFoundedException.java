package br.com.poc.exception;

public class SistemaNotFoundedException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public SistemaNotFoundedException(String mensagem) {
        super(mensagem);
    }

    public SistemaNotFoundedException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}

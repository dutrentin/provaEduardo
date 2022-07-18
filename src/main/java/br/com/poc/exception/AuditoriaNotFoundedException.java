package br.com.poc.exception;

public class AuditoriaNotFoundedException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public AuditoriaNotFoundedException(String mensagem) {
        super(mensagem);
    }

    public AuditoriaNotFoundedException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}
package br.com.poc.exception;

public class ClienteNotFoundedException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public ClienteNotFoundedException(String mensagem) {
        super(mensagem);
    }

    public ClienteNotFoundedException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}

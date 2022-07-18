package br.com.poc.exception;

public class ParametroValorNotFoundedException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public ParametroValorNotFoundedException(String mensagem) {
        super(mensagem);
    }

    public ParametroValorNotFoundedException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}

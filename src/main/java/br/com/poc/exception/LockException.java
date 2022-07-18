package br.com.poc.exception;

public class LockException extends RuntimeException {
    private static final long serialVersionUID = 3536129351253163680L;
    private String mensagem;

    public LockException(String mensagem) {
        super(mensagem);
        this.mensagem = mensagem;
    }

    public LockException(Throwable erro) {
        super(erro);
        if (erro.getMessage() != null && !erro.getMessage().isEmpty()) {
            this.mensagem = erro.getMessage();
        }

    }

    public LockException(String mensagemErro, Throwable erro) {
        super(mensagemErro, erro);
        this.mensagem = mensagemErro;
    }

    public String getMessage() {
        return this.mensagem;
    }
}

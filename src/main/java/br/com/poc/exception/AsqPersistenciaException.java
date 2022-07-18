package br.com.poc.exception;

import javax.print.attribute.standard.Severity;

public class AsqPersistenciaException extends RuntimeException {
    private static final long serialVersionUID = -5619573724801759930L;
    private String codigo;
    private String mensagem;
    private Severity severidade = null;

    public AsqPersistenciaException(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getCodigo() {
        return this.codigo;
    }

    public String getMensagem() {
        return this.mensagem;
    }

    public Severity getSeveridade() {
        return this.severidade;
    }

    public void setCodigo(final String codigo) {
        this.codigo = codigo;
    }

    public void setMensagem(final String mensagem) {
        this.mensagem = mensagem;
    }

    public void setSeveridade(final Severity severidade) {
        this.severidade = severidade;
    }

    public AsqPersistenciaException(final String codigo, final String mensagem, final Severity severidade) {
        this.codigo = codigo;
        this.mensagem = mensagem;
        this.severidade = severidade;
    }

    public AsqPersistenciaException() {
    }
}

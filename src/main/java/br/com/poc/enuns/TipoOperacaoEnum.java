package br.com.poc.enuns;

public enum TipoOperacaoEnum {

    SALVAR("salvar"),
    ALTERAR("alterar"),
    EXCLUIR("excluir");

    private String 	nome;


    TipoOperacaoEnum(String nome){
        this.nome 	= nome;
    }


    public String getNome() {
        return nome;
    }

}

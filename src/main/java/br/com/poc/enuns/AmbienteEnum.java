package br.com.poc.enuns;

public enum AmbienteEnum {
	
	DESENVOLVIMENTO("localhost"),
	HOMOLOGACAO("hmg"),
	PRODUCAO("producao");
	
	private String 	nome;
	
	
	AmbienteEnum(String nome){
		
		this.nome 	= nome;
	}


	public String getNome() {
		return nome;
	}

}

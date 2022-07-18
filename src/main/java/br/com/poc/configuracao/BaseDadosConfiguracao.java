package br.com.poc.configuracao;

import java.net.InetAddress;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import br.com.poc.enuns.AmbienteEnum;


@Configuration
@Profile("BaseDados")
public class BaseDadosConfiguracao {
	
	@Bean
	public DataSource conexaoBancoDados() {
		
		DriverManagerDataSource  	conexao 		= new DriverManagerDataSource();
		
		String 						nomeAmbiente 	= InetAddress.getLoopbackAddress().getHostName();
		//String 					ipAmbiente 		= InetAddress.getLoopbackAddress().getHostAddress();
		
			
		if(AmbienteEnum.DESENVOLVIMENTO.getNome().equalsIgnoreCase(nomeAmbiente)) {
			
			conexao.setUrl("jdbc:h2:file:~/provaeduardodb");
			conexao.setUsername("sa");
			conexao.setPassword("");
			
			
		}else if(AmbienteEnum.HOMOLOGACAO.getNome().equalsIgnoreCase(nomeAmbiente)) {
			
			conexao.setUrl("");
			conexao.setUsername("");
			conexao.setPassword("");
			conexao.setDriverClassName("");
			
		}else {
			
			conexao.setUrl("");
			conexao.setUsername("");
			conexao.setPassword("");
			conexao.setDriverClassName("");
			
		}
	
		return conexao;
	}
	
	

}

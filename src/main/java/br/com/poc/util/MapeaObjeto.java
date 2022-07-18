package br.com.poc.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class MapeaObjeto {
	
	public static String transformaParaJson(Object classe)throws JsonProcessingException{
		return new ObjectMapper().writer().withDefaultPrettyPrinter().writeValueAsString(classe); 
	}

}

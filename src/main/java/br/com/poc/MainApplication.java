package br.com.poc;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
public class MainApplication {

	@Bean(name = "flushBase")
    public String flushBase() {
        return new String();
    }
	
  
	public static void main(String[] args) {
		
		SpringApplication.run(MainApplication.class, args);
	}

}

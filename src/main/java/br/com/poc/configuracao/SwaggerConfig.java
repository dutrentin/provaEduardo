package br.com.poc.configuracao;

import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	
	/**
	 * Método responsável por configurar
	 * a documentação e 
	 * 
	 * @return {@link Docket}
	 */
	
	@Bean
    public Docket configurarAPI() {
        return new Docket(DocumentationType.SWAGGER_2)
          .select()
          .apis(RequestHandlerSelectors.basePackage("br.com.asqprototipoapi"))
          .paths(PathSelectors.regex("/sistema.*"))
          .build()
          .apiInfo(consultarInformacaoApi());
          //.securitySchemes(Arrays.asList(new ApiKey("Token Access", HttpHeaders.AUTHORIZATION, In.HEADER.name())))
          //.securityContexts(Arrays.asList(securityContext()));
    }
	
	private ApiInfo consultarInformacaoApi() {
	    return new ApiInfoBuilder()
	            .title("API REST da ASQ")
	            .description("Proposta de arquitetura para API REST da ASQ")
	            .version("1.0.0")
	            .contact(new Contact("Rodrigo Fortes", "", "rodrigo.candido@asq.com.br"))
	            .build();
	}
	
		
	@SuppressWarnings("unused")
	private SecurityContext securityContext() {
	    return SecurityContext.builder()
	    		.securityReferences(defaultAuth())
	    		.forPaths(PathSelectors.ant("/sistema/**"))
	    		.build();
	}

	List<SecurityReference> defaultAuth() {
	    AuthorizationScope 		authorizationScope  = new AuthorizationScope("ADMIN", "accessEverything");
	    AuthorizationScope[] 	authorizationScopes = new AuthorizationScope[1];
	    						authorizationScopes[0] = authorizationScope;
	    						
	    return Arrays.asList(new SecurityReference("Token para acesso", authorizationScopes));
	}
}

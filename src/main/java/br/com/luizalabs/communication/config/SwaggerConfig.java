package br.com.luizalabs.communication.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
          .select()
          .apis(RequestHandlerSelectors.basePackage("br.com.luizalabs.communication.controller"))
          .paths(PathSelectors.any())
          .build()
          .useDefaultResponseMessages(false)
          .apiInfo(apiInfo());
    }
    
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Desafio Luizalabs")
                .description("Luizalabs' Communication Scheduler API")
                .version("1.0.0")
                .license("GNU GENERAL PUBLIC LICENSE Version 3")
                .licenseUrl("https://www.gnu.org/licenses/gpl-3.0")
                .contact(new Contact("Luís Amaral", "https://github.com/luisamaral12", "lgaa2006@gmail.com"))
                .build();
    }
}

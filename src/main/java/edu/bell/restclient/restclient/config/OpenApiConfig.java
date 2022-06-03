package edu.bell.restclient.restclient.config;


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(
                        new Info()
                                .title("Rest client система api")
                                .version("1.0.0")
                                .contact(new Contact()
                                        .email("sgoshaa@yandex.ru")
                                        .name("Игорь")
                                )
                );
    }
}

package by.jenka.service.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("City List API")
                        .description("Spring City List enterprise application")
                        .contact(new Contact().name("Yauhen K").url("https://www.linkedin.com/in/yauhen-kuzmich/"))
                        .version("v1")
                        .license(new License().name("BSD")));
    }
}

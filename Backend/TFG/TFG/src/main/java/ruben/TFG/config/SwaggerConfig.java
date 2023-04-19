package ruben.TFG.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Collections;

/**
 * Swagger paths
 *
 * HTML: http://localhost:8080/swagger-ui/
 * JSON: http://localhost:8080/v2/api-docs
 */
@Configuration
public class SwaggerConfig {

    /**
     * Configuration of the Swagger API
     */
    @Bean
    public Docket api(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiDetails())
                .select()
                .apis(RequestHandlerSelectors.any())
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();
    }

    /**
     * Configuration of the API details
     */
    private ApiInfo apiDetails(){
        return new ApiInfo("Sport Management API",
                "Documentation of the API REST that allows to manage the sports event",
                "1.0-SNAPSHOT",
                "http://www.google.com",
                new Contact("Backend Team", "https://github.com/rubenminero/TFG", "ruben.minero@edu.uah.es"),
                "UAH License",
                "http://www.google.com",
                Collections.emptyList());
    }
}
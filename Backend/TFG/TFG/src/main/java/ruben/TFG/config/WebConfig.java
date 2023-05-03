package ruben.TFG.config;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;


@Component
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {
    /**
     * Method to enable CORS
     */
    @Override
    public void addCorsMappings(org.springframework.web.servlet.config.annotation.CorsRegistry registry) {
        registry.addMapping("/**");
    }

    /**
     * Method to configure the default view resolver
     */
    @Bean
    public InternalResourceViewResolver defaultViewResolver(){
        return new InternalResourceViewResolver();
    }
}
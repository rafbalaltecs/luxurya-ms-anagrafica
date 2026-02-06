package anagrafica.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class EnableCorsOrigin implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
    	registry.addMapping("/**")
	        .allowedOriginPatterns("*")  // <-- AGGIUNGI QUESTO
	        .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS")
	        .allowedHeaders("*")  // <-- AGGIUNGI QUESTO
	        .exposedHeaders("Authorization")  // <-- AGGIUNGI QUESTO
	        .allowCredentials(true)  // <-- AGGIUNGI QUESTO
	        .maxAge(3600);  // <-- AGGIUNGI QUESTO (cache preflight per 1 ora)
    }


}

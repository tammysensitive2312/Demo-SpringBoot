package niit.com.demo;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

@Configuration
public class ConfigResource {

    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("uploads/**");

    }
}

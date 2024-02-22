package dev.lucasmendes.primespring.config;

import com.sun.faces.config.FacesInitializer;
import jakarta.faces.webapp.FacesServlet;
import org.jboss.weld.environment.servlet.EnhancedListener;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;


@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addViewControllers(final ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("index");
    }

    @Bean
    public ServletRegistrationBean<?> facesServletRegistraiton() {
        final var registration = new ServletRegistrationBean<>(new FacesServlet(), "*.xhtml");
        registration.setName("Faces Servlet");
        registration.setLoadOnStartup(1);
        return registration;
    }

    @Bean
    public ServletContextInitializer servletContextInitializer() {
        return servletContext -> {
            final var cdiInitializer = new EnhancedListener();
            cdiInitializer.onStartup(null, servletContext);

            final var facesInitializer = new FacesInitializer();
            facesInitializer.onStartup(null, servletContext);

            servletContext.setInitParameter("primefaces.THEME", "saga");
        };
    }

    @Bean
    public ViewResolver viewResolver() {
        final InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/views/");
        resolver.setSuffix(".xhtml");
        resolver.setOrder(0);
        return resolver;
    }
}

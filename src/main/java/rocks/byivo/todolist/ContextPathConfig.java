package rocks.byivo.todolist;

import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.stereotype.Component;

@Component
public class ContextPathConfig implements WebServerFactoryCustomizer<ConfigurableServletWebServerFactory> {

    public static final String BASE_PATH = "/todo-list";
    
    @Override
    public void customize(ConfigurableServletWebServerFactory factory) {
	factory.setContextPath(BASE_PATH);
    }

}

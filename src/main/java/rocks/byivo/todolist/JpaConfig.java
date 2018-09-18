package rocks.byivo.todolist;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages= {
	"rocks.byivo.todolist.model",
	"rocks.byivo.todolist.repositories"
})
@Configuration
public class JpaConfig {

}

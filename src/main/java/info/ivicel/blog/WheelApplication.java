package info.ivicel.blog;

import info.ivicel.blog.core.repository.base.BaseRepositoryImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "info.ivicel.blog.core.repository",
		repositoryBaseClass = BaseRepositoryImpl.class)
@EnableJpaAuditing
public class WheelApplication {

	public static void main(String[] args) {
		SpringApplication.run(WheelApplication.class, args);
	}

}

package vn.cmc.du21.userservice.loader;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.sql.Date;

@SpringBootApplication
@ComponentScan(basePackages = {"vn.cmc.du21.userservice.presentation.external.controller",
		"vn.cmc.du21.userservice.presentation.internal.controller",
		"vn.cmc.du21.userservice.service"})
@EntityScan(basePackages = "vn.cmc.du21.userservice.persistence.internal.entity")
@EnableJpaRepositories(basePackages = "vn.cmc.du21.userservice.persistence.internal.repository")
public class UserServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserServiceApplication.class, args);
	}

}

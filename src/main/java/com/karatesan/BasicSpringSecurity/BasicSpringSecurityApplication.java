package com.karatesan.BasicSpringSecurity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@EnableWebSecurity(debug = true) // to jest potrzebne jak nie uzywamy spring boot tylko zwyklego, zeby apka wiedziala ,ze jest security
//debug dodalismy, zeby coestowac filtry, normalnie to trzeba wywalic
public class BasicSpringSecurityApplication {

	public static void main(String[] args) {
		SpringApplication.run(BasicSpringSecurityApplication.class, args);
	}

}

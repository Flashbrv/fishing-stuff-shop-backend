package com.example.fishingstuffshopbackend;

import com.example.fishingstuffshopbackend.domain.Role;
import com.example.fishingstuffshopbackend.domain.User;
import com.example.fishingstuffshopbackend.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	CommandLineRunner run(UserService userService) {
		return args -> {
			userService.save(new Role("ROLE_USER"));
			userService.save(new Role("ROLE_MANAGER"));
			userService.save(new Role("ROLE_ADMIN"));
			userService.save(new Role("ROLE_SUPER_ADMIN"));

			User travolta = User.builder()
					.firstName("John")
					.lastName("Travolta")
					.email("john@mail.ua")
					.phoneNumber("380991291320")
					.password("1234")
					.build();
			userService.create(travolta);

			User smith = User.builder()
					.firstName("Will")
					.lastName("Smith")
					.email("will@mail.ua")
					.phoneNumber("380991291321")
					.password("1234")
					.build();
			userService.create(smith);

			User carry = User.builder()
					.firstName("Jim")
					.lastName("Carry")
					.email("jim@mail.ua")
					.phoneNumber("380991291322")
					.password("1234")
					.build();
			userService.create(carry);

			User schwarzenegger = User.builder()
					.firstName("Arnold")
					.lastName("Schwarzenegger")
					.email("arnold@mail.ua")
					.phoneNumber("380991291323")
					.password("1234")
					.build();
			userService.create(schwarzenegger);

			userService.addRoleToUser("john@mail.ua", "ROLE_USER");
			userService.addRoleToUser("john@mail.ua", "ROLE_MANAGER");
			userService.addRoleToUser("will@mail.ua", "ROLE_MANAGER");
			userService.addRoleToUser("jim@mail.ua", "ROLE_ADMIN");
			userService.addRoleToUser("arnold@mail.ua", "ROLE_ADMIN");
			userService.addRoleToUser("arnold@mail.ua", "ROLE_SUPER_ADMIN");
			userService.addRoleToUser("arnold@mail.ua", "ROLE_USER");
		};
	}

}

package com.example.myapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableAsync;

import com.example.myapp.entity.Role;
import com.example.myapp.repository.RoleRepository;

@SpringBootApplication
@EnableAsync
public class MyappApplication implements CommandLineRunner {

	@Autowired
	private RoleRepository roleRepository;

	public static void main(String[] args) {
		SpringApplication.run(MyappApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// Initialize roles if not exist
		// if (roleRepository.findByName("ROLE_ADMIN").isEmpty()) {
		// 	Role adminRole = new Role();
		// 	adminRole.setName("ROLE_ADMIN");
		// 	adminRole.setDescription("Administrator role");
		// 	roleRepository.save(adminRole);
		// }

		// if (roleRepository.findByName("ROLE_USER").isEmpty()) {
		// 	Role userRole = new Role();
		// 	userRole.setName("ROLE_USER");
		// 	userRole.setDescription("User role");
		// 	roleRepository.save(userRole);
		// }
	}
}

package com.HRPlus.space;

import javax.annotation.PostConstruct;
import java.time.LocalDate;

import com.HRPlus.space.repositories.IUtilidateurRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.HRPlus.space.entities.ERole;
import com.HRPlus.space.entities.Role;
import com.HRPlus.space.entities.UserInformation;
import com.HRPlus.space.repositories.RoleRepository;

@SpringBootApplication
public class HrPlusApplication {

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private IUtilidateurRepo userInformationRepository;

	public static void main(String[] args) {
		SpringApplication.run(HrPlusApplication.class, args);
	}

	@PostConstruct
	public void setUp() {
		// Any setup code can go here
	}

	@Bean
	public ApplicationRunner initRolesAndUser() {
		return new ApplicationRunner() {
			@Override
			public void run(ApplicationArguments args) throws Exception {
				// Initialize roles if not already present
				if (roleRepository.count() == 0) {
					roleRepository.save(new Role(ERole.ROLE_USER));
					roleRepository.save(new Role(ERole.ROLE_ADMIN));
					System.out.println("Roles initialized: ROLE_USER, ROLE_ADMIN");
				}

				// Check if there are users and create the two users if not already present
				if (userInformationRepository.count() == 0) {
					// Create the first user (Soul)
					UserInformation soulUser = new UserInformation();
					soulUser.setUsername("Soul");
					soulUser.setEmail("soultrash11515@outlook.com");
					soulUser.setPassword("$2a$10$y5yFKjqJlN3zzBVDzPFlPu4KysUpeaT1hECIXXTKYSUX3dWPVfgUu");
					soulUser.setDateOfBirth(LocalDate.of(1990, 1, 1)); // Example date
					soulUser.setPhone("+212622896109");
					soulUser.setStatus("Active");
					soulUser.setNom("Kifa");
					soulUser.setPrenom("Soulaimane");
					soulUser.setAdresse("Ressource humaines");
					soulUser.setVille("Casablanca");
					soulUser.setTypeContrat("CDI");
					soulUser.setDateEntree(LocalDate.of(2025, 2, 2));

					// Assign the user role
					Role userRole = roleRepository.findByName(ERole.ROLE_USER).get();
					soulUser.getRoles().add(userRole);

					// Save the user
					userInformationRepository.save(soulUser);
					System.out.println("Soul user created");

					// Create the second user (admin)
					UserInformation adminUser = new UserInformation();
					adminUser.setUsername("admin");
					adminUser.setEmail("admin@example.com");
					adminUser.setPassword("$2a$10$PgRDZIRh1VzM241Xor9cIOlBjZ9N90bdxCqu5r8/U74rIB1mZWFVi");
					adminUser.setDateOfBirth(LocalDate.of(1990, 1, 1)); // Example date
					adminUser.setPhone("1234567890");
					adminUser.setStatus("Active");
					adminUser.setNom("Admin");
					adminUser.setPrenom("Super");
					adminUser.setAdresse("Admin Office");
					adminUser.setVille("Admin City");
					adminUser.setTypeContrat("Admin Contract");
					adminUser.setDateEntree(LocalDate.of(2025, 2, 2));

					// Assign the admin role
					Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN).get();
					adminUser.getRoles().add(adminRole);

					// Save the admin user
					userInformationRepository.save(adminUser);
					System.out.println("Admin user created");
				}
			}
		};
	}
}

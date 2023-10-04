
package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.demo.repositories.UserRepository;


//@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = "com.example.demo")
public class BanqueApplication implements CommandLineRunner{
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;
	public static void main(String[] args) {
		SpringApplication.run(BanqueApplication.class, args);
	}
	
	@Override
    public void run(String... args) throws Exception {
		/* User newUser = new User();
         newUser.setUsername("user");
         newUser.setPassword(passwordEncoder.encode("1234")); // Set the password as-is (not encoded)
         newUser.setRoles(Arrays.asList("USER"));
         userRepository.save(newUser);*/
	}

}

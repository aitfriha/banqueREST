package com.example.demo.config;

import java.util.Arrays;
import java.util.List;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.cors.CorsConfigurationSource;

import com.nimbusds.jose.jwk.source.ImmutableSecret;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true) // activate security. take into consideration methodes that have @PreAuthorize("hasAuthority('SCOPE_USET')") or other ...
public class SecurityConfig{
	@Value("${jwt.secretKey}")
	private String secretKey;

//	@Override
//	public void addCorsMappings(CorsRegistry registry) {
//		registry.addMapping("/**") // This allows CORS for all endpoints
//				.allowedOrigins("http://localhost:4200") // Add your Angular app's URL
//				.allowedMethods("GET", "POST", "PUT", "DELETE").allowedHeaders("*");
//	}
	
	@Autowired
    private CustomUserDetailsService customUserDetailsService; // Inject your custom UserDetailsService

	/*@Bean
	public InMemoryUserDetailsManager inMemoryUserDetailsManager() {
		PasswordEncoder passwordEncoder = passwordEncoder();
		return new InMemoryUserDetailsManager(
				User.withUsername("user").password(passwordEncoder.encode("1234")).authorities("USER").build(),
				User.withUsername("admin").password(passwordEncoder.encode("1234")).authorities("ADMIN", "USER")
						.build());

	}*/

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
		return httpSecurity.sessionManagement(sm -> 
		        sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.csrf(sr -> sr.disable())
				.cors(Customizer.withDefaults())
				// sans "/" ça doit marcher
				//.authorizeHttpRequests(authorizeRequests -> authorizeRequests.requestMatchers("actuator/info").permitAll())
				.authorizeHttpRequests(authorizeRequests -> authorizeRequests.requestMatchers("auth/login/**").permitAll())
				.authorizeHttpRequests(authorizeRequests -> authorizeRequests.requestMatchers("/swagger-ui/**").permitAll())
				.authorizeHttpRequests(authorizeRequests -> authorizeRequests.requestMatchers("/v3/api-docs/**").permitAll())
				.authorizeHttpRequests(authorizeRequests -> authorizeRequests.requestMatchers("/swagger-ui.html**").permitAll())
				// .antMatchers("/swagger-ui/**", "/v3/api-docs/**", "/swagger-ui.html").permitAll() // Adjust these paths
				.authorizeHttpRequests(ar -> ar.anyRequest().authenticated())
				// .httpBasic(Customizer.withDefaults())
				.oauth2ResourceServer(oa -> oa.jwt(Customizer.withDefaults())).build();
	}

	@Bean
	JwtEncoder jwtEncoder() {
		return new NimbusJwtEncoder(new ImmutableSecret<>(secretKey.getBytes()));
	}

	@Bean
	JwtDecoder jwtDecoder() {
		SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(), "RSA");
		return NimbusJwtDecoder.withSecretKey(secretKeySpec).macAlgorithm(MacAlgorithm.HS512).build();
	}

	@Bean
	public AuthenticationManager authenticationManager(UserDetailsService userDetailsService) {
		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
		daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
		//daoAuthenticationProvider.setUserDetailsService(userDetailsService);
		daoAuthenticationProvider.setUserDetailsService(customUserDetailsService); // Use your custom UserDetailsService
		return new ProviderManager(daoAuthenticationProvider);
	}
	
	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(Arrays.asList("*"));
		configuration.setAllowedMethods(Arrays.asList("*"));
		configuration.setAllowedHeaders(Arrays.asList("*"));
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}

}
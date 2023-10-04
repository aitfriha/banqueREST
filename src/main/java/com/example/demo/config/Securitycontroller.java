package com.example.demo.config;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
public class Securitycontroller {
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private JwtEncoder jwtEncoder;
	
	@GetMapping("/profile")
	public Authentication authentication(Authentication authentication) {
		return authentication;
	}

	@PostMapping("/login")
	public Map<String,String> login(String username,String password){
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		//authentication.getName();
		String scopeRoles = authentication.getAuthorities().stream()
				.map(GrantedAuthority::getAuthority).collect(Collectors.joining(" "));
		Instant instant=Instant.now();
		
		JwtClaimsSet jwtClaimsSet = JwtClaimsSet.builder()
				.issuedAt(instant)
	         	.expiresAt(instant.plus(360,ChronoUnit.MINUTES))
	         	.subject(username)
	         	.claim("scope",scopeRoles)
		        .build();
		JwtEncoderParameters jwtEncoderParameters = JwtEncoderParameters.
				from(JwsHeader.with(MacAlgorithm.HS512).build(),
				jwtClaimsSet);
		String jwt= jwtEncoder.encode(jwtEncoderParameters).getTokenValue();
		
		return Map.of("accesstoken",jwt);
	}
}

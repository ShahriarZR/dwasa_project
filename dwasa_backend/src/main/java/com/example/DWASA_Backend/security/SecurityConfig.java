package com.example.DWASA_Backend.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtAuthenticationFilter jwtAuthenticationFilter)
			throws Exception {
		http
				.csrf(csrf -> csrf.disable())
				.sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.httpBasic(httpBasic -> httpBasic.disable())
				.formLogin(form -> form.disable())
				.authorizeHttpRequests(auth -> auth
						.requestMatchers("/api/auth/**").permitAll()
						.requestMatchers("/api/sales/**").authenticated()
						.requestMatchers("/api/productions/**").authenticated()
						.requestMatchers("/api/injection-entries/**").authenticated()
						.requestMatchers("/api/wastages/**").authenticated()
						.requestMatchers("/api/sections/**").authenticated()
						.requestMatchers("/api/units/**").authenticated()
						.requestMatchers("/api/ingradients/**").authenticated()
						.requestMatchers("/api/products/**").authenticated()
						.requestMatchers("/api/customers/**").authenticated()
						.requestMatchers("/api/machines/**").authenticated()
						.requestMatchers("/api/users/me", "/api/users/me/**").authenticated()
						.requestMatchers("/api/users/**").hasRole("ADMIN")
						.anyRequest().authenticated());

		http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}

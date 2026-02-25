package com.example.DWASA_Backend.security;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
	private final JwtService jwtService;

	public JwtAuthenticationFilter(JwtService jwtService) {
		this.jwtService = jwtService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
		String token = extractBearerToken(authHeader);
		if (token != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			try {
				Claims claims = jwtService.parseAndValidate(token);
				String empId = claims.getSubject();
				String dept = claims.get("dept", String.class);
				List<SimpleGrantedAuthority> authorities = Optional.ofNullable(dept)
						.map(d -> Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + d)))
						.orElse(Collections.emptyList());

				UsernamePasswordAuthenticationToken authentication =
						new UsernamePasswordAuthenticationToken(empId, null, authorities);
				SecurityContextHolder.getContext().setAuthentication(authentication);
			} catch (Exception ignored) {
				// Invalid/expired token; leave request unauthenticated
			}
		}

		filterChain.doFilter(request, response);
	}

	private String extractBearerToken(String authHeader) {
		if (authHeader == null) {
			return null;
		}
		if (!authHeader.startsWith("Bearer ")) {
			return null;
		}
		String token = authHeader.substring("Bearer ".length()).trim();
		return token.isEmpty() ? null : token;
	}
}

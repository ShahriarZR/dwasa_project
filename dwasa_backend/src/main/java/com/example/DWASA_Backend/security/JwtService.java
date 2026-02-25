package com.example.DWASA_Backend.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class JwtService {
	private final SecretKey key;
	private final long expirationSeconds;

	public JwtService(
			@Value("${app.jwt.secret}") String secret,
			@Value("${app.jwt.expiration-seconds:86400}") long expirationSeconds) {
		this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
		this.expirationSeconds = expirationSeconds;
	}

	public long getExpirationSeconds() {
		return expirationSeconds;
	}

	public String generateToken(String empId, String department) {
		Instant now = Instant.now();
		Instant expiry = now.plusSeconds(expirationSeconds);

		return Jwts.builder()
				.subject(empId)
				.claim("dept", department)
				.issuedAt(Date.from(now))
				.expiration(Date.from(expiry))
				.signWith(key, Jwts.SIG.HS256)
				.compact();
	}

	public Claims parseAndValidate(String token) {
		return Jwts.parser()
				.verifyWith(key)
				.build()
				.parseSignedClaims(token)
				.getPayload();
	}
}

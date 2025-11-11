package br.com.eaugusto.security;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import br.com.eaugusto.exception.JWTKeyGenerationException;
import br.com.eaugusto.model.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

/**
 * Utility class for generating and parsing JWT tokens. Uses the database
 * password as the secret key for signing tokens.
 * 
 * @author Eduardo Augusto (github.com/AsrielDreemurrGM/)
 * @since Nov 11, 2025
 */
@Component
public class JWTUtil {

	private final Key key;
	private static final int ONEDAYINMILLISECONDS = 86_400_000;

	public JWTUtil(@Value("${spring.datasource.password}") String dbPassword) {
		this.key = deriveKeyFromPassword(dbPassword);
	}

	private Key deriveKeyFromPassword(String password) {
		try {
			MessageDigest sha256 = MessageDigest.getInstance("SHA-256");
			byte[] hashed = sha256.digest(password.getBytes(StandardCharsets.UTF_8));
			return Keys.hmacShaKeyFor(hashed);
		} catch (NoSuchAlgorithmException e) {
			throw new JWTKeyGenerationException("Failed to generate JWT key from password", e);
		}
	}

	public String generateToken(User user) {
		return Jwts.builder().setSubject(user.getEmail()).setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + ONEDAYINMILLISECONDS))
				.signWith(key, SignatureAlgorithm.HS256).compact();
	}

	public String extractEmail(String token) {
		return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().getSubject();
	}
}

package br.com.eaugusto.security;

import java.security.Key;
import java.util.Date;

import org.springframework.stereotype.Component;

import br.com.eaugusto.model.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

/**
 *
 * @author Eduardo Augusto (github.com/AsrielDreemurrGM/)
 * @since Nov 11, 2025
 */
@Component
public class JWTUtil {

	private static final String SECRET = "JWTSecretKeyJWTSecretKeyJWTSecretKey12";
	private static final int ONEDAYINMILLISECONDS = 86400000;

	private final Key key = Keys.hmacShaKeyFor(SECRET.getBytes());

	public String generateToken(User user) {
		return Jwts.builder().setSubject(user.getEmail()).setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + ONEDAYINMILLISECONDS))
				.signWith(key, SignatureAlgorithm.HS256).compact();
	}

	public String extractEmail(String token) {
		return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().getSubject();
	}
}

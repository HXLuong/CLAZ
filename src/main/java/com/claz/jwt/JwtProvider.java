package com.claz.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.SecureRandom;
import java.util.Date;

@Component
public class JwtProvider {
	private final SecretKey jwtSecret;
	private final long jwtExpirationMs = 86400000;

	public JwtProvider() {
		byte[] keyBytes = new byte[64]; 
		new SecureRandom().nextBytes(keyBytes); 
		this.jwtSecret = Keys.hmacShaKeyFor(keyBytes); 
	}

	public String generateToken(String username) {
		return Jwts.builder().setSubject(username).setIssuedAt(new Date())
				.setExpiration(new Date((new Date()).getTime() + jwtExpirationMs)).signWith(jwtSecret)
				.compact();
	}

	public String getUsernameFromJwt(String token) {
		return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
	}

	public boolean validateJwtToken(String token) {
		try {
			Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
			return true;
		} catch (SignatureException | MalformedJwtException | ExpiredJwtException | UnsupportedJwtException
				| IllegalArgumentException e) {
			System.out.println("Invalid JWT token: " + e.getMessage());
		}
		return false;
	}

	public String getUserNameFromToken(String token) {
		return getClaimsFromToken(token).getSubject();
	}

	private Claims getClaimsFromToken(String token) {
		return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
	}
}

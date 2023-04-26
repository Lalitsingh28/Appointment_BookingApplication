package com.example.demo.security;

import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtils {
	

	  @Value("${blogg.app.jwtSecret}")
	  private String jwtSecret;

	  @Value("${blogg.app.jwtExpirationMs}")
	  private long jwtExpirationMs;

	  private Claims parseToken(String token) {
	        // Create JwtParser
	        JwtParser jwtParser = Jwts.parserBuilder()
	                .setSigningKey(jwtSecret.getBytes())
	                .build();

	        try {
	            return jwtParser.parseClaimsJws(token)
	                    .getBody();
	        } catch (ExpiredJwtException e) {
	            System.out.println(e.getMessage());
	        } catch (UnsupportedJwtException e) {
	            System.out.println(e.getMessage());
	        } catch (MalformedJwtException e) {
	            System.out.println(e.getMessage());
	        } catch (IllegalArgumentException e) {
	            System.out.println(e.getMessage());
	        }

	        return null;
	    }

	    public boolean validateToken(String token) {
	        return parseToken(token) != null;
	    }

	    public String getUsernameFromToken(String token) {
	        // Get claims
	        Claims claims = parseToken(token);

	        // Extract subject
	        if(claims != null){
	            return claims.getSubject();
	        }

	        return null;
	    }

	    public String generateToken(String username) {
	        // Create signing key
	        Key key = Keys.hmacShaKeyFor(jwtSecret.getBytes());

	        // Generate token
	        var currentDate = new Date();
	        var expiration = new Date(currentDate.getTime() + jwtExpirationMs);

	        return Jwts.builder()
	                .setSubject(username)
	                .setIssuedAt(currentDate)
	                .setExpiration(expiration)
	                .signWith(key)
	                .compact();
	    }
		
}

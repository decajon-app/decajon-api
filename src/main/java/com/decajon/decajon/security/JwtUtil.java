package com.decajon.decajon.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil
{
    @Value("${jwt.secret}")
    private String SECRET_KEY;

    public JwtUtil(
            @Value("${jwt.secret}") String SECRET_KEY)
    {
        this.SECRET_KEY = SECRET_KEY;
    }

    public String generateToken(String email)
    {
        Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractEmail(String token)
    {
        return getClaims(token).getSubject();
    }

    public boolean validateToken(String token, UserDetails userDetails)
    {
        String email = extractEmail(token);
        return email.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token)
    {
        return getClaims(token).getExpiration().before(new Date());
    }

    private Claims getClaims(String token)
    {
        Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}

package com.decajon.decajon.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;
import java.nio.file.AccessDeniedException;
import java.security.Key;
import java.time.Instant;
import java.util.Date;

@Component
public class JwtUtil
{
    private static final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    public String generateToken(String email, long expiration)
    {
        var now = Instant.now();
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(Date.from(now))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(SECRET_KEY, SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractEmail(String token) throws AccessDeniedException
    {
        return getTokenBody(token).getSubject();
    }

    public Boolean validateToken(String token) throws AccessDeniedException
    {
        try
        {
            extractEmail(token);
            return !isTokenExpired(token);
        }
        catch (Exception e)
        {
            return false;
        }
    }

    private boolean isTokenExpired(String token) throws AccessDeniedException
    {
        Claims claims = getTokenBody(token);
        return claims.getExpiration().before(new Date());
    }

    private Claims getTokenBody(String token) throws AccessDeniedException
    {
        try
        {
            return Jwts
                    .parserBuilder()
                    .setSigningKey(SECRET_KEY)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        }
        catch (ExpiredJwtException e)
        {
            throw new AccessDeniedException("Access denied: " + e.getMessage());
        }
    }
}

package com.decajon.decajon.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import java.nio.file.AccessDeniedException;
import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Component
public class JwtUtil
{
    private static final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private static final int MINUTES = 60;

    public static String generateToken(String email)
    {
        var now = Instant.now();
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plus(MINUTES, ChronoUnit.MINUTES)))
                .signWith(SECRET_KEY, SignatureAlgorithm.HS256)
                .compact();
    }

    public static String extractEmail(String token) throws AccessDeniedException
    {
        return getTokenBody(token).getSubject();
    }

    public static Boolean validateToken(String token, UserDetails userDetails) throws AccessDeniedException
    {
        final String email = extractEmail(token);
        return email.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    private static boolean isTokenExpired(String token) throws AccessDeniedException
    {
        Claims claims = getTokenBody(token);
        return claims.getExpiration().before(new Date());
    }

    private static Claims getTokenBody(String token) throws AccessDeniedException
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

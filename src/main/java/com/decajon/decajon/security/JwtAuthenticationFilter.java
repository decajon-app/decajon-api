package com.decajon.decajon.security;

import com.decajon.decajon.services.UserDetailsServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.nio.file.AccessDeniedException;

@RequiredArgsConstructor
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter
{
    private final UserDetailsServiceImpl userDetailsService;
    private final ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws ServletException, IOException
    {
        try {
            String authHeader = request.getHeader("Authorization");
            String token = null;
            String email = null;

            if(authHeader != null && authHeader.startsWith("Bearer "))
            {
                token = authHeader.substring(7);
                email = JwtUtil.extractEmail(token);
            }

            if(token == null)
            {
                filterChain.doFilter(request, response);
                return;
            }

            if(email != null && SecurityContextHolder.getContext().getAuthentication() == null)
            {
                UserDetails userDetails = userDetailsService.loadUserByUsername(email);
                if(JwtUtil.validateToken(token, userDetails))
                {
                    UsernamePasswordAuthenticationToken authenticationToken
                        = new UsernamePasswordAuthenticationToken(userDetails, null, null);
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }

            filterChain.doFilter(request, response);
        }
        catch (AccessDeniedException e)
        {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
    }
}

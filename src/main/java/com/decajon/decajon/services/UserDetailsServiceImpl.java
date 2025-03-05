package com.decajon.decajon.services;

import com.decajon.decajon.models.User;
import com.decajon.decajon.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService
{
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        System.out.println("Buscando usuario con email: " + email);
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con email: " + email));
        System.out.println("Usuario encontrado: " + user.getEmail());
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                List.of(new SimpleGrantedAuthority("USER"))
        );
    }
}

package com.decajon.decajon.services;

import com.decajon.decajon.dto.UserDto;
import com.decajon.decajon.dto.UserRequestDto;
import com.decajon.decajon.mappers.UserMapper;
import com.decajon.decajon.models.User;
import com.decajon.decajon.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService
{
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final UserMapper userMapper;


    /**
     * Get user by ID
     */
    public User getUserById(Long id)
    {
        Optional<User> user = userRepository.findById(id);
        return user.orElse(null);
    }


    /**
     * Registrar un nuevo usuario.
     * Recibe un UserRequestDto, que contiene la contraseña.
     * Regresa un UserDto, que son los datos sin la contraseña.
     * @param userRequestDto
     * @return UserDto
     */
    @Transactional
    public UserDto createUser(UserRequestDto userRequestDto)
    {
        if(userRepository.findByEmail(userRequestDto.getEmail()).isPresent())
        {
            throw new RuntimeException("El email ya está registrado.");
        }

        User user = userMapper.toEntity(userRequestDto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User savedUser = userRepository.save(user);

        return userMapper.toDto(savedUser);
    }


    /**
     *
     * @param id
     * @param userDto
     * @return
     */
    @Transactional
    public UserDto updateUser(Long id, UserDto userDto)
    {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado, id: " + id));

        userMapper.updateUserFromDto(userDto, user);
        User updatedUser = userRepository.save(user);

        return userMapper.toDto(updatedUser);
    }


    /**
     *
     * @param id
     * @return
     */
    @Transactional
    public void deleteUser(Long id)
    {
        if(!userRepository.existsById(id))
        {
            throw new EntityNotFoundException("Usuario no encontrado, id: " + id);
        }
        userRepository.deleteById(id);
    }


    /**
     *
     * @param email
     * @return
     */
    public Optional<User> getUserByEmail(String email)
    {
        return userRepository.findByEmail(email);
    }

    /**
     * Metodo para actualizar el refresh_token de la bd cuando se realiza un refresh del token
     * @param email
     * @param refreshToken
     */
    @Transactional
    public void updateRefreshToken(String email, String refreshToken)
    {
        userRepository.updateRefreshToken(email, refreshToken);
    }
}

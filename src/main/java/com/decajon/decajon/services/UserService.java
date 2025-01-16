package com.decajon.decajon.services;

import com.decajon.decajon.dto.UserDto;
import com.decajon.decajon.models.User;
import com.decajon.decajon.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class UserService
{
    @Autowired
    private UserRepository userRepository;


    /**
     * Get all users
     */
    public List<User> getAllUsers()
    {
        return userRepository.findAll();
    }


    /**
     * Get user by ID
     */
    public User getUserById(Long id)
    {
        Optional<User> user = userRepository.findById(id);
        return user.orElse(null);
    }


    /**
     * Create new user
     */
    public User createUser(UserDto userDto)
    {
        // Mapeo del DTO a User
        User user = new User();
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword((userDto.getPassword())); // TODO: cifrar la contraseña

        // Guardar el usuario en la bd a través del repository
        return userRepository.save(user);
    }


    /**
     * Update user
     */
    public Optional<User> updateUser(Long id, UserDto userDto)
    {
        Optional<User> existingUser = userRepository.findById(id);
        if (existingUser.isPresent())
        {
            User user = existingUser.get();
            user.setName(userDto.getName());
            user.setEmail(userDto.getEmail());
            user.setPassword(userDto.getPassword());
            return Optional.of(userRepository.save(user));
        }
        return Optional.empty();
    }


    /**
     * Delete user
     */
    public boolean deleteUser(Long id)
    {
        Optional<User> user = userRepository.findById(id);
        if(user.isPresent())
        {
            userRepository.delete(user.get());
            return true;
        }
        return false;
    }


}

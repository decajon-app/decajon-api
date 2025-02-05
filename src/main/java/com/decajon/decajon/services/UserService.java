package com.decajon.decajon.services;

import com.decajon.decajon.dto.UserDto;
import com.decajon.decajon.mappers.UserMapper;
import com.decajon.decajon.models.User;
import com.decajon.decajon.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService
{

    private final UserRepository userRepository;
    private final UserMapper userMapper;

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
     *
     * @param userDto
     * @return
     */
    @Transactional
    public UserDto createUser(UserDto userDto)
    {
        User user = userMapper.toEntity(userDto);
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

        userMapper.updatedUserFromDto(userDto, user);;
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


}

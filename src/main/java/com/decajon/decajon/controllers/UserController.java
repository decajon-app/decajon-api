package com.decajon.decajon.controllers;

import com.decajon.decajon.dto.UserDto;
import com.decajon.decajon.dto.UserRequestDto;
import com.decajon.decajon.models.User;
import com.decajon.decajon.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController
{
    private final UserService userService;


    /**
     * Obtener un usuario por su id
     */
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id)
    {
        Optional<User> user = Optional.ofNullable(userService.getUserById(id));
        return user.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    /**
     * Actualizar un usuario por id
     */
    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable Long id, @RequestBody UserDto userDto)
    {
        return ResponseEntity.ok(userService.updateUser(id, userDto));
    }


    /**
     * Eliminar un usuario por id
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id)
    {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}

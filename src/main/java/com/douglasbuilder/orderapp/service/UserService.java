package com.douglasbuilder.orderapp.service;

import com.douglasbuilder.orderapp.dto.user.CreateUserDTO;
import com.douglasbuilder.orderapp.dto.user.ResponseUserDTO;
import com.douglasbuilder.orderapp.dto.user.UpdateUserDTO;
import com.douglasbuilder.orderapp.exceptions.auth.AuthInvalidCredentialsException;
import com.douglasbuilder.orderapp.exceptions.user.DuplicateEmailException;
import com.douglasbuilder.orderapp.exceptions.user.UserNotFoundException;
import com.douglasbuilder.orderapp.mappers.UserMapper;
import com.douglasbuilder.orderapp.model.User;
import com.douglasbuilder.orderapp.repository.UserRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Data
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public ResponseUserDTO create(CreateUserDTO createUserDTO) {
        boolean emailExists = userRepository.existsByEmail(createUserDTO.getEmail());
        if (emailExists) {
            throw new DuplicateEmailException("Email already in use: " + createUserDTO.getEmail());
        }

        // MapStruct will handle the mapping from CreateUserDTO to User entity
        var user = userMapper.toModel(createUserDTO);

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        var createdUser = userRepository.save(user);

        return userMapper.toDto(createdUser);
    }

    public ResponseUserDTO findById(UUID id) {
        var user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("No User found with ID: " + id));
        return userMapper.toDto(user);
    }

    public User findByEmail(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UserNotFoundException("No User found with email: " + email);
        }
        return user;
    }

    public void updateById(UUID id, UpdateUserDTO updateUserDTO) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("No User found with ID: " + id));

        if (updateUserDTO.getFirstName() != null) {
            user.setFirstName(updateUserDTO.getFirstName());
        }
        if (updateUserDTO.getLastName() != null) {
            user.setLastName(updateUserDTO.getLastName());
        }

        var userUpdated = userRepository.save(user);

        userMapper.toDto(userUpdated);
    }

    public void deleteById(UUID id) {
        boolean idExists = userRepository.existsById(id);
        if (!idExists) {
            throw new UserNotFoundException("No User found with ID: " + id);
        }
        userRepository.deleteById(id);
    }

    public void authenticateUser(String email, String pwd) {
        User user = findByEmail(email);
        if (!passwordEncoder.matches(pwd, user.getPassword())){
            throw new AuthInvalidCredentialsException("Password invalid -- TempMsg");
        };
    }

}

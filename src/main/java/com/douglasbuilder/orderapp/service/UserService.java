package com.douglasbuilder.orderapp.service;

import com.douglasbuilder.orderapp.dto.user.CreateUserDTO;
import com.douglasbuilder.orderapp.dto.user.ResponseUserDTO;
import com.douglasbuilder.orderapp.dto.user.UpdateUserDTO;
import com.douglasbuilder.orderapp.exceptions.user.DuplicateEmailException;
import com.douglasbuilder.orderapp.exceptions.user.UserNotFoundException;
import com.douglasbuilder.orderapp.mappers.UserMapper;
import com.douglasbuilder.orderapp.model.User;
import com.douglasbuilder.orderapp.repository.UserRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Data
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public User create(CreateUserDTO createUserDTO) {
        boolean emailExists = userRepository.existsByEmail(createUserDTO.getEmail());
        if (emailExists) {
            throw new DuplicateEmailException("Email already in use: " + createUserDTO.getEmail());
        }

        // MapStruct will handle the mapping from CreateUserDTO to User entity
        User newUser = userMapper.toModel(createUserDTO);

        return userRepository.save(newUser);
    }

    public ResponseUserDTO findById(Long id) {
        //If a value is present(user), returns the user,
        // otherwise throws an exception produced by the exception supplying function
        // (in this case, a lambda function that creates a new UserNotFoundException with a message).
        var user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("No User found with ID: " + id));

        return userMapper.toDto(user);
    }

    public void deleteById(Long id) {
        boolean idExists = userRepository.existsById(id);
        if (!idExists) {
            throw new UserNotFoundException("No User found with ID: " + id);
        }
        userRepository.deleteById(id);
    }

    public User updateById(Long id, UpdateUserDTO updateUserDTO) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("No User found with ID: " + id));

        if (updateUserDTO.getFirstName() != null) {
            user.setFirstName(updateUserDTO.getFirstName());
        }
        if (updateUserDTO.getLastName() != null) {
            user.setLastName(updateUserDTO.getLastName());
        }
        return userRepository.save(user);
    }

    public User findByEmail(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UserNotFoundException("No User found with email: " + email);
        }
        return user;
    }

    public void deleteByEmail(String email) {
        boolean emailExists = userRepository.existsByEmail(email);
        if (!emailExists) {
            throw new UserNotFoundException("No User found with email: " + email);
        }
        userRepository.deleteByEmail(email);
    }

    public User updateByEmail(String email, UpdateUserDTO updateUserDTO) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UserNotFoundException("No User found with email: " + email);
        }

        if (updateUserDTO.getFirstName() != null) {
            user.setFirstName(updateUserDTO.getFirstName());
        }
        if (updateUserDTO.getLastName() != null) {
            user.setLastName(updateUserDTO.getLastName());
        }
        return userRepository.save(user);
    }
}

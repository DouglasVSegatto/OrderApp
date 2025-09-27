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

    public ResponseUserDTO create(CreateUserDTO createUserDTO) {
        boolean emailExists = userRepository.existsByEmail(createUserDTO.getEmail());
        if (emailExists) {
            throw new DuplicateEmailException("Email already in use: " + createUserDTO.getEmail());
        }

        // MapStruct will handle the mapping from CreateUserDTO to User entity
        User newUser = userMapper.toModel(createUserDTO);

        var createdUser = userRepository.save(newUser);

        return userMapper.toDto(createdUser);
    }

    public ResponseUserDTO findById(Long id) {
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

    public void updateById(Long id, UpdateUserDTO updateUserDTO) {
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

    public void deleteById(Long id) {
        boolean idExists = userRepository.existsById(id);
        if (!idExists) {
            throw new UserNotFoundException("No User found with ID: " + id);
        }
        userRepository.deleteById(id);
    }

}

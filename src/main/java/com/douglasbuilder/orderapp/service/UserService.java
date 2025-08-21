package com.douglasbuilder.orderapp.service;

import com.douglasbuilder.orderapp.dto.user.CreateUserDTO;
import com.douglasbuilder.orderapp.dto.user.UpdateUserDTO;
import com.douglasbuilder.orderapp.exceptions.user.DuplicateEmailException;
import com.douglasbuilder.orderapp.exceptions.user.UserNotFoundException;
import com.douglasbuilder.orderapp.model.User;
import com.douglasbuilder.orderapp.repository.UserRepository;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Service
public class UserService {

  @Autowired private UserRepository userRepository;

  public List<User> getAll() {
    return userRepository.findAll();
  }

  public User getByEmail(String email) {
    User user = userRepository.findByEmail(email);
    if (user == null) {
      throw new UserNotFoundException("No User found with email: " + email);
    }
    return user;
  }

  public User create(CreateUserDTO createUserDTO) {
    boolean emailExists = userRepository.existsByEmail(createUserDTO.getEmail());
    if (emailExists) {
      throw new DuplicateEmailException("Email already in use: " + createUserDTO.getEmail());
    }
    User newUser =
        new User(
            createUserDTO.getFirstName(), createUserDTO.getLastName(), createUserDTO.getEmail());
    return userRepository.save(newUser);
  }

  public void delete(String email) {
    boolean emailExists = userRepository.existsByEmail(email);
    if (!emailExists) {
      throw new UserNotFoundException("No User found with email: " + email);
    }
    userRepository.deleteByEmail(email);
  }

  public User update(String email, UpdateUserDTO updateUserDTO) {
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

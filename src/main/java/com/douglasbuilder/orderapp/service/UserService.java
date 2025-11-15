package com.douglasbuilder.orderapp.service;

import com.douglasbuilder.orderapp.dto.profile.ProfileChangePasswordDTO;
import com.douglasbuilder.orderapp.dto.profile.ProfileResponseDTO;
import com.douglasbuilder.orderapp.dto.profile.ProfileUpdateDTO;
import com.douglasbuilder.orderapp.dto.user.CreateUserDTO;
import com.douglasbuilder.orderapp.dto.user.ResponseUserDTO;
import com.douglasbuilder.orderapp.dto.user.UpdateUserDTO;
import com.douglasbuilder.orderapp.exceptions.auth.AuthInvalidCredentialsException;
import com.douglasbuilder.orderapp.exceptions.user.UserNotFoundException;
import com.douglasbuilder.orderapp.mappers.ProfileMapper;
import com.douglasbuilder.orderapp.mappers.UserMapper;
import com.douglasbuilder.orderapp.model.User;
import com.douglasbuilder.orderapp.repository.UserRepository;
import com.douglasbuilder.orderapp.security.TokenService;
import jakarta.validation.Valid;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Data
@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

  private final UserRepository userRepository;
  private final UserMapper userMapper;
  private final PasswordEncoder passwordEncoder;
  private final InternalUserService internalUserService;
  private final ProfileMapper profileMapper;
  private final AuthenticationManager authenticationManager;
  private final TokenService tokenService;
  private final AuthService authService;

  public List<User> getAll() {
    return userRepository.findAll();
  }

  public boolean emailExists(String email) {
    return userRepository.existsByEmail(email);
  }

  public User create(CreateUserDTO createUserDTO) {

    var user = userMapper.toModel(createUserDTO);

    user.setPassword(passwordEncoder.encode(user.getPassword()));

    return userRepository.save(user);
  }

  public ResponseUserDTO findById(UUID id) {
    var user =
        userRepository
            .findById(id)
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
    User user =
        userRepository
            .findById(id)
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

  // TODO Duplicated with deleteAccount due to new authentication - TO REVIEW
  public void deleteById(UUID id) {
    boolean idExists = userRepository.existsById(id);
    if (!idExists) {
      throw new UserNotFoundException("No User found with ID: " + id);
    }
    userRepository.deleteById(id);
  }

  public void deleteAccount(User user) {
    userRepository.deleteByEmail(user.getEmail());
  }


  public void changePassword(ProfileChangePasswordDTO dto, User user) {

    var userPassword = new UsernamePasswordAuthenticationToken(user.getEmail(), dto.getCurrentPassword());
    authenticationManager.authenticate(userPassword);

    if (passwordEncoder.matches(dto.getNewPassword(), user.getPassword())) {
      throw new AuthInvalidCredentialsException("New password must be different from previous, try again.");
    }

    user.setPassword(passwordEncoder.encode(dto.getNewPassword()));
    userRepository.save(user);
    authService.revokeUserTokens(user);
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return userRepository.findByEmail(username);
  }

  public ProfileResponseDTO getProfile(User user) {
    return profileMapper.toProfile(user);
  }

  private void saveUser(User user) {
    userRepository.save(user);
  }

  public void updateLastLogin(User user) {
    user.setLastLogin(LocalDateTime.now());
    userRepository.save(user);
  }

  public void updateFirstName(@Valid String firstName, User user) {
    user.setFirstName(firstName);
    saveUser(user);
  }

  public void updateLastName(@Valid String lastName, User user) {
    user.setLastName(lastName);
    saveUser(user);
  }

  public void updateEmail(@Valid String email, User user) {
    user.setEmail(email);
    saveUser(user);
    authService.revokeUserTokens(user);
  }

  public void updateProfilePicture(@Valid String url, User user) {
    user.setProfilePicture(url);
    saveUser(user);
  }
}

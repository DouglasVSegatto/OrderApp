package com.douglasbuilder.orderapp.service;

import com.douglasbuilder.orderapp.dto.user.*;
import com.douglasbuilder.orderapp.exceptions.auth.AuthInvalidCredentialsException;
import com.douglasbuilder.orderapp.mappers.AddressMapper;
import com.douglasbuilder.orderapp.mappers.UserMapper;
import com.douglasbuilder.orderapp.model.User;
import com.douglasbuilder.orderapp.repository.TokenRepository;
import com.douglasbuilder.orderapp.repository.UserRepository;
import com.douglasbuilder.orderapp.security.TokenService;
import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

  private final UserRepository userRepository;
  private final UserMapper userMapper;
  private final PasswordEncoder passwordEncoder;
  private final TokenRepository tokenRepository;
  private final CurrentUserService currentUser;
  private final TokenService tokenService;
  private final AddressMapper addressMapper;

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

  public User getUser() {
    return userRepository.findByEmail(currentUser.getCurrentUserEmail());
  }

  public User getUser(String email) {
    return userRepository.findByEmail(email);
  }

  public void deleteAccount() {
    String email = currentUser.getCurrentUserEmail();
    userRepository.deleteByEmail(email);
    tokenService.deleteUSerToken(email);
  }

  // TODO User token still works, FUTURE: to implement isLoggedOut to user to track.
  @Transactional
  public void changePassword(UserChangePasswordDTO dto) {
    User user = getUser();

    if (passwordEncoder.matches(dto.getNewPassword(), user.getPassword())) {
      throw new AuthInvalidCredentialsException(
          "New password must be different from previous, try again.");
    }

    user.setPassword(passwordEncoder.encode(dto.getNewPassword()));
    userRepository.save(user);
    tokenService.deleteUSerToken();
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return userRepository.findByEmail(username);
  }

  private void saveUser(User user) {
    userRepository.save(user);
  }

  public void recordLogin(User user) {
    user.setLastLogin(LocalDateTime.now());
    userRepository.save(user);
  }

  public void updateFirstName(@Valid String firstName) {
    User user = getUser();
    user.setFirstName(firstName);
    saveUser(user);
  }

  public void updateLastName(@Valid String lastName) {
    User user = getUser();
    user.setLastName(lastName);
    saveUser(user);
  }

  // TODO compare new with previous
  @Transactional
  public void updateEmail(@Valid String email) {
    User user = getUser();
    String previousEmail = user.getEmail();
    user.setEmail(email);
    saveUser(user);
    tokenService.deleteUSerToken(previousEmail);
  }

  public void updateAddress(AddressUpdateDTO dto) {
    User user = getUser();
    var address = addressMapper.toModel(dto);
    user.setAddress(address);
    saveUser(user);
  }

  public void updatePhoneNumber(UpdatePhoneNumberDTO dto) {
    User user = getUser();
    user.setPhoneCountry(dto.getPhoneCountry());
    user.setPhoneNumber(dto.getPhoneNumber());
    saveUser(user);
  }

  public UserProfileDTO getUserProfile() {
    User user = getUser();
    UserProfileDTO dto = userMapper.toProfileDTO(user);
    dto.setMemberSince(user.getCreatedAt());
    dto.setLastLoginAgo(user.getLastLogin());
    return dto;
  }
}

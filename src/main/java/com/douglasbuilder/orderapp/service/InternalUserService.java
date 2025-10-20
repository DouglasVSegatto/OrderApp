package com.douglasbuilder.orderapp.service;

import com.douglasbuilder.orderapp.exceptions.user.UserNotFoundException;
import com.douglasbuilder.orderapp.model.User;
import com.douglasbuilder.orderapp.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class InternalUserService {

    private final UserRepository userRepository;

    public User findById(UUID id){
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("ID: " + id));
    }
}

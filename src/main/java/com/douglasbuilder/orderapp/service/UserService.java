package com.douglasbuilder.orderapp.service;

import com.douglasbuilder.orderapp.dto.user.CreateUserDTO;
import com.douglasbuilder.orderapp.dto.user.UpdateUserDTO;
import com.douglasbuilder.orderapp.exceptions.user.DuplicateEmailException;
import com.douglasbuilder.orderapp.exceptions.user.UserNotFoundException;
import com.douglasbuilder.orderapp.model.User;
import com.douglasbuilder.orderapp.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Data
@Service
public class UserService {
    List<User> usersList = new ArrayList<>();

    @Autowired
    private UserRepository userRepository;

    public UserService() {
        usersList.add(new User("Doug", "Test", "test1@g.com"));
        usersList.add(new User("Wes", "Test2","test2@g.com"));
        usersList.add(new User("Nois", "Test3","test3@g.com"));

    }

    public List<User> getAll(){
        return userRepository.findAll();
    }

    public User create(CreateUserDTO createUserDTO){
        boolean emailExists = userRepository.existsByEmail(createUserDTO.getEmail());
        if (emailExists){
            // To create a custom exception
            throw new DuplicateEmailException("Email already in use: "+createUserDTO.getEmail());
        }
        User newUser = new User(
                createUserDTO.getFirstName(),
                createUserDTO.getLastName(),
                createUserDTO.getEmail()
                );
        return userRepository.save(newUser);

    }

    public User getUserById(Long id){
        return usersList.stream().filter(user -> user.getId().equals(id)).findFirst().orElse(null);
    }

    public void delete(String email){
        boolean emailExists = userRepository.existsByEmail(email);
        if (!emailExists){
            throw new UserNotFoundException("No User found with email: "+ email);
        }
        userRepository.deleteByEmail(email);
    }

    public User update(String email, UpdateUserDTO updateUserDTO){
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UserNotFoundException("No User found with email: "+ email);
        }

        if (updateUserDTO.getFirstName() != null){
            user.setFirstName(updateUserDTO.getFirstName());
        }
        if (updateUserDTO.getLastName() != null){
            user.setLastName(updateUserDTO.getLastName());
        }
        return userRepository.save(user);
    }
}

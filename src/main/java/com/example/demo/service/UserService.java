package com.example.demo.service;

import com.example.demo.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Data
@Service
public class UserService {
    List<User> usersList = new ArrayList<>();

    public UserService() {
        usersList.add(new User(2L,"Doug", "Test"));
        usersList.add(new User(3L,"Wes", "Test2"));
        usersList.add(new User(4L,"Nois", "Test3"));
    }

    public List<User> getAll(){
        return usersList;
    }

    public User getUserById(Long id){
        return usersList.stream().filter(user -> user.getId().equals(id)).findFirst().orElse(null);
    }

    public void removeUserById(Long id){
        usersList.removeIf(user -> user.getId().equals(id));
    }
}

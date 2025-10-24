package com.douglasbuilder.orderapp.controller;

import com.douglasbuilder.orderapp.dto.api.ApiResponse;
import com.douglasbuilder.orderapp.dto.user.CreateUserDTO;
import com.douglasbuilder.orderapp.dto.user.ResponseUserDTO;
import com.douglasbuilder.orderapp.dto.user.UpdateUserDTO;
import com.douglasbuilder.orderapp.model.User;
import com.douglasbuilder.orderapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /*
    UserController:
    GET /user/{id}        (requires JWT)
    PUT /user/{id}        (requires JWT)
    DELETE /user/{id}     (requires JWT)
     */

    @GetMapping
    public List<User> getUsers() {
        return userService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ResponseUserDTO>> findUserById(@PathVariable UUID id) {
        var user = userService.findById(id);

        //TODO criei esse ApiResponse para padronizar as respostas da API
        return ResponseEntity.ok().body(new ApiResponse<>(user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUserById(@PathVariable UUID id) {
        userService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUserById(@PathVariable UUID id, @RequestBody UpdateUserDTO updateUserDTO) {
        userService.updateById(id, updateUserDTO);
        return ResponseEntity.ok().build();
    }
}

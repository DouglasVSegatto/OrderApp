package com.douglasbuilder.orderapp.repository;

import com.douglasbuilder.orderapp.model.User;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

  boolean existsByEmail(@NonNull String email);

  void deleteByEmail(@NonNull String email);

  User findByEmail(String email);

}

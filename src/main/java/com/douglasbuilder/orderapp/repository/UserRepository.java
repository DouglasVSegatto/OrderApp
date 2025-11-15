package com.douglasbuilder.orderapp.repository;

import com.douglasbuilder.orderapp.model.User;
import java.util.UUID;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

  boolean existsByEmail(@NonNull String email);

  User findByEmail(String email);

  void deleteByEmail(String email);

}

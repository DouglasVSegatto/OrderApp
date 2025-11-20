package com.douglasbuilder.orderapp.repository;

import com.douglasbuilder.orderapp.model.Token;
import com.douglasbuilder.orderapp.model.User;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepository extends JpaRepository<Token, UUID> {
  void deleteAllByUserEmail(String email);

  boolean existsByToken(String token);

  List<Token> findAllByUser(User user);
  Optional<Token> findByToken(String token);
}

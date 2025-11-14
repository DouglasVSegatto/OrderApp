package com.douglasbuilder.orderapp.repository;

import com.douglasbuilder.orderapp.model.Token;
import com.douglasbuilder.orderapp.model.User;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepository extends JpaRepository<Token, UUID> {
  void deleteAllByUser(User user);
}

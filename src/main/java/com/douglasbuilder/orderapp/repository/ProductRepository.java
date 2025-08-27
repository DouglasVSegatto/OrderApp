package com.douglasbuilder.orderapp.repository;

import com.douglasbuilder.orderapp.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
  boolean existsByName(String name);
}
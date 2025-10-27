package com.douglasbuilder.orderapp.repository;

import com.douglasbuilder.orderapp.model.Product;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {
  boolean existsBySku(String sku);

  List<Product> getAllByAvailableTrue();
}

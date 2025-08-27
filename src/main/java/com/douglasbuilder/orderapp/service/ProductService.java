package com.douglasbuilder.orderapp.service;

import com.douglasbuilder.orderapp.dto.product.CreateProductDTO;
import com.douglasbuilder.orderapp.dto.product.UpdateProductDTO;
import com.douglasbuilder.orderapp.exceptions.product.DuplicateNameException;
import com.douglasbuilder.orderapp.exceptions.product.ProductNotFoundException;
import com.douglasbuilder.orderapp.exceptions.user.UserNotFoundException;
import com.douglasbuilder.orderapp.model.Product;

import java.util.List;

import com.douglasbuilder.orderapp.repository.ProductRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
@Data
public class ProductService {

  @Autowired private ProductRepository productRepository;
  
  public List<Product> getAll() { return productRepository.findAll();}

  public Product find(Long id) {
    return productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException("Product not found with ID: " + id));
  }

  public void delete(Long id) {
    if (!productRepository.existsById(id)){
      throw new ProductNotFoundException("Product not found with ID: " + id);
    }
    productRepository.deleteById(id);
  }

  public Product create(CreateProductDTO createProductDTO) {
    if (productRepository.existsByName(createProductDTO.getName())){
      throw new DuplicateNameException("Product name already registered. Name: " + createProductDTO.getName());
    }
    return productRepository.save(new Product(
            createProductDTO.getName(),
            createProductDTO.getType(),
            createProductDTO.getQuantity(),
            createProductDTO.getPrice(),
            createProductDTO.isAvailable()
    ));
  }

  public Product update(Long id, UpdateProductDTO updateProductDTO) {
    Product product = productRepository.findById(id).orElseThrow(() -> new UserNotFoundException("Product ID not found"));

    if (updateProductDTO.getType() != null){
      product.setType(updateProductDTO.getType());
    }
    if (updateProductDTO.getQuantity() != null){
      product.setQuantity(updateProductDTO.getQuantity());
    }
    if (updateProductDTO.getPrice() != null){
      product.setPrice(updateProductDTO.getPrice());
    }
    if (updateProductDTO.isAvailable() != product.isAvailable()){
      product.setAvailable(updateProductDTO.isAvailable());
    }
    return productRepository.save(product);
  }

}

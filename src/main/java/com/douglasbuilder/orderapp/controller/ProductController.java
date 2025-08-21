package com.douglasbuilder.orderapp.controller;

import com.douglasbuilder.orderapp.dto.product.CreateProductDTO;
import com.douglasbuilder.orderapp.dto.product.ProductResponseDTO;
import com.douglasbuilder.orderapp.dto.product.ReplaceProductDTO;
import com.douglasbuilder.orderapp.dto.product.UpdateProductDTO;
import com.douglasbuilder.orderapp.exceptions.ProductNotFoundException;
import com.douglasbuilder.orderapp.model.Product;
import com.douglasbuilder.orderapp.service.ProductService;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product")
public class ProductController {

  private ProductService productService;

  public ProductController(ProductService productService) {
    this.productService = productService;
  }

  @GetMapping
  public List<Product> getAll() {
    return productService.getAll();
  }

  @GetMapping("/{id}")
  public ResponseEntity<ProductResponseDTO> getProduct(@PathVariable Long id)
      throws ProductNotFoundException {
    Product productFound = productService.getProductById(id);

    if (productFound == null) {
      throw new ProductNotFoundException(id);
    }
    ProductResponseDTO requestProduct = new ProductResponseDTO();
    requestProduct.setId(id);
    requestProduct.setName(productFound.getName());
    requestProduct.setPrice(productFound.getPrice());
    requestProduct.setAvailable(productFound.isAvailable());
    return ResponseEntity.ok(requestProduct);
  }

  @PostMapping
  public ResponseEntity<Product> createProduct(@RequestBody CreateProductDTO createProduct) {
    Product product =
        new Product(
            createProduct.getName(),
            createProduct.getType(),
            createProduct.getQuantity(),
            createProduct.getPrice(),
            createProduct.isAvailable());
    Product productSaved = productService.addProduct(product);
    return ResponseEntity.status(201).body(productSaved);
  }

  @PatchMapping("/{id}")
  public ResponseEntity<Product> updateProduct(
      @PathVariable Long id, @RequestBody UpdateProductDTO updateProductDTO)
      throws ProductNotFoundException {
    Product product =
        productService.updateProductById(
            id, updateProductDTO.getField(), updateProductDTO.getValue());
    return ResponseEntity.status(201).body(product);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Product> putProduct(
      @PathVariable Long id, @RequestBody ReplaceProductDTO replaceProductDTO)
      throws ProductNotFoundException {
    Product product = productService.getProductById(id);

    if (product == null) {
      throw new ProductNotFoundException(id);
    }
    Product updatedProduct = new Product();
    updatedProduct.setName(replaceProductDTO.getName());
    updatedProduct.setType(replaceProductDTO.getType());
    updatedProduct.setQuantity(replaceProductDTO.getQuantity());
    updatedProduct.setPrice(replaceProductDTO.getPrice());
    updatedProduct.setAvailable(replaceProductDTO.isAvailable());
    updatedProduct.setId(id);

    productService.replaceProduct(id, updatedProduct);
    return ResponseEntity.ok(product);
  }
}

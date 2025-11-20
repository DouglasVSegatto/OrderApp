package com.douglasbuilder.orderapp.controller;

import com.douglasbuilder.orderapp.dto.api.ApiResponse;
import com.douglasbuilder.orderapp.dto.product.CreateProductDTO;
import com.douglasbuilder.orderapp.dto.product.UpdateProductDTO;
import com.douglasbuilder.orderapp.service.ProductService;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {

  private final ProductService productService;

  @GetMapping
  public ResponseEntity<ApiResponse<Object>> getAll() {
    var products = productService.getAll();
    return ResponseEntity.ok(new ApiResponse<>(products));
  }

  @GetMapping("/catalog")
  public ResponseEntity<ApiResponse<Object>> getCatalog() {
    var products = productService.getCatalog();
    return ResponseEntity.ok(new ApiResponse<>(products));
  }

  @GetMapping("/{id}")
  public ResponseEntity<ApiResponse<Object>> find(@PathVariable UUID id) {
    Object product = productService.find(id);
    return ResponseEntity.ok(new ApiResponse<>(product));
  }

  @PostMapping
  public ResponseEntity<ApiResponse<Object>> create(
      @RequestBody CreateProductDTO createProductDTO) {
    Object product = productService.create(createProductDTO);
    return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>(product));
  }

  @PutMapping("/{id}")
  public ResponseEntity<ApiResponse<Object>> update(
      @PathVariable UUID id, @RequestBody UpdateProductDTO updateProductDTO) {
    Object product = productService.update(id, updateProductDTO);
    return ResponseEntity.ok(new ApiResponse<>(product));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<ApiResponse<Object>> delete(@PathVariable UUID id) {
    productService.delete(id);
    return ResponseEntity.ok(new ApiResponse<>());
  }
}

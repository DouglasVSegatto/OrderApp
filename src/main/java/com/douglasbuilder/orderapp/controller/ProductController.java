package com.douglasbuilder.orderapp.controller;

import com.douglasbuilder.orderapp.dto.api.ApiResponse;
import com.douglasbuilder.orderapp.dto.product.CreateProductDTO;
import com.douglasbuilder.orderapp.dto.product.UpdateProductDTO;
import com.douglasbuilder.orderapp.model.Product;
import com.douglasbuilder.orderapp.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<?> getAll() {
        var products = productService.getAll();
        return ResponseEntity.ok().body(new ApiResponse<>(products));
    }

    @GetMapping("/catalog")
    public ResponseEntity<?> getCatalog() {
        var products = productService.getCatalog();
        return ResponseEntity.ok().body(new ApiResponse<>(products));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> find(@PathVariable Long id) {
        Product product = productService.find(id);
        return ResponseEntity.ok(product);
    }

    @PostMapping
    public ResponseEntity<Product> create(@RequestBody CreateProductDTO createProductDTO) {
        Product product = productService.create(createProductDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(product);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> update(@PathVariable Long id, @RequestBody UpdateProductDTO updateProductDTO) {
        Product product = productService.update(id, updateProductDTO);
        return ResponseEntity.status(HttpStatus.OK).body(product);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        productService.delete(id);
        return ResponseEntity.ok().build();
    }
}

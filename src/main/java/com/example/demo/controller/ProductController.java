package com.example.demo.controller;

import com.example.demo.model.Product;
import com.example.demo.model.dto.ReplaceProduct;
import com.example.demo.model.dto.RequestProduct;
import com.example.demo.model.dto.UpdateProduct;
import com.example.demo.service.ProductNotFoundException;
import com.example.demo.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    private ProductService productService;

    public ProductController(ProductService productService){this.productService = productService; }

    @GetMapping
    public List<Product> getAll(){
        return productService.getAll();
    }

    @PostMapping
    public ResponseEntity<Product> addProduct(@RequestBody RequestProduct request){
        Product product = new Product(request.getName(), request.getType(), request.getQuantity());
        Product productSaved = productService.addProduct(product);
        return ResponseEntity.status(201).body(productSaved);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody UpdateProduct updateProduct) throws ProductNotFoundException {
        Product product = productService.updateProductById(id, updateProduct.getField(), updateProduct.getValue());
        return ResponseEntity.status(201).body(product);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> putProduct(@PathVariable Long id, @RequestBody ReplaceProduct replaceProduct) throws ProductNotFoundException {
        Product product = productService.getProductById(id);

        if (product == null){
            throw new ProductNotFoundException(id);
        }
        Product updatedProduct = new Product();
        updatedProduct.setName(replaceProduct.getName());
        updatedProduct.setType(replaceProduct.getType());
        updatedProduct.setQuantity(replaceProduct.getQuantity());
        updatedProduct.setId(id);

        productService.replaceProduct(id, updatedProduct);
        return ResponseEntity.ok(product);
    }
}



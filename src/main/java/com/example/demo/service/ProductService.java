package com.example.demo.service;

import com.example.demo.exceptions.ProductNotFoundException;
import com.example.demo.model.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
@Data
public class ProductService {

    List<Product> productList = new ArrayList<>();

    public ProductService(){
        productList.add(new Product("Blusa", "Roupa",0, new BigDecimal("10.99"), false));
        productList.add(new Product("Calca", "Roupa",0, new BigDecimal("9.99"), false));
        productList.add(new Product("Teclado", "Eletronico",10,new BigDecimal("50.00"), true));
    }

    public List<Product> getAll(){
        return productList;
    }

    public Product getProductById(Long id){
        return productList.stream().filter(product -> product.getId().equals(id)).findFirst().orElse(null);
    }

    public void removeProductById(Long id){
        productList.removeIf(product -> product.getId().equals(id));
    }

    public Product addProduct(Product product){
        productList.add(product);
        return product;
    }

    public Product replaceProduct(Long id, Product replaceProduct) throws ProductNotFoundException {
        Product foundProduct = getProductById(id);
        if (foundProduct == null){
            throw new ProductNotFoundException(id);
        }
        productList.set(Math.toIntExact(id-1),replaceProduct);
        return replaceProduct;
    }

    public Product updateProductById(Long id, String field, Object value) throws ProductNotFoundException {
        Product product = getProductById(id);

        if (product == null){
            throw new ProductNotFoundException(id);
        }

        switch (field.toLowerCase()) {
            case "name":
                product.setName((String) value);
                break;
            case "type":
                product.setType((String) value);
                break;
            case "quantity":
                product.setQuantity(Integer.parseInt((String )value));
                break;
            default:
                throw new IllegalArgumentException("Invalid field: " + field);
        }
        return product;
    }
}

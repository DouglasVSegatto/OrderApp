package com.douglasbuilder.orderapp.service;

import com.douglasbuilder.orderapp.dto.product.CreateProductDTO;
import com.douglasbuilder.orderapp.dto.product.UpdateProductDTO;
import com.douglasbuilder.orderapp.exceptions.product.DuplicateNameException;
import com.douglasbuilder.orderapp.exceptions.product.ProductNotFoundException;
import com.douglasbuilder.orderapp.exceptions.user.UserNotFoundException;
import com.douglasbuilder.orderapp.model.Product;
import com.douglasbuilder.orderapp.repository.ProductRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Data
@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public List<Product> getAll() {
        return productRepository.findAll();
    }

    public Product find(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException("Product not found with ID: " + id));
    }

    public void delete(Long id) {
        if (!productRepository.existsById(id)) {
            throw new ProductNotFoundException("Product not found with ID: " + id);
        }
        productRepository.deleteById(id);
    }

    public Product create(CreateProductDTO createProductDTO) {

        if (productRepository.existsBySku(createProductDTO.getSku())) {
            throw new DuplicateNameException("Product name already registered. Name: " + createProductDTO.getName());
        }

        //TODO refactor to use mapper like MapStruct UserService
        var newProduct = new Product();
        newProduct.setName(createProductDTO.getName());
        newProduct.setSku(createProductDTO.getSku());
        newProduct.setType(createProductDTO.getType());
        newProduct.setQuantityInStock(createProductDTO.getQuantity());
        newProduct.setPrice(createProductDTO.getPrice());
        newProduct.setAvailable(createProductDTO.getAvailable());

        return productRepository.save(newProduct);
    }

    public Product update(Long id, UpdateProductDTO updateProductDTO) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Product ID not found"));

        if (updateProductDTO.getType() != null) {
            product.setType(updateProductDTO.getType());
        }
        if (updateProductDTO.getQuantity() != null) {
            product.setQuantityInStock(updateProductDTO.getQuantity());
        }
        if (updateProductDTO.getPrice() != null) {
            product.setPrice(updateProductDTO.getPrice());
        }

        //TODO só para verificar o comentatio
        // não deveria ser nesse momento essa alteração, poderiamos criar uma propriedade nova status Ativo/Inativo
        // a propriedade available deveria ser alterada somente após a finalização de uma venda,
        // validando se a quantidade em estoque é maior que zero ou não
        // mas vamos conversar sobre isso depois
        if (updateProductDTO.getAvailable() != product.getAvailable()) {
            product.setAvailable(!product.getAvailable());
        }

        return productRepository.save(product);
    }

}

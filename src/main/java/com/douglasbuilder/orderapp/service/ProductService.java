package com.douglasbuilder.orderapp.service;

import com.douglasbuilder.orderapp.dto.product.CreateProductDTO;
import com.douglasbuilder.orderapp.dto.product.UpdateProductDTO;
import com.douglasbuilder.orderapp.exceptions.product.DuplicateNameException;
import com.douglasbuilder.orderapp.exceptions.product.ProductInsufficientStockException;
import com.douglasbuilder.orderapp.exceptions.product.ProductNotFoundException;
import com.douglasbuilder.orderapp.exceptions.user.UserNotFoundException;
import com.douglasbuilder.orderapp.mappers.ProductMapper;
import com.douglasbuilder.orderapp.model.Product;
import com.douglasbuilder.orderapp.repository.ProductRepository;
import java.util.List;
import java.util.UUID;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Data
@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public List<Product> getAll() {
        return productRepository.findAll();
    }

    public List<Product> getCatalog() {
        return productRepository.getAllByAvailableTrue();
    }

    public Product find(UUID id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with ID: " + id));
    }

    public void delete(UUID id) {
        if (!productRepository.existsById(id)) {
            throw new ProductNotFoundException("Product not found with ID: " + id);
        }
        productRepository.deleteById(id);
    }

    public Product create(CreateProductDTO createProductDTO) {

        if (productRepository.existsBySku(createProductDTO.getSku())) {
            throw new DuplicateNameException("SKU: " + createProductDTO.getSku());
        }

        var newProduct = productMapper.toModel(createProductDTO);

        return productRepository.save(newProduct);
    }

    public Product update(UUID id, UpdateProductDTO updateProductDTO) {
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

    public void reduceStock(UUID productId, Integer quantity){
        Product product = find(productId);

        if (!product.getAvailable()){
            throw new ProductInsufficientStockException("Product is not available");
        }

        if (product.getQuantityInStock() < quantity){
            throw new ProductInsufficientStockException("Product insufficient stock");
        }

        product.setQuantityInStock(product.getQuantityInStock() - quantity);

        if (product.getQuantityInStock() == 0){
            product.setAvailable(false);
        }

        productRepository.save(product);
  }
}

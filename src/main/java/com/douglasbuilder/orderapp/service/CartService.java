package com.douglasbuilder.orderapp.service;

import com.douglasbuilder.orderapp.exceptions.cart.CartNotFoundException;
import com.douglasbuilder.orderapp.exceptions.cartitem.CartItemNotFoundException;
import com.douglasbuilder.orderapp.exceptions.cartitem.CartItemProductAlreadyExists;
import com.douglasbuilder.orderapp.exceptions.product.ProductNotFoundException;
import com.douglasbuilder.orderapp.mappers.ProductMapper;
import com.douglasbuilder.orderapp.model.Cart;
import com.douglasbuilder.orderapp.model.CartItem;
import com.douglasbuilder.orderapp.model.Product;
import com.douglasbuilder.orderapp.model.User;
import com.douglasbuilder.orderapp.repository.CartItemRepository;
import com.douglasbuilder.orderapp.repository.CartRepository;
import com.douglasbuilder.orderapp.repository.ProductRepository;
import jakarta.validation.constraints.Null;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Data
@Service
@AllArgsConstructor
public class CartService {

    public final CartRepository cartRepository;
    public final CartItemRepository cartItemRepository;
    public final UserService userService;
    public final InternalUserService internalUserService;
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public Cart getOrCreateCart(Long userId){
        Cart userCart = cartRepository.findByUserId(userId);
        if (userCart != null){
            return userCart;
        }

        Cart newCart = new Cart();
        newCart.setUser(internalUserService.findById(userId));
        newCart.setCartItems(new ArrayList<>());
        return cartRepository.save(newCart);
    }

    public Cart getCartByUserId(Long userId){
        Cart userCart = cartRepository.findByUserId(userId);
        System.out.println("CART: " + userCart);
        if (userCart == null ){
            throw new CartNotFoundException("ID: " + userId);
        }
        return userCart;
    }

    public void addItemToCart(Long userId, Long productId) {
        Cart userCart = getOrCreateCart(userId);

        //TODO improve
        if (cartItemRepository.existsByCart_IdAndProduct_Id(userCart.getId(), productId)){
            throw new CartItemProductAlreadyExists("Product already in the cart");

        }

        Product product = productRepository.findById(productId).orElseThrow(() -> new ProductNotFoundException("ID: " + productId));
        System.out.println("PRODUCT: " + product);
        CartItem newCartItem = new CartItem();
        newCartItem.setProduct(product);
        newCartItem.setQuantity(1);
        newCartItem.setPrice(product.getPrice());

        //AQUI EU ADICIONO NO CARTITEMREPOSITORY
        // MAS QUANDO EU USO O GET ELE NAO PEGAR DE LA
        //ELE PEGA O USUARIO ENTAO EU PRECISO:
        // * fazer um  add no usualrio para adicionar a lista nessa parte do codigo tipo usercart.add(caritem)
        // * fazer um get do repository quando eu chamo la no controller para pegar o cart do usuario
        // tipo usuario ta aqui, agora pego do repositoru tudo dele e aditiono a lista e entrego akgi assun,

        cartItemRepository.save(newCartItem);
    }

    public void deleteItemFromCart(User user, Long id) {
        if (!cartItemRepository.existsById(id)){
            throw new CartItemNotFoundException("Cart Item not found with ID: " + id);
        }
        cartItemRepository.deleteCartItemById(id);
    }

}

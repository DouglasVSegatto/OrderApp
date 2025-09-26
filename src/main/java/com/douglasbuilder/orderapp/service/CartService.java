package com.douglasbuilder.orderapp.service;

import com.douglasbuilder.orderapp.exceptions.cart.CartNotFoundException;
import com.douglasbuilder.orderapp.exceptions.cartitem.CartItemNotFoundException;
import com.douglasbuilder.orderapp.exceptions.cartitem.CartItemProductAlreadyExists;
import com.douglasbuilder.orderapp.exceptions.product.ProductNotFoundException;
import com.douglasbuilder.orderapp.exceptions.user.UserNotFoundException;
import com.douglasbuilder.orderapp.model.Cart;
import com.douglasbuilder.orderapp.model.CartItem;
import com.douglasbuilder.orderapp.model.Product;
import com.douglasbuilder.orderapp.repository.CartItemRepository;
import com.douglasbuilder.orderapp.repository.CartRepository;
import com.douglasbuilder.orderapp.repository.ProductRepository;
import com.douglasbuilder.orderapp.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Data
@Slf4j
@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public Cart getCartByUserId(Long userId) {
        Cart userCart = cartRepository.findByUserId(userId);
        if (userCart == null) {
            throw new CartNotFoundException("ID: " + userId);
        }
        return userCart;
    }

    //TODO Consideramos que sistema so da opcao se o product ta available ou tem quantidade? fazer validacao?
    //TODO Answer - pode validar se o o produto esta available(no repository crie um metodo fin by id e avaliable), porem a quantidade so faz sentido validar no momento do checkout(futuras implementacoes)
    //TODO Criar um method que da a resposta parece mais viavel como seria checado varias vezes
    //TODO Answer - sim, sempre que for implementar uma nova funcionalidade pense que ela pode ser reutilizada em outras partes do sistema
    public void addItem(Long userId, Long productId) {

        Product product = productRepository.findById(productId).orElseThrow(() -> new ProductNotFoundException("ID: " + productId));

        Cart cart = getOrCreateCart(userId);

        cart.getCartItems().forEach(item -> {
            if (item.getProduct().getId().equals(productId)) {
                throw new CartItemProductAlreadyExists("Product already in the cart");
            }
        });

        var cartItem = CartItem.builder()
                .product(product)
                .quantity(1)
                .price(product.getPrice())
                .cart(cart)
                .build();

        cart.getCartItems().add(cartItem);

        cartRepository.save(cart);
    }


    //TODO Return product quantity
    //TODO Answer - não entendi esse TODO
    public void deleteItem(Long userId, Long itemId) {
        Cart cart = getCartByUserId(userId);

        CartItem cartItem = cart.getCartItems().stream()
                .filter(item -> item.getId().equals(itemId))
                .findFirst().orElseThrow(() -> new CartItemNotFoundException("ID: " + itemId));

        cart.getCartItems().remove(cartItem);
        cartRepository.save(cart);

    }

    private Cart getOrCreateCart(Long userId) {
        Cart cart = cartRepository.findByUserId(userId);
        if (cart != null) {
            return cart;
        }

        var user = userRepository.getReferenceById(userId);

        Cart newCart = new Cart();
        newCart.setUser(user);
        newCart.setCartItems(new ArrayList<>());
        return cartRepository.save(newCart);
    }

    //TODO Restore products quantity (if any)
    //TODO Answer - nessa classe não estamos lidando com a quantidade do produto, apenas adicionando ou removendo do carrinho. A quantidade do produto deve ser gerenciada em outro lugar, como no momento do checkout ou na gestão de estoque.
    @Transactional
    public void deleteCart(Long userId){
        if (!cartRepository.existsByUserId(userId)){
            throw new UserNotFoundException("ID: " + userId);
        }
        cartRepository.deleteCartByUser_Id(userId);
    }

    //TODO Validate product available and quantity
    //TODO Answer - pode validar se o o produto esta available(no repository crie um metodo fin by id e avaliable), porem a quantidade so faz sentido validar no momento do checkout(futuras implementacoes)
    @Transactional
    public CartItem updateCartItem(Long userId, Long itemId, Integer quantity){
        //TODO tente não reutilizar metodos publicos dentro de outros metodos publicos, crie um metodo privado para isso, ex: crie um metodo privado getCartIfExists ou outro nome
        //TODO pq caso mude algo nesse metodo publico, pode impactar outros metodos publicos que o utilizam
        Cart cart = getCartByUserId(userId);

        CartItem cartItem = cart.getCartItems().stream()
                .filter(item -> item.getId().equals(itemId))
                .findFirst().orElseThrow(() -> new CartItemNotFoundException("ID: " + itemId));

        cartItem.setQuantity(quantity);
        cartRepository.save(cart);
        return cartItem;
    }

}

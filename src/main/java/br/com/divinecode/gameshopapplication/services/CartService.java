package br.com.divinecode.gameshopapplication.services;

import br.com.divinecode.gameshopapplication.domain.cart.Cart;
import br.com.divinecode.gameshopapplication.domain.product.Product;
import br.com.divinecode.gameshopapplication.domain.user.User;
import br.com.divinecode.gameshopapplication.dto.CartDTO.CartDTO;
import br.com.divinecode.gameshopapplication.dto.userDTO.UserDTO;
import br.com.divinecode.gameshopapplication.exceptions.CartNotFoundException;
import br.com.divinecode.gameshopapplication.repositories.CartRepository;
import br.com.divinecode.gameshopapplication.repositories.ProductRepository;
import br.com.divinecode.gameshopapplication.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    public Cart findById(Long cartId) {
        Cart cartById = cartRepository.findById(cartId).orElseThrow(() -> new RuntimeException("Cart not found"));

        return cartById;
    }

    public Cart addProductToCart(Long cartId, CartDTO cartDTO) {
        try {
            Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new CartNotFoundException("Usuário não foi encontrado"));
            List<Product> allProductsById = productRepository.findAllById(cartDTO.getProducts());
            if (Objects.nonNull(cart)) {
                cart.setProducts(allProductsById);
                return cartRepository.save(cart);
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro ao adicionar produtos no carrinho");
        }
        return null;
    }

    public Cart removeProductCart(Long cartId, CartDTO cartDTO) {
        try {
            Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new CartNotFoundException("Usuário não foi encontrado"));
            List<Product> allProductsById = productRepository.findAllById(cartDTO.getProducts());
            if (Objects.nonNull(cart)) {
                cart.getProducts().removeAll(allProductsById);
                return cartRepository.saveAndFlush(cart);
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro ao adicionar produtos no carrinho");
        }
        return null;
    }
}

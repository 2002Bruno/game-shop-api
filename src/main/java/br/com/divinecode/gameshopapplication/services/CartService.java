package br.com.divinecode.gameshopapplication.services;

import br.com.divinecode.gameshopapplication.domain.cart.Cart;
import br.com.divinecode.gameshopapplication.domain.product.Product;
import br.com.divinecode.gameshopapplication.domain.user.User;
import br.com.divinecode.gameshopapplication.dto.CartDTO.CartDTO;
import br.com.divinecode.gameshopapplication.dto.userDTO.UserDTO;
import br.com.divinecode.gameshopapplication.repositories.CartRepository;
import br.com.divinecode.gameshopapplication.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserRepository userRepository;

    public Cart findById(Long id) {
        Cart cartById = cartRepository.findById(id).orElseThrow(() -> new RuntimeException("Cart not found"));

        return cartById;
    }

    public Cart addProductToCart(UserDTO userDTO, List<Product> productsIds) {
        User userByEmail = userRepository.findByEmail(userDTO.getEmail());

        if (Objects.nonNull(userByEmail.getCart())) {
            Cart cart = userByEmail.getCart();
            CartDTO cartDTO = new CartDTO();
            cartDTO.setId(cart.getId());
            cartDTO.setUserId(userByEmail.getId());
            cartDTO.setProducts(productsIds);

            ModelMapper mapper = new ModelMapper();
            Cart cartMapped = mapper.map(cartDTO, Cart.class);

            return cartRepository.save(cartMapped);
        }
        return null;
    }
}

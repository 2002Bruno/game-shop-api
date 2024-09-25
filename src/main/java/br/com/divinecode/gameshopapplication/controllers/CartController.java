package br.com.divinecode.gameshopapplication.controllers;

import br.com.divinecode.gameshopapplication.domain.cart.Cart;
import br.com.divinecode.gameshopapplication.domain.product.Product;
import br.com.divinecode.gameshopapplication.domain.user.User;
import br.com.divinecode.gameshopapplication.dto.userDTO.UserDTO;
import br.com.divinecode.gameshopapplication.services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @GetMapping("/{id}")
    public ResponseEntity<?> findCartById(@PathVariable("id") Long id) {
        Cart byId = cartService.findById(id);
        return ResponseEntity.ok(byId);
    }

    @PostMapping("/{productId}")
    public ResponseEntity<?> findCartById(@PathVariable("productId") List<Product> productsIds, @RequestBody UserDTO userDTO) {
        Cart cart = cartService.addProductToCart(userDTO, productsIds);

        return ResponseEntity.ok(cart);
    }
}

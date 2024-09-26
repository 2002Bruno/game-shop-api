package br.com.divinecode.gameshopapplication.controllers;

import br.com.divinecode.gameshopapplication.domain.cart.Cart;
import br.com.divinecode.gameshopapplication.dto.CartDTO.CartDTO;
import br.com.divinecode.gameshopapplication.exceptions.CartNotFoundException;
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

    @GetMapping("/{cartId}")
    public ResponseEntity<?> findCartById(@PathVariable("cartId") Long cartId) {
        try {
            Cart byId = cartService.findById(cartId);
            return ResponseEntity.ok().body(byId);
        } catch (Exception e) {
            throw new CartNotFoundException("Cart não encontrado");
        }
    }

    @PostMapping("/add-product/{cartId}")
    public ResponseEntity<?> addProductToCart(@PathVariable("cartId") Long cartId, @RequestBody CartDTO cartDTO) {
        Cart cart = cartService.addProductToCart(cartId, cartDTO);

        return ResponseEntity.ok(cart);
    }

    @PutMapping("/remove-product/{cartId}")
    public ResponseEntity<?> removeProductToCart(@PathVariable("cartId") Long cartId, @RequestBody CartDTO cartDTO) {
        Cart cart = cartService.removeProductCart(cartId, cartDTO);

        return ResponseEntity.ok(cart);
    }
}

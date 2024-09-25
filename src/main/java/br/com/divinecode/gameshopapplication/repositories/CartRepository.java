package br.com.divinecode.gameshopapplication.repositories;

import br.com.divinecode.gameshopapplication.domain.cart.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
}

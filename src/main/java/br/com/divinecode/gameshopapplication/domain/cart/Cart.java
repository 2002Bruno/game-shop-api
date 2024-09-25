package br.com.divinecode.gameshopapplication.domain.cart;

import br.com.divinecode.gameshopapplication.domain.product.Product;
import br.com.divinecode.gameshopapplication.domain.user.User;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@Table(name = "cart")
@Entity
public class Cart implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relacionamento de um carrinho para muitos produtos
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Product> products = new ArrayList<>();
}

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

    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "cart_products", // Nome da tabela de junção
            joinColumns = @JoinColumn(name = "cart_id"), // Chave estrangeira para Cart
            inverseJoinColumns = @JoinColumn(name = "product_id") // Chave estrangeira para Product
    )
    private List<Product> products = new ArrayList<>();
}

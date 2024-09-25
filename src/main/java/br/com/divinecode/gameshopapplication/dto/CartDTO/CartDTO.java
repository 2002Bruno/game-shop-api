package br.com.divinecode.gameshopapplication.dto.CartDTO;

import br.com.divinecode.gameshopapplication.domain.product.Product;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class CartDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private List<Long> products;
}

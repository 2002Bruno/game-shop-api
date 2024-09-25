package br.com.divinecode.gameshopapplication.dto.userDTO;

import br.com.divinecode.gameshopapplication.domain.cart.Cart;
import lombok.Data;

import java.io.Serializable;

@Data
public class UserDTO implements Serializable {

    private final long serialVersionUID = 1L;

    private long id;

    private String username;

    private String email;

    private String password;

    private Cart cart;
}

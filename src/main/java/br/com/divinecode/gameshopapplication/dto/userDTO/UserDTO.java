package br.com.divinecode.gameshopapplication.dto.userDTO;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserDTO implements Serializable {

    private final long serialVersionUID = 1L;

    private long id;

    private String username;

    private String email;

    private String password;
}

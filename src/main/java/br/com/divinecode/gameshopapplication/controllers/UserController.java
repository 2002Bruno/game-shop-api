package br.com.divinecode.gameshopapplication.controllers;

import br.com.divinecode.gameshopapplication.dto.userDTO.UserDTO;
import br.com.divinecode.gameshopapplication.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/{userId}")
    public ResponseEntity<UserDTO> findUserById(@PathVariable("userId") Long id) {
        try {
            UserDTO userById = userService.findById(id);
            return ResponseEntity.ok(userById);
        } catch (Exception e) {
            throw new RuntimeException("Usuário não encontrado");
        }
    }
}

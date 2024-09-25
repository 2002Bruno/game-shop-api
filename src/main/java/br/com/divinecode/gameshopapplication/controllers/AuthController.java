package br.com.divinecode.gameshopapplication.controllers;

import br.com.divinecode.gameshopapplication.dto.security.AccountCredentialsDTO;
import br.com.divinecode.gameshopapplication.dto.userDTO.UserDTO;
import br.com.divinecode.gameshopapplication.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping
    public ResponseEntity<?> signin(@RequestBody AccountCredentialsDTO accountCredentialsDTO) {
        try {
            ResponseEntity signin = authService.signin(accountCredentialsDTO);

            return ResponseEntity.ok(signin);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro no signin");
        }
    }

    @PostMapping("/registry")
    public ResponseEntity<?> register(@RequestBody UserDTO userDTO) {
        try {
            authService.register(userDTO);
            return ResponseEntity.ok().body("Registro gravado com sucesso");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Email já existente.");
        }
    }
}

package br.com.divinecode.gameshopapplication.services;

import br.com.divinecode.gameshopapplication.domain.cart.Cart;
import br.com.divinecode.gameshopapplication.domain.user.User;
import br.com.divinecode.gameshopapplication.dto.security.AccountCredentialsDTO;
import br.com.divinecode.gameshopapplication.dto.security.TokenDTO;
import br.com.divinecode.gameshopapplication.dto.userDTO.UserDTO;
import br.com.divinecode.gameshopapplication.repositories.CartRepository;
import br.com.divinecode.gameshopapplication.repositories.UserRepository;
import br.com.divinecode.gameshopapplication.services.security.jwt.TokenProvider;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenProvider tokenProvider;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartService cartService;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private CartRepository cartRepository;

    public AuthService(AuthenticationManager authenticationManager, TokenProvider tokenProvider, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public ResponseEntity signin(AccountCredentialsDTO accountCredentialsDTO) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(accountCredentialsDTO.email(), accountCredentialsDTO.password()));
            User user = userRepository.findByEmail(accountCredentialsDTO.email());

            var tokenResponse = new TokenDTO();

            if (Objects.nonNull(user)) {
                tokenResponse = tokenProvider.createAccessToken(user.getEmail(), user.getRoles());

                return ResponseEntity.ok(tokenResponse);
            } else {
                throw new UsernameNotFoundException("email ou senha não encontrados");
            }
        } catch (Exception e) {
            throw new BadCredentialsException("Email ou senha inválidos");
        }
    }

    public void register(UserDTO userDTO) {
        ModelMapper mapper = new ModelMapper();
        Cart cart = new Cart();

        User byEmail = userRepository.findByEmail(userDTO.getEmail());
        if (Objects.nonNull(byEmail)) {
            throw new RuntimeException("Email já existe.");
        }
        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        userDTO.setCart(cart);

        User userMap = mapper.map(userDTO, User.class);
        userRepository.save(userMap);
    }
}

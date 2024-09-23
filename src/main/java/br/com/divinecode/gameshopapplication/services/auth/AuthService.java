package br.com.divinecode.gameshopapplication.services.auth;

import br.com.divinecode.gameshopapplication.domain.user.User;
import br.com.divinecode.gameshopapplication.dto.security.AccountCredentialsDTO;
import br.com.divinecode.gameshopapplication.dto.security.TokenDTO;
import br.com.divinecode.gameshopapplication.dto.userDTO.UserDTO;
import br.com.divinecode.gameshopapplication.repositories.UserRepository;
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
    private PasswordEncoder passwordEncoder;

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
                tokenResponse = tokenProvider.createAccessToken(user.getUsername(), user.getRoles());

                return ResponseEntity.ok(tokenResponse);
            } else {
                throw new UsernameNotFoundException("email ou senha não encontrados");
            }
        } catch (Exception e) {
            throw new BadCredentialsException("Email ou senha inválidos");
        }
    }

    public void register(UserDTO userDTO) {
        User byEmail = userRepository.findByEmail(userDTO.getEmail());
        if (Objects.nonNull(byEmail)) {
            throw new RuntimeException("Email já existe.");
        }
        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        ModelMapper mapper = new ModelMapper();

        User userMap = mapper.map(userDTO, User.class);
        userRepository.save(userMap);
    }
}

package br.com.divinecode.gameshopapplication.services;

import br.com.divinecode.gameshopapplication.domain.user.User;
import br.com.divinecode.gameshopapplication.dto.userDTO.UserDTO;
import br.com.divinecode.gameshopapplication.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    @Lazy
    PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        try {
            return userRepository.findByEmail(email);
        } catch (Exception e) {
            throw new UsernameNotFoundException("Este email não foi encontrado: " + email);
        }
    }

    public UserDTO findById(Long id) {
        ModelMapper mapper = new ModelMapper();

        Optional<User> byId = userRepository.findById(id);
        UserDTO userMapped = mapper.map(byId, UserDTO.class);

        return userMapped;
    }
}

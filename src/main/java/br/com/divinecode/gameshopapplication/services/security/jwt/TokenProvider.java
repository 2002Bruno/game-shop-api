package br.com.divinecode.gameshopapplication.services.security.jwt;

import br.com.divinecode.gameshopapplication.dto.security.TokenDTO;
import br.com.divinecode.gameshopapplication.exceptions.InvalidJwtAuthenticationException;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class TokenProvider {

    @Value("${api.security.token.secret}")
    private String secretKey = "SaborOverdoseNoYakisoba";

    @Value("${security.jwt.token.expire-length:3600000}")
    private long validInMilliSeconds = 3600000; // Equivale a 1h

    @Autowired
    private UserDetailsService userDetailsService;

    Algorithm algorithm = null;

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
        algorithm = Algorithm.HMAC256(secretKey);
    }

    public TokenDTO createAccessToken(String username, List<String> roles) {
        Date now = new Date();
        Date valid = new Date(now.getTime() + validInMilliSeconds);
        var accessToken = getAccessToken(username, roles, now, valid);
        var refreshToken = getRefreshToken(username, roles, now);
        return new TokenDTO(username, true, now, valid, accessToken, refreshToken);
    }

    private String getAccessToken(String username, List<String> roles, Date now, Date valid) {
        String issuerUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toString();
        return JWT.create()
                .withClaim("roles", roles)
                .withIssuedAt(now)
                .withExpiresAt(valid)
                .withSubject(username)
                .withIssuer(issuerUrl)
                .sign(algorithm)
                .strip();
    }

    public TokenDTO refreshToken(String refreshToken) {
        if (refreshToken.contains("")) refreshToken = refreshToken.substring("Bearer ".length());
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT decodedJWT = verifier.verify(refreshToken);
        String username = decodedJWT.getSubject();
        List<String> roles = decodedJWT.getClaim("roles").asList(String.class);
        return createAccessToken(username, roles);
    }

    public String getRefreshToken(String username, List<String> roles, Date now) {
        Date validRefreshToken = new Date(now.getTime() + validInMilliSeconds);
        String issuerUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toString();
        return JWT.create()
                .withClaim("roles", roles)
                .withIssuedAt(now)
                .withExpiresAt(validRefreshToken)
                .withSubject(username)
                .withIssuer(issuerUrl)
                .sign(algorithm)
                .strip();
    }

    public Authentication getAuthentication(String token) {
        DecodedJWT decodedJWT = decodedToken(token);
        UserDetails userDetails = userDetailsService.loadUserByUsername(decodedJWT.getSubject());

        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public DecodedJWT decodedToken(String token) {
        Algorithm alg = Algorithm.HMAC256(secretKey);
        JWTVerifier verifier = JWT.require(alg).build();
        DecodedJWT decodedJWT = verifier.verify(token);
        return decodedJWT;
    }

    public String resolveToken(HttpServletRequest req) {
        String bearerToken = req.getHeader("Authorization");

        if (Objects.nonNull(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring("Bearer ".length());
        }
        return null;
    }

    public boolean validateToken(String token) {
        DecodedJWT decodedJWT = decodedToken(token);

        try {
            if (decodedJWT.getExpiresAt().before(new Date())) return false;
            return true;
        } catch (Exception e) {
            throw new InvalidJwtAuthenticationException("Token expirado");
        }
    }
}

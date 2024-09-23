package br.com.divinecode.gameshopapplication.dto.security;

import lombok.Data;

import java.util.Date;

@Data
public class TokenDTO {

    private String username;

    private Boolean authenticated;

    private Date created;

    private Date expiration;

    private String accessToken;

    private String refreshToken;

    public TokenDTO() {
    }

    public TokenDTO(String username, Boolean authenticated, Date created, Date expiration, String accessToken, String refreshToken) {
        this.username = username;
        this.authenticated = authenticated;
        this.created = created;
        this.expiration = expiration;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}

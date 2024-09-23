package br.com.divinecode.gameshopapplication.dto.security;

import com.fasterxml.jackson.annotation.JsonInclude;


@JsonInclude(JsonInclude.Include.NON_NULL)
public record ResponseAuthDTO(String username, String token, String message, int status) {
}

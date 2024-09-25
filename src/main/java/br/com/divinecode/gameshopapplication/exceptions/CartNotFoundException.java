package br.com.divinecode.gameshopapplication.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.AuthenticationException;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CartNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public CartNotFoundException(String ex) {
        super(ex);
    }
}

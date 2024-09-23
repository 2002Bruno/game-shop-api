package br.com.divinecode.gameshopapplication.exceptions.handler;

import br.com.divinecode.gameshopapplication.exceptions.InvalidJwtAuthenticationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import br.com.divinecode.gameshopapplication.exceptions.ExeptionResponse;

import java.util.Date;

@ControllerAdvice
@RestController
public class CustomizeResponseExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(InvalidJwtAuthenticationException.class)
    public final ResponseEntity<ExeptionResponse> InvalidJwtAuthenticationException(Exception ex, WebRequest request) {
        ExeptionResponse exeptionResponse = new ExeptionResponse(
                new Date(),
                ex.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<>(exeptionResponse, HttpStatus.FORBIDDEN);
    }
}

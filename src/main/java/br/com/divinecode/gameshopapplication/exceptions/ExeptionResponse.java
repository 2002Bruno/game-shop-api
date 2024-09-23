package br.com.divinecode.gameshopapplication.exceptions;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class ExeptionResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    private Date timesTamp;

    private String message;

    private String details;

    public ExeptionResponse(Date timesTamp, String message, String details) {
        this.timesTamp = timesTamp;
        this.message = message;
        this.details = details;
    }
}

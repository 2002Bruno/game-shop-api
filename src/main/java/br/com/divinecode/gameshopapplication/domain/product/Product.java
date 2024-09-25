package br.com.divinecode.gameshopapplication.domain.product;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Data
@Table(name = "product")
@Entity
public class Product implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    private Long avaliation;
}

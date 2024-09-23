package br.com.divinecode.gameshopapplication.domain.permission;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;

@Data
@Entity
@Table(name = "permission")
public class Permission implements GrantedAuthority, Serializable {

    private static final long SerialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false, length = 100)
    private String description;

    public Permission() {}

    @Override
    public String getAuthority() {
        return this.description;
    }
}

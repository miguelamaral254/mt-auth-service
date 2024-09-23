package br.com.loginauth.domain.entities;

import br.com.loginauth.domain.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
@Entity
@Table(name = "users")
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @Column(nullable = false, unique = true)
    private String cpf;
// private String ?? photo; Task 0071
    private String name;
    private String email;
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    private boolean active;
    private LocalDateTime createDate;

    @PrePersist
    protected void onCreate() {
        createDate = LocalDateTime.now();
        active = true;
    }
}

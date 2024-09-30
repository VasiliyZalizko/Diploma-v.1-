package ru.netology.cloudstorage.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Embedded
    private UserData userData;

    @ManyToOne(optional = false)
    private Role role;

    @Override
    public String toString() {
        return "User {" + "id " + id + ", login = " + email + userData + "}\n";
    }
}
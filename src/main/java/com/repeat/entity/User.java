package com.repeat.entity;


import com.repeat.dto.AuthRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "users")
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    public User(AuthRequestDto authRequestDto) {
        this.username = authRequestDto.getUsername();
        this.password = authRequestDto.getPassword();
    }

}

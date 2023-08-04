package com.repeat.entity;


import com.repeat.dto.SingupRequestDto;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

// lombok
@Getter
@NoArgsConstructor
@EqualsAndHashCode

// jpa
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    public User(SingupRequestDto singupRequestDto) {
        this.username = singupRequestDto.getUsername();
        this.password = singupRequestDto.getPassword();
    }

}

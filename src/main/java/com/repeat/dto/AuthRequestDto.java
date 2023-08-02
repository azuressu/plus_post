package com.repeat.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthRequestDto {

    @Pattern(regexp = "^[a-z0-9]{3,}$")
    @NotBlank
    private String username;

    @Size(min = 4)
    @NotBlank
    private String password;

    @NotBlank
    private String confirmpassword;

}

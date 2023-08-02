package com.repeat.controller;

import com.repeat.dto.SingupRequestDto;
import com.repeat.service.UserService;
import com.sun.jdi.request.DuplicateRequestException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api")
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/user/signup")
    public ResponseEntity<String> signUp(@Valid @RequestBody SingupRequestDto singupRequestDto) {
        try {
            String result = userService.signUp(singupRequestDto);
            return ResponseEntity.ok().body(result);
        } catch (DuplicateRequestException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

}

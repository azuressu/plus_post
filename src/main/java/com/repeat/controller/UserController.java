package com.repeat.controller;

import com.repeat.dto.LoginRequestDto;
import com.repeat.dto.SingupRequestDto;
import com.repeat.jwt.JwtUtil;
import com.repeat.service.UserService;
import com.sun.jdi.request.DuplicateRequestException;
import jakarta.servlet.http.HttpServletResponse;
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
    private final JwtUtil jwtUtil;

    @PostMapping("/user/signup")
    public ResponseEntity<String> signUp(@Valid @RequestBody SingupRequestDto singupRequestDto) {
        try {
            String result = userService.signUp(singupRequestDto);
            return ResponseEntity.ok().body(result);
        } catch (DuplicateRequestException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/user/login")
    public ResponseEntity<String> logIn(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse response) {
        try {
            // 로그인 서비스에 가서 로그인 진행
            String result = userService.logIn(loginRequestDto);
            // 로그인한 username으로 JWT 생성
            String token = jwtUtil.createToken(loginRequestDto.getUsername());
            // JWT를 cookie에 추가
            jwtUtil.addJwtToCookie(token, response);
            // 결과와 함께 return
            return ResponseEntity.ok().body(result);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}

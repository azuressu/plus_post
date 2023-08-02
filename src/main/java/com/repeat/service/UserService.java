package com.repeat.service;

import com.repeat.dto.LoginRequestDto;
import com.repeat.dto.SingupRequestDto;
import com.repeat.entity.User;
import com.repeat.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public String signUp(SingupRequestDto singupRequestDto) {
        String username = singupRequestDto.getUsername();
        String password = singupRequestDto.getPassword();
        String confirmpassword = singupRequestDto.getConfirmpassword();

        // password에 닉네임과 같은 값이 포함된 경우
        if (password.contains(username)) {
            throw new IllegalArgumentException("비밀번호에 닉네임이 포함될 수 없습니다");
        }

        // password와 confirmpassword과 정확하게 일치함을 확인
        if (!(password.equals(confirmpassword))) {
            throw new IllegalArgumentException("비밀번호 확인이 일치하지 않습니다");
        }

        // DB에 존재하는 닉네임을 입력한 경우
        if (userRepository.findByUsername(username).isPresent()) {
            throw new IllegalArgumentException("중복된 닉네임입니다");
        }

        User user = new User(singupRequestDto);
        userRepository.save(user);
        return "회원가입 성공";
    }

    public String logIn(LoginRequestDto loginRequestDto) {
        Optional<User> confirmUser = userRepository.findByUsername(loginRequestDto.getUsername());

        if (confirmUser.isPresent()) {
            if ( !confirmUser.get().getPassword().equals(loginRequestDto.getPassword()) ) {
                throw new IllegalArgumentException("닉네임 또는 패스워드를 확인해주세요");
            }
        } else {
            throw new IllegalArgumentException("닉네임 또는 패스워드를 확인해주세요");
        }

        return "로그인 성공";
    }
}

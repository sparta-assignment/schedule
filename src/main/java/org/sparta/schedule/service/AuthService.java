package org.sparta.schedule.service;

import lombok.RequiredArgsConstructor;
import org.sparta.schedule.common.exception.DataNotFoundException;
import org.sparta.schedule.common.exception.DuplicateIdException;
import org.sparta.schedule.common.exception.InvalidCredentialsException;
import org.sparta.schedule.common.exception.UserNotFoundException;
import org.sparta.schedule.common.jwt.JwtTokenProvider;
import org.sparta.schedule.common.utils.mapper.MapperUtil;
import org.sparta.schedule.dto.LoginReqDto;
import org.sparta.schedule.dto.LoginResDto;
import org.sparta.schedule.dto.UserAddDto;
import org.sparta.schedule.dto.UserResDto;
import org.sparta.schedule.entity.User;
import org.sparta.schedule.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public UserResDto signUp(UserAddDto userAddDto) {
        // 중복 ID 확인
        existsByUsername(userAddDto.getUsername());
        User user = MapperUtil.toEntity(userAddDto, User.class);
        return new UserResDto(userRepository.save(user));
    }

    public LoginResDto signIn(LoginReqDto loginReqDto) {
        User user = findByUsername(loginReqDto.getUsername());
        checkPassword(loginReqDto.getPassword(), user.getPassword());

        String token = jwtTokenProvider.createToken(user.getUsername(), user.getRole());
        return new LoginResDto(new UserResDto(user), token);
    }

    private void existsByUsername(String username) {
        if (userRepository.existsByUsername(username)) {
            throw new DuplicateIdException("중복된 아이디가 존재합니다.");
        }
    }

    private User findByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(
                () -> new UserNotFoundException("회원을 찾을 수 없습니다.")
        );
    }

    private void checkPassword(String inputPassword, String password) {
        if (!Objects.equals(inputPassword, password)) {
            throw new InvalidCredentialsException("비밀번호가 맞지 않습니다.");
        }
    }
}

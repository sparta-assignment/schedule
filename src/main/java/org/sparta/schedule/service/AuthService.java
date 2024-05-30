package org.sparta.schedule.service;

import lombok.RequiredArgsConstructor;
import org.sparta.schedule.common.exception.DuplicateIdException;
import org.sparta.schedule.common.exception.InvalidCredentialsException;
import org.sparta.schedule.common.exception.UserNotFoundException;
import org.sparta.schedule.common.utils.mapper.MapperUtil;
import org.sparta.schedule.dto.*;
import org.sparta.schedule.dto.login.LoginDto;
import org.sparta.schedule.dto.login.LoginReqDto;
import org.sparta.schedule.dto.login.LoginResDto;
import org.sparta.schedule.entity.User;
import org.sparta.schedule.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final TokenService tokenService;

    public UserResDto signUp(UserAddDto userAddDto) {
        // 중복 ID 확인
        existsByUsername(userAddDto.getUsername());
        User user = MapperUtil.toEntity(userAddDto, User.class);
        return new UserResDto(userRepository.save(user));
    }

    public LoginDto signIn(LoginReqDto loginReqDto) {
        User user = findByUsername(loginReqDto.getUsername());
        checkPassword(loginReqDto.getPassword(), user.getPassword());

        String accessToken = tokenService.createAccessToken(user.getUsername(), user.getRole());
        String refreshToken = tokenService.createRefreshToken(user);
        return new LoginDto(new UserResDto(user), new TokenDto(accessToken, refreshToken));
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

    public TokenDto reissueToken(String accessToken, String refreshToken) {
        String username = tokenService.getTokenUsername(accessToken);
        User user = findByUsername(username);
        return tokenService.reissueToken(user, refreshToken);
    }
}

package org.sparta.schedule.service;

import lombok.RequiredArgsConstructor;
import org.sparta.schedule.common.exception.DuplicateIdException;
import org.sparta.schedule.common.utils.mapper.MapperUtil;
import org.sparta.schedule.dto.UserAddDto;
import org.sparta.schedule.dto.UserResDto;
import org.sparta.schedule.entity.User;
import org.sparta.schedule.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;

    public UserResDto signUp(UserAddDto userAddDto) {
        // 중복 ID 확인
        existsByUsername(userAddDto.getUsername());
        User user = MapperUtil.toEntity(userAddDto, User.class);
        return new UserResDto(userRepository.save(user));
    }

    public void existsByUsername(String username) {
        if (userRepository.existsByUsername(username)) {
            throw new DuplicateIdException("중복된 아이디가 존재합니다.");
        }
    }
}

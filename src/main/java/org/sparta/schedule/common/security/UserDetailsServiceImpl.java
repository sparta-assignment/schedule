package org.sparta.schedule.common.security;

import lombok.RequiredArgsConstructor;
import org.sparta.schedule.common.exception.InvalidCredentialsException;
import org.sparta.schedule.entity.User;
import org.sparta.schedule.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new InvalidCredentialsException("회원을 찾을 수 없습니다.")
        );
        return new UserDetailsImpl(user);
    }
}

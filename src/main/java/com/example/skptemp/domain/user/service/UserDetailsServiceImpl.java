package com.example.skptemp.domain.user.service;

import com.example.skptemp.domain.user.entity.User;
import com.example.skptemp.domain.user.entity.UserDetailsImpl;
import com.example.skptemp.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException { // 여기서 username (String) == user_id (Long)
        Long userId = Long.parseLong(username);
        Optional<User> findUserOpt = userRepository.findById(userId);
        if(findUserOpt.isEmpty())
            throw new UsernameNotFoundException("회원을 찾지 못했습니다.");

        return new UserDetailsImpl(findUserOpt.get());
    }
}

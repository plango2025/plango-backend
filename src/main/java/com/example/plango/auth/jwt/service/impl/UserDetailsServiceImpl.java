package com.example.plango.auth.jwt.service.impl;

import com.example.plango.auth.jwt.model.UserDetailsImpl;
import com.example.plango.user.model.UserInfo;
import com.example.plango.user.repository.UserInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserInfoRepository userInfoRepository;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        UserInfo userInfo =userInfoRepository.findById(userId)
                .orElseThrow(()->new UsernameNotFoundException("존재하지 않는 사용자입니다. (userId = "+userId+")"));
        return UserDetailsImpl.build(userInfo);
    }
}

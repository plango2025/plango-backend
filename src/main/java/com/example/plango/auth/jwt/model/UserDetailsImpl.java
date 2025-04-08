package com.example.plango.auth.jwt.model;

import com.example.plango.user.model.UserInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserDetailsImpl implements UserDetails {
    private UserInfo userInfo;

    public static UserDetailsImpl build(UserInfo userInfo){
        return new UserDetailsImpl(userInfo);
    }

    @Override
    public String getUsername(){
        return userInfo.getId();
    }

    @Override
    public String getPassword(){
        return null;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // 현재 프로젝트에서는 Authority를 설정할 일이 없어서 빈 Collection을 반환
        return Collections.emptyList();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

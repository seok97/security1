package com.cos.security1.config.auth;

// 시큐리티가 /login 주소요청이 오면 가로채어 로그인을 진행 시킨다.
// 로그인 성공시 session (Spring Security 만의 세션{Security ContextHolder}) 을 생성 한다.
// 이 세션에 저장될 수 있는 객체는 => Authentication객체 이다.
// Authentication 객체에는 User 정보가 있어야 한다.
// User정보 또한 객체 타입이 정해져 있으며, UserDetails 객체 이다.


import com.cos.security1.dto.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

public class PrincipalDetails implements UserDetails {

    private User user; // 컴포지션

    public PrincipalDetails(User user){
        this.user = user;
    }

    // 해당 User의 권한을 리턴해준다.
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collect = new ArrayList<>();

        // 반환 타입이 Collection<GrantedAuthority> 이므로 다음과 같이 리턴해준다.
        collect.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return user.getRole();
            }
        });
        return collect;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    // 이레 메소드들은 true 를 리턴하면 해당되지 않음을 의미한다. 따라서 일단 true 로 반환하도록 수정

    @Override
    public boolean isAccountNonExpired() { // 만료 여부
        return true;
    }

    @Override
    public boolean isAccountNonLocked() { // 잠김 여부
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() { // 패스워드 변경 기간 만료 여부
        return true;
    }

    @Override
    public boolean isEnabled() { // 계정 활성화 여부
        return true;
    }
}

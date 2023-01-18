package com.cos.security1.config.auth;

import com.cos.security1.dto.entity.User;
import com.cos.security1.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

// 시큐리티 설정에서 loginProcessingUrl("/login"); 을 했기때문에
// /login 요청이 오면 자동으로 UserDetailsService 타입으로 IoC되어 있는 loadUserByUsername 함수가 실행 된다.
@Slf4j
@Service
public class PrincipalDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;



    /**
     * form 로그인 형식 일때, 사용자 아이디 파라미터 명을 username 으로 맞춰 주거나
     * security config 에서 어떤 파라미터명으로 아이디를 받을 것인지 설정 해주어야 한다.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("username : {}", username);
        // 로그인하면 이 메소드가 실행되고 아래 로직이 수행 되면 사용자 아이디로 정보를 조회하고
        // 시큐리티 세션에 해당 정보를 담는다.
        User userEntity = userRepository.findByUsername(username);
        if(userEntity != null){
            return new PrincipalDetails(userEntity);
        }
        return null;
    }
}

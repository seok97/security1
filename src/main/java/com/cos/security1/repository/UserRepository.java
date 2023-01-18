package com.cos.security1.repository;

import com.cos.security1.dto.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

// CRUD 함수를 JPA가 들고 있다.
// @Respository 어노테이션 필요없음 -> JapRespository 가 이미 상속받고 있고 이를 상속받았기 때문.
public interface UserRepository extends JpaRepository<User, Integer> {

    /**
     * findBy 라는 이름은 규칙이다.
     * findBy 규칙 -> Username 문법
     * 함수명을 위처럼 사용하면 아래 쿼리를 실행한다.
     * select * from user where username = 1?
     */
    public User findByUsername(String username);

    /**
     * 이 메소드는 다음 쿼리가 호출 된다.
     * select * from user where email = ?
     */
//    public User findByEmail(); Jpa 쿼리 메소드
}

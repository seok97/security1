package com.cos.security1.repository;

import com.cos.security1.dto.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

// CRUD 함수를 JPA가 들고 있다.
// @Respository 어노테이션 필요없음 -> JapRespository 가 이미 상속받고 있고 이를 상속받았기 때문.
public interface UserRepository extends JpaRepository<User, Integer> {

}

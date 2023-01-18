package com.cos.security1.ctrl;

import com.cos.security1.dto.entity.User;
import com.cos.security1.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class IndexCtrl {

    @Autowired
    UserRepository userRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping({"", "/"})
    public String index(){
        // 해당 프로젝트에서는 머스테치를 사용 ( spring 에서 권장? )
        // 머스테치 기본 경로 /src/main /resources/
        // 뷰리졸버 설정 : /templates (prefix), .mustache ( suffix ) - 의존 주입 시 기본 경로로 설정됨.
        // index.mustache 파일로 하게되면 설정이 까다롭기 때문에 html 파일로 작업한다.
        return "index"; // 'index' 이렇게 반환 시 /src/main /resources/templates/index.mustache 파일을 찾게 되기 때문에 이 설정을 변경 해준다.
        /**
         * WebMvcConfigurer 를 구현하는 클래스를 생성
         * configureViewResolvers(ViewResolverRegistry) 메소드를 구현한다.
         * MustacheViewResolver 객체로 머스테치 재설정 가능
         */
    }

    @GetMapping("/user")
    public @ResponseBody String user(){
        return "user";
    }

    @GetMapping("/admin")
    public @ResponseBody String admin(){
        return "admin";
    }

    @GetMapping("/manager")
    public @ResponseBody String manager(){
        return "manager";
    }

    @GetMapping("/loginForm")
    public String loginForm(){
        return "loginForm";
    }

    @GetMapping("/joinForm")
    public String joinForm(){
        return "joinForm";
    }

    @PostMapping("/join")
    public String join(User user){
        user.setRole("ROLE_USER");
        String rawPass = user.getPassword();
        String encPass = bCryptPasswordEncoder.encode(rawPass);
        user.setPassword(encPass);
        userRepository.save(user);
        return "redirect:/loginForm";
    }

    @GetMapping("/joinProc")
    public @ResponseBody String joinProc(){
        return "회원가입완료";
    }

    @Secured("ROLE_ADMIN") // spring security 설정에서 사용을 true 로 설정해주면 사용 가능
    @GetMapping("/info")
    public @ResponseBody String info(){
        return "personalinfo";
    }

    /**
     * secured 는 하나의 권한만 설정가능 하지만
     * preauthorize를 사용하면 여러개의 권한을 설정할 수 있다.
     * 메소드가 실행되기 전에 권한을 검사한다.
     */
    @PreAuthorize("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
//    @PostAuthorize() 메소드가 실행된 후 검사한다.
    @GetMapping("/date")
    public @ResponseBody String data(){
        return "datainfo";
    }
}

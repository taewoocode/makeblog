package me.denden.springbootdeveloper.controller;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import me.denden.springbootdeveloper.dto.AddUserRequest;
import me.denden.springbootdeveloper.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@RequiredArgsConstructor
@Controller
public class UserApiController {

    private final UserService userService;

    @PostMapping("/user") //등록
    public String signup(AddUserRequest request) {
        userService.save( request ); //userService객체
        return "redirect:/login"; //회원 가입 완료이후 login page로 이동한다.

    }

    //로그아웃 기능 구현
    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        new SecurityContextLogoutHandler().logout( request,response,
                SecurityContextHolder.getContext().getAuthentication() );
        return "redirect/login";
    }
}

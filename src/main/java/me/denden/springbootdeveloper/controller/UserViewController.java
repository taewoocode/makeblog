package me.denden.springbootdeveloper.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserViewController {
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/signup")
    public String signup() {
        return "signup";
    }
}

/*
get 요청이 들어오면 이를 처리하는 메소드이다.
return login은 login이라는 view를 반환해준 다는 것을 의미한다.
클라이언트에게 보여질 html을 정의해야 한다.
 */
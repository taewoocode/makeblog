package me.denden.springbootdeveloper.controller;


import lombok.RequiredArgsConstructor;
import me.denden.springbootdeveloper.dto.AddUserRequest;
import me.denden.springbootdeveloper.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@RequiredArgsConstructor
@Controller
public class UserApiController {

    private final UserService userService;

    @PostMapping("/user") //등록
    public String signup(AddUserRequest request) {
        userService.save( request ); //userService객체
        return "redirect:/login";

    }
}

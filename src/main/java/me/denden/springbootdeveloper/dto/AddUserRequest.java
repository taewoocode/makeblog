package me.denden.springbootdeveloper.dto;


import lombok.Getter;
import lombok.Setter;

//객체를 먼저 정리하고
@Setter
@Getter
public class AddUserRequest {

    private String email;
    private String password;

}

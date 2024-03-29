package me.denden.springbootdeveloper.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.denden.springbootdeveloper.config.jwt.JwtFactory;
import me.denden.springbootdeveloper.config.jwt.JwtProperties;
import me.denden.springbootdeveloper.domain.ReFreshToken;
import me.denden.springbootdeveloper.domain.User;
import me.denden.springbootdeveloper.dto.CreateAccessTokenRequest;
import me.denden.springbootdeveloper.repository.RefreshTokenRepository;
import me.denden.springbootdeveloper.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class TokenApiControllerTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    JwtProperties jwtProperties;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RefreshTokenRepository refreshTokenRepository;


    @BeforeEach
    public void mockMvcSetUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup( context )
                        .build();
        userRepository.deleteAll();
    }

    @DisplayName("createNewAccessToken: 새로운 토큰 발급")
    @Test
    public void createNewAccessToken() throws  Exception {

        //given
        final String url = "/api/.token";

        User testUser = userRepository.save( User.builder()
                .email( "user@gmail.com" )
                .password( "test" )
                .build() );

        String refreshToken = JwtFactory.builder()
                .claims( Map.of( "Id", testUser.getId() ) )
                .build()
                .createToken( jwtProperties );

        refreshTokenRepository.save( new ReFreshToken( testUser.getId(),
                refreshToken ) );


        CreateAccessTokenRequest request = new CreateAccessTokenRequest();
        request.setRefreshToken( refreshToken );

        final String requestBody = objectMapper.writeValueAsString( request );

        //when
        ResultActions resultActions = mockMvc.perform( post( url )
                .contentType( MediaType.APPLICATION_JSON_VALUE )
                .contentType( requestBody ) );


        //then
        resultActions
                .andExpect( status().isCreated() )
                .andExpect( jsonPath( "$.accessToken" ).isNotEmpty() );
    }
}

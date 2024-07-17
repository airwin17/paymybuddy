package com.pmb.paymybuddy.controllerTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.pmb.paymybuddy.repositories.UserRepository;
import jakarta.servlet.http.Cookie;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
public class UserControllerTest {
    @Autowired
    private MockMvc mockmvc;
    @Autowired
    private UserRepository userRepository;

    public Cookie login() throws Exception {
        String requestBody = "email=testo&password=";
        ResultActions result = mockmvc.perform(MockMvcRequestBuilders.post("/login")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .content(requestBody));
                MockHttpServletResponse res=result.andReturn().getResponse();
                Cookie cookie=result.andReturn().getResponse().getCookie("JSESSIONID");
        return cookie;
    }

    @Test
    public void createUserTest() throws Exception {
        int before = userRepository.findAll().size();
        mockmvc.perform(MockMvcRequestBuilders.post("/api/user/saveUser")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        { "username": "testu",
                         "password": "testi",
                         "email": "testa"
                         }
                        """));
        int after = userRepository.findAll().size();
        assertEquals(before + 1, after);
    }

    @Test
    public void updateUserTest() throws Exception {
        Cookie cookie = login();
        String before = userRepository.findUserByEmail("testo").get().getUsername();
        mockmvc.perform(MockMvcRequestBuilders.put("/api/user/updateUser")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        { "username": "testuoooo",
                         "password": "testi",
                         "email": "testo"
                         }
                        """)
                .cookie(cookie));
        String after = userRepository.findUserByEmail("testo").get().getUsername();
        assertEquals("testuoooo", after);
    }
}

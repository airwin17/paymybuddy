package com.pmb.paymybuddy.controllerTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.pmb.paymybuddy.controllers.GlobalController;
import com.pmb.paymybuddy.controllers.UserController;
@SpringBootTest
public class EndpointTest {
    MockMvc mockmvc;
    @BeforeEach
    public void init(){
        mockmvc = MockMvcBuilders.standaloneSetup(new GlobalController()).build();
    }
    @Test
    public void saveUserTest() throws Exception{
        mockmvc.perform(MockMvcRequestBuilders.get("/signin")).andExpect(
            result -> assertEquals(403, result.getResponse().getStatus())
        );
    }
}

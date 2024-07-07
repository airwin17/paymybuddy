package com.pmb.paymybuddy.controllerTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.pmb.paymybuddy.controllers.UserController;
import com.pmb.paymybuddy.services.UserService;

@TestComponent
public class UserControllerTest {
    /*UserService userService = new UserService();
    private MockMvc mockMvc;
    @BeforeEach
    public void init(){
        mockMvc=MockMvcBuilders.standaloneSetup(new UserController()).build();
        userService=new UserService();
        //reset user table
        userService.deleteAll();
    }

    @Test
    public void createUser() throws Exception {
        int before=userService.findAll().size();
        ResultActions ra=mockMvc.perform(MockMvcRequestBuilders
            .post("/saveUser")
            .contentType("application/json")
            .content("{\n" +
                "    \"username\": \"test\",\n" +
                "    \"password\": \"test\",\n" +
                "    \"email\": \"test\"\n" +
                "}"));
                String res=ra.andReturn().getResponse().getContentAsString();
            int after=userService.findAll().size();
            assertEquals("User created", res);
            assertEquals(before+1,after);
    }

    @Test
    public void createUserWhereEmailAlreadyExists() throws Exception {
        createUser();
        mockMvc.perform(MockMvcRequestBuilders
            .post("/saveUser")
            .contentType("application/json")
            .content("""
                {
                    "username": "test",
                    "password": "test",
                    "email": "test"
                }""")).andExpect(mvcResult -> assertEquals(400, mvcResult.getResponse().getStatus()));
    }
    @Test
    public void getUserByIdTest() throws Exception {
        createUser();
        mockMvc.perform(MockMvcRequestBuilders
            .get("/getUserbyId/1"))
            .andExpect(mvcResult -> assertEquals(200, mvcResult.getResponse().getStatus()));
    }
    @Test
    public void getUserByIdWhenUserDoesNotExist() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
            .get("/getUserbyId/1"))
            .andExpect(mvcResult -> assertEquals(404, mvcResult.getResponse().getStatus()));
    }
    @Test
    public void getUserByUsernameTest() throws Exception {
        createUser();
        mockMvc.perform(MockMvcRequestBuilders
            .get("/getUserByUsername/test"))
            .andExpect(mvcResult -> assertEquals(200, mvcResult.getResponse().getStatus()));
    }
    @Test
    public void getUserByUsernameWhenUserDoesNotExist() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
            .get("/getUserByUsername/test"))
            .andExpect(mvcResult -> assertEquals(404, mvcResult.getResponse().getStatus()));
    }
    @Test 
    public void updateUserTest() throws Exception {
        createUser();
        mockMvc.perform(MockMvcRequestBuilders
            .put("/updateUser")
            .contentType("application/json")
            .content(String.format("""
                {
                    "id": 1,
                    "username": "test",
                    "password": "test",
                    "email": "test"
                }"""))).andExpect(mvcResult -> assertEquals(200, mvcResult.getResponse().getStatus()));
    }
    @Test
    public void updateUserwhenUserDoesNotExistTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
            .put("/updateUser")
            .contentType("application/json")
            .content(String.format("""
                {
                    "id": 1,
                    "username": "test",
                    "password": "test",
                    "email": "test"
                }"""))).andExpect(mvcResult -> assertEquals(404, mvcResult.getResponse().getStatus()));
    }

    public int getUsercount(){
        UserService userService = new UserService();
        return userService.findAll().size();
    }*/
}

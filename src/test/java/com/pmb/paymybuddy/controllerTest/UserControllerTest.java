package com.pmb.paymybuddy.controllerTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import com.pmb.paymybuddy.model.User;
import com.pmb.paymybuddy.services.UserService;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserControllerTest {
    @Autowired
    private MockMvc mockmvc;
    @Autowired
    private UserService userService;
    private static User user;
    
    @BeforeAll
    public void beforeAll() throws Exception {
        user=userService.findUserByEmail("newUser");
        if (user==null) {
            user=new User();
            user.setUsername("usernameTest");
            user.setEmail("newUser");
            user.setPassword("lool");
            userService.createUser(user);
        }
    }
    
    public void createUser(User user) throws Exception {
        String str="{\"username\": \""+user.getUsername()+"\", \"password\": \""+user.getPassword()+"\", \"email\": \""+user.getEmail()+"\"}";
        mockmvc.perform(MockMvcRequestBuilders.post("/api/user/saveUser")
                .contentType(MediaType.APPLICATION_JSON)
                .content(str));
    }
    @Test
    public void createUserTest() throws Exception {
        int before = userService.findAll().size();
        user=new User();
        user.setUsername("usernameTest");
        user.setEmail("newUserTest"+String.valueOf(System.currentTimeMillis()));
        user.setPassword("lool");
        createUser(user);
        int after = userService.findAll().size();
        assertEquals(before + 1, after);
    }
    @Test
    public void createUserTestWhereEmailAlreadyExist() throws Exception {
        int before = userService.findAll().size();
        user=new User();
        user.setUsername("usernameTest");
        user.setEmail("newUser");
        user.setPassword("lool");
        createUser(user);
        int after = userService.findAll().size();
        assertEquals(before, after);
    }
    @Test
    @WithUserDetails(value = "newUser")
    public void updateUserTest() throws Exception {
        mockmvc.perform(MockMvcRequestBuilders.put("/api/user/updateUser")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                        "{\"username\": \"testuoooo\", \"email\": \"newUser\", \"password\": \"lool\"}"
                )
                );
        String after = userService.findUserByEmail("newUser").getUsername();
        assertEquals("testuoooo", after);
    }
    @Test
    @WithUserDetails(value = "newUser")
    public void addConnectionTest() throws Exception {
        int before = userService.findUserByEmail("newUser").getConnectedUser().size();
        String email="newUserTest"+String.valueOf(System.currentTimeMillis());
        User user1=new User();
        user1.setUsername("usernameTest");
        user1.setEmail(email);
        user1.setPassword("lool");
        createUser(user1);
        mockmvc.perform(MockMvcRequestBuilders.post("/api/user/addConnection")
                .param("email",email));
        User logUser=userService.findUserByEmail("newUser");
        assertEquals(before+1, logUser.getConnectedUser().size());
    }
    @Test
    @WithUserDetails(value = "newUser")
    public void addConnectionWhereUserNotFoundTest() throws Exception {
        int before = userService.findUserByEmail("newUser").getConnectedUser().size();
        String email="newUserTest"+String.valueOf(System.currentTimeMillis());
        User user1=new User();
        user1.setUsername("usernameTest");
        user1.setEmail(email);
        user1.setPassword("lool");
        createUser(user1);
        mockmvc.perform(MockMvcRequestBuilders.post("/api/user/addConnection")
                .param("email",email+"r"));
        User logUser=userService.findUserByEmail("newUser");
        assertEquals(before, logUser.getConnectedUser().size());
    }
    @Test
    @WithUserDetails(value = "newUser")
    public void addConnectionWhereConnectionAlreadyExistTest() throws Exception {
        String email = "newUserTest" + String.valueOf(System.currentTimeMillis());
        User user1 = new User();
        user1.setUsername("usernameTest");
        user1.setEmail(email);
        user1.setPassword("lool");
        createUser(user1);
        mockmvc.perform(MockMvcRequestBuilders.post("/api/user/addConnection")
                .param("email", email));
        int before = userService.findUserByEmail("newUser").getConnectedUser().size();
        mockmvc.perform(MockMvcRequestBuilders.post("/api/user/addConnection")
                .param("email", email));
        User logUser = userService.findUserByEmail("newUser");
        assertEquals(before, logUser.getConnectedUser().size());
    }
}

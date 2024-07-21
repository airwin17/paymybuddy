package com.pmb.paymybuddy.controllerTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.pmb.paymybuddy.model.Transaction;
import com.pmb.paymybuddy.model.User;
import com.pmb.paymybuddy.services.TransactionService;
import com.pmb.paymybuddy.services.UserService;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TransactionControllerTest {
    @Autowired
    private MockMvc mockmvc;
    @Autowired
    private UserService userService;
    @Autowired
    public TransactionService transactionService;
    private User user;
    private User receiver;

    @BeforeAll
    public void beforeAll() throws Exception {
        user = userService.findUserByEmail("newUser");
        if ( user == null) {
            user = new User();
            user.setUsername("usernameTest");
            user.setEmail("newUser");
            user.setPassword("lool");
            userService.createUser(user);
        }
        userService.setcash(userService.findUserByEmail("newUser"), 0);
        userService.addCash(userService.findUserByEmail("newUser"), 100);
    }

    @BeforeEach
    public void beforeTestMethod() throws Exception {
        receiver=userService.findUserByEmail("newUser12");
        if (receiver == null) {
            receiver = new User();
            receiver.setUsername("usernameTest1");
            receiver.setEmail("newUser12");
            receiver.setPassword("receiver");
            userService.createUser(receiver);
            userService.addConnection(receiver.getEmail(), userService.findUserByEmail(user.getEmail()));
        }
    }

    @Test
    @WithUserDetails(value = "newUser", userDetailsServiceBeanName = "userDetailsService")
    public void saveTransactionTest() throws Exception {
        int u=transactionService.getTransactions(receiver).size();
        mockmvc.perform(MockMvcRequestBuilders.post("/saveTransaction")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"relationshipEmail\": \"" + receiver.getEmail()
                        + "\", \"amount\": 100, \"description\": \"test\"}"));
        List<Transaction> transactions = transactionService.getTransactions(receiver);
        assertEquals(u+1, transactions.size());
    }
    @Test
    @WithUserDetails(value = "newUser", userDetailsServiceBeanName = "userDetailsService")
    public void saveTransactionWhereNoCashTest() throws Exception {
        int u=transactionService.getTransactions(receiver).size();
        mockmvc.perform(MockMvcRequestBuilders.post("/saveTransaction")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"relationshipEmail\": \"" + receiver.getEmail()
                        + "\", \"amount\": 1000, \"description\": \"test\"}"));
        List<Transaction> transactions = transactionService.getTransactions(receiver);
        assertEquals(u, transactions.size());
    }
    @Test
    @WithUserDetails(value = "newUser", userDetailsServiceBeanName = "userDetailsService")
    public void saveTransactionWhereNoConnectionTest() throws Exception {
        String email="newUserTest"+String.valueOf(System.currentTimeMillis());
        User user1=new User();
        user1.setUsername("usernameTest");
        user1.setEmail(email);
        user1.setPassword("lool");
        userService.createUser(user1);
        int u=transactionService.getTransactions(receiver).size();
        mockmvc.perform(MockMvcRequestBuilders.post("/saveTransaction")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"relationshipEmail\": \"" + email
                        + "\", \"amount\": 100, \"description\": \"test\"}"));
        List<Transaction> transactions = transactionService.getTransactions(receiver);
        assertEquals(u, transactions.size());
    }
}

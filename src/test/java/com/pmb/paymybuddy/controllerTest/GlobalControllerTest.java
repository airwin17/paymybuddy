package com.pmb.paymybuddy.controllerTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.UnsupportedEncodingException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.pmb.paymybuddy.exceptions.EmailAlreadyExistsException;
import com.pmb.paymybuddy.model.User;
import com.pmb.paymybuddy.services.TransactionService;
import com.pmb.paymybuddy.services.UserService;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class GlobalControllerTest {
    @Autowired
    private MockMvc mockmvc;
    @Autowired
    private UserService userService;
    @Autowired
    public TransactionService transactionService;
    private User user;
    @BeforeAll
    public void beforeAll() throws EmailAlreadyExistsException {
        user = userService.findUserByEmail("newUser");
        if ( user == null) {
            user = new User();
            user.setUsername("usernameTest");
            user.setEmail("newUser");
            user.setPassword("lool");
            userService.createUser(user);
        }
    }
    @Test
    @WithUserDetails(value = "newUser")
    public void addTransactionTest() throws Exception {
        String str=mockmvc.perform(MockMvcRequestBuilders
        .get("/addTransaction")).andReturn().getResponse().getContentAsString();
        Document doc=Jsoup.parse(str);
        Element rel=doc.body().getElementById("relationship");
        int size=rel.childrenSize()-2;
        assertEquals(user.getConnectedUser().size(), size);
        Element table=doc.body().getElementById("table").children().get(0);
        int sizeTable=table.childrenSize()-1;
        assertEquals(transactionService.getTransactions(user).size(), sizeTable);
    }
    @Test
    public void addConnectionTest() throws Exception {
        String str=mockmvc.perform(MockMvcRequestBuilders
        .get("/signin")).andReturn().getResponse().getContentAsString();
        assertEquals(true, str.length()>0);
    }
    @Test
    public void login() throws Exception {
        String str=mockmvc.perform(MockMvcRequestBuilders
        .get("/loginPage")).andReturn().getResponse().getContentAsString();
        assertEquals(true, str.length()>0);
    }
    @Test
    @WithUserDetails(value = "newUser")
    public void ajouterUneRelation() throws UnsupportedEncodingException, Exception{
        String str=mockmvc.perform(MockMvcRequestBuilders.get("/addRelationship")).andReturn().getResponse().getContentAsString();
        assertEquals(true, str.length()>0);
    }
    @Test
    @WithUserDetails(value = "newUser")
    public void profilTest() throws Exception {
        String str=mockmvc.perform(MockMvcRequestBuilders
        .get("/profil")).andReturn().getResponse().getContentAsString();
        assertEquals(true, str.length()>0);
    }
}

package com.example.bcs.server.demo.controller;

import com.example.bcs.server.demo.AppConfig;
import com.example.bcs.server.demo.entity.Account;
import com.example.bcs.server.demo.repository.AccountsRepo;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(AccountController.class)
@ContextConfiguration(classes = {AppConfig.class})
public class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountsRepo accountsRepo;

//    @MockBean
//    private AccountConverter accountConverter;

    private LocalDateTime dateTime = LocalDateTime.now();


    @WithMockUser(username = "admin", password = "password")
    @Test
    @Ignore
    public void getAllAccounts() throws Exception {
        JSONArray expectedJson = new JSONArray();
        expectedJson.put(new JSONObject()
                .put("id", 1)
                .put("name", "Ben")
                .put("email", "ben@mail.com")
                .put("address", "NSK")
                .put("created_at", dateTime)
                .put("phone_number", "79101002030")
        ).put(new JSONObject()
                .put("id", 2)
                .put("name", "Melony")
                .put("email", "melony@mail.com")
                .put("address", "MSK")
                .put("created_at", dateTime)
                .put("phone_number", "79201002030")
        );

        given(accountsRepo.findAll()).willReturn(Arrays.asList(
                new Account(1L, "Ben", dateTime, "ben@mail.com", "79101002030", "NSK"),
                new Account(2L, "Melony", dateTime, "melony@mail.com", "79201002030", "MSK")
        ));

        mockMvc.perform(get("/accounts").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson.toString()));
    }

    @WithMockUser(username = "admin", password = "password")
    @Test
    @Ignore
    public void getAccountById() throws Exception {
        long requestId = 1;

        JSONObject expectedJson = new JSONObject()
                .put("id", 1)
                .put("name", "Ben")
                .put("email", "ben@mail.com")
                .put("address", "NSK")
                .put("created_at", dateTime)
                .put("phone_number", "79101002030");

        given(accountsRepo.findById(requestId))
                .willReturn(Optional.of(new Account(1L, "Ben", dateTime, "ben@mail.com", "79101002030", "NSK")));

        mockMvc.perform(get("/accounts/" + requestId).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson.toString()));
    }

    @WithMockUser(username = "admin", password = "password")
    @Test
    @Ignore
    public void postNewAccount() throws Exception {
        JSONObject accountJson = new JSONObject()
                .put("id", 1)
                .put("name", "Ben")
                .put("email", "ben@mail.com")
                .put("address", "NSK")
                .put("created_at", dateTime)
                .put("phone_number", "79101002030");

        JSONObject postJson = new JSONObject()
                .put("name", "Ben")
                .put("email", "ben@mail.com")
                .put("address", "NSK")
                .put("phone_number", "79101002030");


        Account saveAccount = new Account(1L, "Ben", dateTime, "ben@mail.com", "79101002030", "NSK");

        given(accountsRepo.save(saveAccount))
                .willReturn(saveAccount);

        mockMvc.perform(post("/accounts").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(postJson.toString()))
                .andExpect(status().isCreated())
                .andExpect(content().json(accountJson.toString()));
    }


}
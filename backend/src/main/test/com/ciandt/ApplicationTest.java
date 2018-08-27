package com.ciandt;

import com.ciandt.model.User;
import com.google.gson.Gson;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.ArrayList;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ApplicationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldReturnDefaultMessage() throws Exception {
        this.mockMvc.perform(get("/"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Hello world!!")));
    }

    @Test
    public void test01_ShouldPostUser() throws Exception {
        Gson gson = new Gson();
        User user = new User(1, new BigDecimal(8));
        String jsonString = gson.toJson(user);

        this.mockMvc.perform(post("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonString))
                .andExpect(content().string(containsString(jsonString)))
                .andExpect(status().isOk());
    }

    @Test
    public void test02_ShouldPostUser() throws Exception {
        Gson gson = new Gson();
        User user = new User(2, new BigDecimal(80));
        String jsonString = gson.toJson(user);

        this.mockMvc.perform(post("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonString))
                .andExpect(content().json(jsonString))
                .andExpect(status().isOk());
    }

    @Test
    public void test03_ShouldPostUser() throws Exception {
        Gson gson = new Gson();
        User user = new User(3, new BigDecimal(8000000));
        String jsonString = gson.toJson(user);

        this.mockMvc.perform(post("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonString))
                .andExpect(content().json(jsonString))
                .andExpect(status().isOk());
    }

    @Test
    public void test04_ShouldGetUser() throws Exception {
        Gson gson = new Gson();
        User user = new User(3, new BigDecimal(8000000));
        String jsonString = gson.toJson(user);

        this.mockMvc.perform(get("/user/3")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(jsonString))
                .andExpect(status().isOk());
    }

    @Test
    public void test05_ShouldDeleteUser() throws Exception {

        this.mockMvc.perform(delete("/user/3")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(containsString("OK")))
                .andExpect(status().isOk());
    }


    @Test
    public void test06_ShouldGetEmptyList() throws Exception {
        this.mockMvc.perform(get("/user/3")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(containsString("User not found")))
                .andExpect(status().isNotFound());
    }
}

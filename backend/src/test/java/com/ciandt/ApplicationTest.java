package com.ciandt;

import com.ciandt.model.Quote;
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

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
    public void test01_ShouldPostSiriusQuote() throws Exception {
        Gson gson = new Gson();
        Quote quote = new Quote("Sirius", "A mussum iria ficar pequena");
        String jsonString = gson.toJson(quote);

        this.mockMvc.perform(post("/quote")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonString))
                .andExpect(content().json(jsonString))
                .andExpect(status().isOk());
    }

    @Test
    public void test02_ShouldPostVictorQuote() throws Exception {
        Gson gson = new Gson();
        Quote quote = new Quote("Victor", "Só vi a cabecinha!");
        String jsonString = gson.toJson(quote);

        this.mockMvc.perform(post("/quote")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonString))
                .andExpect(content().json(jsonString))
                .andExpect(status().isOk());
    }

    @Test
    public void test03_ShouldPostPicanhaQuote() throws Exception {
        Gson gson = new Gson();
        Quote quote = new Quote("Picanha", "O sistema funciona por sorte.");
        String jsonString = gson.toJson(quote);

        this.mockMvc.perform(post("/quote")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonString))
                .andExpect(content().json(jsonString))
                .andExpect(status().isOk());
    }

    @Test
    public void test04_ShouldGetPicanhaQuote() throws Exception {
        Gson gson = new Gson();
        Quote quote = new Quote("Picanha", "O sistema funciona por sorte.");
        quote.setCreatedAt(null);
        String jsonString = gson.toJson(quote);

        this.mockMvc.perform(get("/quote/3")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(jsonString, false))
                .andExpect(status().isOk());
    }

    @Test
    public void test05_ShouldDeletePicanhaQuote() throws Exception {

        this.mockMvc.perform(delete("/quote/3")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(containsString("OK")))
                .andExpect(status().isOk());
    }


    @Test
    public void test06_ShouldNotFindQuote() throws Exception {
        this.mockMvc.perform(get("/quote/3")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(containsString("Quote not found")))
                .andExpect(status().isNotFound());
    }

    @Test
    public void test07_ShouldLikeSiriusQuote() throws Exception {
        this.mockMvc.perform(post("/quote/1/like")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(containsString("OK")))
                .andExpect(status().isOk());
    }
}

package com.microservice.auth.service;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.json.JSONObject;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.microservice.auth.service.entities.AppUser;
import com.microservice.auth.service.repositories.UserRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Sql("tests.sql")
public class AuthServiceApplicationTests {
  
  @Autowired
  protected MockMvc mockMvc;
  @Autowired UserRepository userRepository;
    
  @Test
  protected void testAuthenticate() throws Exception {
    this.mockMvc
        .perform( MockMvcRequestBuilders.post("/register")
                  .content("{\"username\":\"userTest\",\"password\": \"test\",\"role\": \"admin\"}")
                  .contentType(MediaType.APPLICATION_JSON)
                  .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isCreated());
    
    this.mockMvc
    .perform( MockMvcRequestBuilders.post("/register")
              .content("{\"username\":\"userTest\",\"password\": \"test\",\"role\": \"admin\"}")
              .contentType(MediaType.APPLICATION_JSON)
              .accept(MediaType.APPLICATION_JSON))
    .andExpect(status().isConflict());
    
    ResultActions result = this.mockMvc.perform( MockMvcRequestBuilders.post("/authenticate")
              .content("{\"username\":\"user\",\"password\": \"test\",\"role\": \"admin\"}")
              .contentType(MediaType.APPLICATION_JSON)
              .accept(MediaType.APPLICATION_JSON))
    .andExpect(status().isOk())
    .andExpect(MockMvcResultMatchers.jsonPath("$.token").exists());
    
    String resultString = result.andReturn().getResponse().getContentAsString();
    
    JSONObject tokenJson = new JSONObject(resultString);
    String token = tokenJson.getString("token");
    
    this.mockMvc
    .perform( MockMvcRequestBuilders.get("/me")
              .header("Authorization", "Bearer " + token))
    .andExpect(status().isOk())
    .andExpect(MockMvcResultMatchers.jsonPath("$.username").value("user"))
    .andExpect(MockMvcResultMatchers.jsonPath("$.password").exists()); 
    AppUser userTest = userRepository.findByUsername("userTest");
    userRepository.deleteById(userTest.getId());
    AppUser admin = userRepository.findByUsername("user");
    userRepository.deleteById(admin.getId());
  }
  
  
  
  

}


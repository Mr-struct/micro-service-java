package com.microservice.crud.service;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Sql("tests.sql")
class CrudEntitiesServiceApplicationTests {
	
	@Autowired
	protected MockMvc mockMvc;
	
	protected String authenticate(String username) throws Exception{
		
	    String url = "http://localhost:9100/authenticate";
	    HttpClient httpClient = HttpClientBuilder.create().build();
	    HttpPost post = new HttpPost(url);
	    JSONObject body = new JSONObject();
	    body.put("username", username);
	    body.put("password", "test");
	    StringEntity entity = new StringEntity(body.toString());
	    post.setEntity(entity);
	    post.setHeader("Content-type", "application/json");
	    
	    HttpResponse response = httpClient.execute(post);	    
	    
	    JSONObject tokenJson = new JSONObject(EntityUtils.toString(response.getEntity()));
	    
	    return tokenJson.getString("token");
	  }
	
	@Test
	protected void testHostel() throws Exception {
		String userToken = authenticate("user");
		
		this.mockMvc
	    .perform( MockMvcRequestBuilders.post("/hostel")
	    		.header("Authorization", "Bearer " + userToken))
	    .andExpect(status().isBadRequest());
		
		this.mockMvc
	    .perform( MockMvcRequestBuilders.get("/hostel/1")
	    		.header("Authorization", "Bearer " + userToken))
	    .andExpect(status().isNotFound());
		
		this.mockMvc
	    .perform( MockMvcRequestBuilders.delete("/hostel/1")
	    		.header("Authorization", "Bearer " + userToken))
	    .andExpect(status().isNotFound());
		
		this.mockMvc
	    .perform( MockMvcRequestBuilders.post("/hostel")
	    		  .header("Authorization", "Bearer " + userToken)
	              .content("{" + 
	              		"  \"name\": \"Hotel1\"," + 
	              		"  \"phoneNumber\": \"0611111111\"," + 
	              		"  \"address\": \"Rue de la mairie\"," + 
	              		"  \"rooms\": [" + 
	              		"    {" + 
	              		"      \"name\": \"Room1\"," + 
	              		"      \"roomCategory\": \"SR\"," + 
	              		"      \"price\": 50.0," + 
	              		"      \"capacity\": 2" + 
	              		"    }" + 
	              		"  ]" + 
	              		"}")
	              .contentType(MediaType.APPLICATION_JSON)
	              .accept(MediaType.APPLICATION_JSON))
	    .andExpect(status().isCreated());
	    
	    this.mockMvc
	    .perform( MockMvcRequestBuilders.put("/hostel/22")
	    		  .header("Authorization", "Bearer " + userToken)
	              .content("{" + 
	              		"  \"name\": \"TestHotel\"," + 
	              		"  \"phoneNumber\": \"0622222222\"," + 
	              		"  \"address\": \"Rue de la mairie\"" +  
	              		"}")
	              .contentType(MediaType.APPLICATION_JSON)
	              .accept(MediaType.APPLICATION_JSON))
	    .andExpect(status().isOk());
	    
	    this.mockMvc
	    .perform( MockMvcRequestBuilders.get("/hostel/22")
	    		  .header("Authorization", "Bearer " + userToken))
	    .andExpect(status().isOk())
	    .andExpect(MockMvcResultMatchers.jsonPath("$.phoneNumber").value("0622222222"));
	    
	    this.mockMvc
	    .perform( MockMvcRequestBuilders.get("/hostel/getAll")
	    		.header("Authorization", "Bearer " + userToken))
	    .andExpect(status().isOk());
	    
	    this.mockMvc
	    .perform( MockMvcRequestBuilders.delete("/hostel/22")
	    		.header("Authorization", "Bearer " + userToken))
	    .andExpect(status().isOk());
	    
	    this.mockMvc
	    .perform( MockMvcRequestBuilders.get("/hostel/22")
	    		.header("Authorization", "Bearer " + userToken))
	    .andExpect(status().isNotFound());
	}

	@Test
	protected void testReservation() throws Exception {
		String userToken = authenticate("user");
		
		this.mockMvc
	    .perform( MockMvcRequestBuilders.post("/reservation")
	    		.header("Authorization", "Bearer " + userToken))
	    .andExpect(status().isBadRequest());
		
		this.mockMvc
	    .perform( MockMvcRequestBuilders.get("/reservation/1")
	    		.header("Authorization", "Bearer " + userToken))
	    .andExpect(status().isNotFound());
		
		this.mockMvc
	    .perform( MockMvcRequestBuilders.delete("/reservation/1")
	    		.header("Authorization", "Bearer " + userToken))
	    .andExpect(status().isNotFound());
		
		this.mockMvc
	    .perform( MockMvcRequestBuilders.post("/reservation")
	    		  .header("Authorization", "Bearer " + userToken)
	              .content("{\"hostel\": { " +
	              		"    \"id\" : 24," +
	              		"    \"name\": \"Hotel45\",\"phoneNumber\": \"0611111111\"," + 
	              		"    \"address\": \"Rue du test\"" +  
	              		"  }," + 
	              		"  \"room\": {" +
	              		"    \"id\" : 24, " + 
	              		"    \"name\": \"Room41\"," + 
	              		"    \"roomCategory\": \"SR\"," + 
	              		"    \"price\": 50.0," + 
	              		"    \"capacity\": 2" + 
	              		"  }" + 
	              		"}")
	              .contentType(MediaType.APPLICATION_JSON)
	              .accept(MediaType.APPLICATION_JSON))
	    .andExpect(status().isCreated());
	    
	    this.mockMvc
	    .perform( MockMvcRequestBuilders.get("/reservation/1")
	    		.header("Authorization", "Bearer " + userToken))
	    .andExpect(status().isOk());

	    
	    this.mockMvc
	    .perform( MockMvcRequestBuilders.get("/reservation/getAll")
	    		.header("Authorization", "Bearer " + userToken))
	    .andExpect(status().isOk());
	    
	    this.mockMvc
	    .perform( MockMvcRequestBuilders.delete("/reservation/1")
	    		.header("Authorization", "Bearer " + userToken))
	    .andExpect(status().isOk());
	    
	    this.mockMvc
	    .perform( MockMvcRequestBuilders.get("/reservation/1")
	    		.header("Authorization", "Bearer " + userToken))
	    .andExpect(status().isNotFound());
	}
	
	@Test
	protected void testRoom() throws Exception {
		String userToken = authenticate("user");
		
		this.mockMvc
	    .perform( MockMvcRequestBuilders.post("/room")
	    		  .header("Authorization", "Bearer " + userToken))
	    .andExpect(status().isBadRequest());
		
		this.mockMvc
	    .perform( MockMvcRequestBuilders.get("/room/1")
	    		  .header("Authorization", "Bearer " + userToken))
	    .andExpect(status().isNotFound());
		
		this.mockMvc
	    .perform( MockMvcRequestBuilders.delete("/room/1")
	    		  .header("Authorization", "Bearer " + userToken))
	    .andExpect(status().isNotFound());
		
	    this.mockMvc
	    .perform( MockMvcRequestBuilders.post("/room")
	    		  .header("Authorization", "Bearer " + userToken)
	              .content("{\"name\":\"Room1\",\"roomCategory\":\"SR\",\"price\": 50.0,\"capacity\": 2}")
	              .contentType(MediaType.APPLICATION_JSON)
	              .accept(MediaType.APPLICATION_JSON))
	    .andExpect(status().isCreated());
	    
	    this.mockMvc
	    .perform( MockMvcRequestBuilders.get("/room/22")
	    		  .header("Authorization", "Bearer " + userToken))
	    .andExpect(status().isOk());
	    
	    this.mockMvc
	    .perform( MockMvcRequestBuilders.put("/room/22")
	    		  .header("Authorization", "Bearer " + userToken)
	              .content("{\"name\":\"TestRoom\",\"roomCategory\":\"SR\",\"price\": 75.0,\"capacity\": 2}")
	              .contentType(MediaType.APPLICATION_JSON)
	              .accept(MediaType.APPLICATION_JSON))
	    .andExpect(status().isOk());
	    
	    this.mockMvc
	    .perform( MockMvcRequestBuilders.get("/room/22")
	    		  .header("Authorization", "Bearer " + userToken))
	    .andExpect(status().isOk())
	    .andExpect(MockMvcResultMatchers.jsonPath("$.price").value(75.0));
	    
	    this.mockMvc
	    .perform( MockMvcRequestBuilders.get("/room/getAll")
	    		  .header("Authorization", "Bearer " + userToken))
	    .andExpect(status().isOk());
	    
	    this.mockMvc
	    .perform( MockMvcRequestBuilders.delete("/room/22")
	    		  .header("Authorization", "Bearer " + userToken))
	    .andExpect(status().isOk());
	    
	    this.mockMvc
	    .perform( MockMvcRequestBuilders.get("/room/22")
	    		  .header("Authorization", "Bearer " + userToken))
	    .andExpect(status().isNotFound());
	}
}

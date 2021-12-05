package com.fifi.java.practise.springhibernate.testSuite;



import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import java.io.UnsupportedEncodingException;




import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fifi.java.practise.springhibernate.api.UserReadingApplicationAPI;

import com.fifi.java.practise.springhibernate.requestInterface.UserHttpRequest;
import com.fifi.java.practise.springhibernate.response.obj.ResultNotFoundResponse;
import com.fifi.java.practise.springhibernate.response.obj.UserContact;
import com.google.gson.Gson;


//Testing our API by with dummy data (UserHttpRequestDummy) instead of 3rd party API

@EnableAutoConfiguration
@RunWith(SpringRunner.class)
@SpringBootTest
class UserAPITests {
	
	
	@Value("${NO_USERID_AND_USERNAME_ERROR}")
	private String NO_USERID_AND_USERNAME_ERROR;
	
	@Value("${USERID_INVALID_ERROR}")
	private String USERID_INVALID_ERROR;
	
	private MockMvc mockMvc;
    
	@Autowired
	private UserReadingApplicationAPI userReadingApplicationAPI;
	
	@Autowired
	private WebApplicationContext webApplicationContext;	
	
	@BeforeEach
	public void setUp() {
		 mockMvc = webAppContextSetup(webApplicationContext).build();		 
	}	
    
	@Autowired
	@Qualifier("dummyHttpRequest")
	private UserHttpRequest dummy;

	
	//Testing the getusercontacts api without provide any parameter
	
    @Test    
    public void userAPINoParamTest() throws UnsupportedEncodingException, Exception {
 
    	mockMvc = MockMvcBuilders
    	        .standaloneSetup(userReadingApplicationAPI).build();
    	
    	userReadingApplicationAPI.setUserHttpRequest(dummy);    	
    	
		String result = mockMvc.perform(get("http://test/api/getusercontacts"))
				.andExpect(status().is(400))
				.andReturn()
				.getResponse()
				.getContentAsString();

		assertEquals(NO_USERID_AND_USERNAME_ERROR, result);
    }

    
  //Testing the getusercontacts api with invalid user id
    
    @Test    
    public void userAPIInvalidIdTest() throws UnsupportedEncodingException, Exception {
        
    	mockMvc = MockMvcBuilders
    	        .standaloneSetup(userReadingApplicationAPI).build();
    	
    	userReadingApplicationAPI.setUserHttpRequest(dummy);
    	
    	String result = mockMvc.perform(get("http://test/api/getusercontacts").param("id", "p"))
				.andExpect(status().is(400))
				.andReturn()
				.getResponse()
				.getContentAsString();

		assertEquals(USERID_INVALID_ERROR, result);
    }    
 
    
  //Testing the getusercontacts api by providing a non existing user id
    
    @Test    
    public void userAPIUserNotFoundTest() throws UnsupportedEncodingException, Exception {
        
    	
    	
    	mockMvc = MockMvcBuilders
    	        .standaloneSetup(userReadingApplicationAPI).build();
    	
    	userReadingApplicationAPI.setUserHttpRequest(dummy);
    	
		String result = mockMvc.perform(get("http://test/api/getusercontacts").param("id", "0"))
				.andExpect(status().is(200))
				.andReturn()
				.getResponse()
				.getContentAsString();
		Gson gson = new Gson();	
		
		ResultNotFoundResponse res = gson.fromJson(result, ResultNotFoundResponse.class);
		
		
		assertEquals(res.getId(), -1);
    }    
    
    
  //Testing the getusercontacts api with an existing user id in dummy data
    
    @Test    
    public void userAPIByIdTest1() throws UnsupportedEncodingException, Exception {
        
    	
    	
    	mockMvc = MockMvcBuilders
    	        .standaloneSetup(userReadingApplicationAPI).build();
    	
    	userReadingApplicationAPI.setUserHttpRequest(dummy);
    	
		String result = mockMvc.perform(get("http://test/api/getusercontacts").param("id", "1"))
				.andExpect(status().is(200))
				.andReturn()
				.getResponse()
				.getContentAsString();
		Gson gson = new Gson();	
		
		UserContact res = gson.fromJson(result, UserContact.class);
		
		
		assertEquals(res.getId(), 1);
		assertEquals(res.getName(), "Fifi Lai");
		assertEquals(res.getUsername(), "fifilai");
		assertEquals(res.getEmail(), "fifilai@gmail.com");
		assertEquals(res.getPhone(), "123456789");
    } 
    
    
  //Testing the getusercontacts api with another exist user id in dummy data
    
    @Test    
    public void userAPIByIdTest2() throws UnsupportedEncodingException, Exception {
        
    	
    	
    	mockMvc = MockMvcBuilders
    	        .standaloneSetup(userReadingApplicationAPI).build();
    	
    	userReadingApplicationAPI.setUserHttpRequest(dummy);
    	
		String result = mockMvc.perform(get("http://test/api/getusercontacts").param("id", "2"))
				.andExpect(status().is(200))
				.andReturn()
				.getResponse()
				.getContentAsString();
		Gson gson = new Gson();	
		
		UserContact res = gson.fromJson(result, UserContact.class);
		
		
		assertEquals(res.getId(), 2);
		assertEquals(res.getName(), "catcat");
		assertEquals(res.getUsername(), "catcat");
		assertEquals(res.getEmail(), "iamacat@gmail.com");
		assertEquals(res.getPhone(), "987654321");
    } 
    
  //Testing the getusercontacts api by providing a non existing username
    
    @Test    
    public void userAPIUserNotFoundByUsernameTest() throws UnsupportedEncodingException, Exception {
        
    	
    	
    	mockMvc = MockMvcBuilders
    	        .standaloneSetup(userReadingApplicationAPI).build();
    	
    	userReadingApplicationAPI.setUserHttpRequest(dummy);
    	
		String result = mockMvc.perform(get("http://test/api/getusercontacts").param("username", "johndoe"))
				.andExpect(status().is(200))
				.andReturn()
				.getResponse()
				.getContentAsString();
		Gson gson = new Gson();	
		
		ResultNotFoundResponse res = gson.fromJson(result, ResultNotFoundResponse.class);
		
		
		assertEquals(res.getId(), -1);
    }     
    
  //Testing the getusercontacts api with by an exact match username
    
    @Test    
    public void userAPIByUserNameExactMatch() throws UnsupportedEncodingException, Exception {
        
    	
    	
    	mockMvc = MockMvcBuilders
    	        .standaloneSetup(userReadingApplicationAPI).build();
    	
    	userReadingApplicationAPI.setUserHttpRequest(dummy);
    	
		String result = mockMvc.perform(get("http://test/api/getusercontacts").param("username", "fifilai"))
				.andExpect(status().is(200))
				.andReturn()
				.getResponse()
				.getContentAsString();
		Gson gson = new Gson();	
		
		UserContact res = gson.fromJson(result, UserContact.class);
		
		
		assertEquals(res.getId(), 1);
		assertEquals(res.getName(), "Fifi Lai");
		assertEquals(res.getUsername(), "fifilai");
		assertEquals(res.getEmail(), "fifilai@gmail.com");
		assertEquals(res.getPhone(), "123456789");
    }  
    
  //Testing the getusercontacts api with by an partial match username
    
    @Test    
    public void userAPIByUserNamePartialMatch() throws UnsupportedEncodingException, Exception {
        
    	
    	
    	mockMvc = MockMvcBuilders
    	        .standaloneSetup(userReadingApplicationAPI).build();
    	
    	userReadingApplicationAPI.setUserHttpRequest(dummy);
    	
		String result = mockMvc.perform(get("http://test/api/getusercontacts").param("username", "fifi"))
				.andExpect(status().is(200))
				.andReturn()
				.getResponse()
				.getContentAsString();
		Gson gson = new Gson();	
		
		UserContact res = gson.fromJson(result, UserContact.class);
		
		
		assertEquals(res.getId(), 1);
		assertEquals(res.getName(), "Fifi Lai");
		assertEquals(res.getUsername(), "fifilai");
		assertEquals(res.getEmail(), "fifilai@gmail.com");
		assertEquals(res.getPhone(), "123456789");
    }     
}

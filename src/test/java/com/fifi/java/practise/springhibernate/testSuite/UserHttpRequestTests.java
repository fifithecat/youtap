package com.fifi.java.practise.springhibernate.testSuite;



import static org.junit.Assert.assertTrue;


import java.io.UnsupportedEncodingException;

import java.util.List;



import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;


import com.fifi.java.practise.springhibernate.requestInterface.UserHttpRequest;


//Testing the component that load data from the 3rd API

@EnableAutoConfiguration
@RunWith(SpringRunner.class)
@SpringBootTest
@ComponentScan(basePackages = {"com.fifi.java.practise.springhibernate.dummy"})
class UserHttpRequestTests {
	

	@Value("${USER_URL}")
	private String USER_URL;


	
	@Autowired
	@Qualifier("dummyHttpRequest")
	private UserHttpRequest userRequest;
	
    
    @Test    
    public void userAPITest() throws UnsupportedEncodingException, Exception {

    	List list = userRequest.requestUser(USER_URL);
    	assertTrue(list.size() > 0);
    }	    
	
}

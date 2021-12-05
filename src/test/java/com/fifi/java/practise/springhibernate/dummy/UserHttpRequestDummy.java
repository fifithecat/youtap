package com.fifi.java.practise.springhibernate.dummy;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fifi.java.practise.springhibernate.request.obj.User;
import com.fifi.java.practise.springhibernate.requestInterface.UserHttpRequest;


@Component
@Qualifier("dummyHttpRequest")
public class UserHttpRequestDummy implements UserHttpRequest {
		
	@Value("${THIRD_PARTY_FAILURE_ERROR}")
	private String THIRD_PARTY_FAILURE_ERROR;
	
	public List<User> requestUser(String userURL) throws Exception {
		
		
		User user = new User();
		user.setId(1L);
		user.setName("Fifi Lai");
		user.setUsername("fifilai");
		user.setEmail("fifilai@gmail.com");
		user.setPhone("123456789");

		User user2 = new User();
		user2.setId(2L);
		user2.setName("catcat");
		user2.setUsername("catcat");
		user2.setEmail("iamacat@gmail.com");
		user2.setPhone("987654321");		
		
		List users = new ArrayList();
		
		users.add(user);
		users.add(user2);
		
	    return users;
	}	

		
}




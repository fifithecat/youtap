package com.fifi.java.practise.springhibernate.requestInterface;




import java.util.List;


import org.springframework.stereotype.Component;

import com.fifi.java.practise.springhibernate.request.obj.User;


@Component
public interface UserHttpRequest {
		
	public List<User> requestUser(String userURL) throws Exception;	

}




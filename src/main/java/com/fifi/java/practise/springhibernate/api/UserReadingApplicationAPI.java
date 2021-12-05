package com.fifi.java.practise.springhibernate.api;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.fifi.java.practise.springhibernate.requestInterface.UserHttpRequest;
import com.fifi.java.practise.springhibernate.request.obj.User;
import com.fifi.java.practise.springhibernate.response.obj.ResultNotFoundResponse;
import com.fifi.java.practise.springhibernate.response.obj.UserContact;

@RestController
@RequestMapping("/api")

public class UserReadingApplicationAPI
{

	@Value("${USER_URL}")
	private String USER_URL;
	
	@Value("${NO_USERID_AND_USERNAME_ERROR}")
	private String NO_USERID_AND_USERNAME_ERROR;
	
	@Value("${USERID_INVALID_ERROR}")
	private String USERID_INVALID_ERROR;
	
	@Autowired
	@Qualifier("productionHttpRequest")
	private UserHttpRequest userHttpRequest;
	
	@GetMapping(path = "/echo", produces=MediaType.TEXT_PLAIN_VALUE)
	@ResponseBody
	public ResponseEntity<Object> echo() {					
        return new ResponseEntity<Object>("Hello World", HttpStatus.OK);
	}    

	@GetMapping(path = "/getusercontacts", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getUser(@RequestParam(value = "id", required = false) Long userId, @RequestParam(value = "username", required = false) String userName) throws Exception {
		

		if ((userId == null && userName == null) || (userId != null && userName != null))	{
			throw new Exception(NO_USERID_AND_USERNAME_ERROR);
		}
		
		
		List<User> users = userHttpRequest.requestUser(USER_URL);
		
		
		Optional<User> result = users.parallelStream().filter(user -> user.getId().equals(userId) || (userName != null && user.getUsername().toLowerCase().contains(userName.toLowerCase()))  ).findAny();
		
		User userResult;
		try {
			userResult = result.get();
		}	catch (NoSuchElementException exception)	{
			return new ResponseEntity<Object>(new ResultNotFoundResponse(), HttpStatus.OK);
		}

		UserContact userContactReturn = new UserContact(userResult.getId(), userResult.getName(), userResult.getUsername(), userResult.getEmail(), userResult.getPhone());
		
        return new ResponseEntity<Object>(userContactReturn, HttpStatus.OK);
	}
	
	@ExceptionHandler({Exception.class})
	public ResponseEntity<String> rulesForUserNotFound(HttpServletRequest req, Exception e) 
	{
		String error = "";
		if (e instanceof MethodArgumentTypeMismatchException)	{
			error = USERID_INVALID_ERROR;
		}	else	{
			error = new String(e.getMessage());
		}
		return new ResponseEntity<String>(error, HttpStatus.BAD_REQUEST);
	}

	public UserHttpRequest getUserHttpRequest() {
		return userHttpRequest;
	}

	public void setUserHttpRequest(UserHttpRequest userHttpRequest) {
		this.userHttpRequest = userHttpRequest;
	}

}

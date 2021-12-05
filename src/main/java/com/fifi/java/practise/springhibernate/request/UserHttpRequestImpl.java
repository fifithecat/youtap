package com.fifi.java.practise.springhibernate.request;


import java.io.BufferedReader;

import java.io.InputStreamReader;

import java.net.HttpURLConnection;
import java.net.URL;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fifi.java.practise.springhibernate.request.obj.User;
import com.fifi.java.practise.springhibernate.requestInterface.UserHttpRequest;
import com.fifi.java.practise.util.JsonUtil;

@Component
@Qualifier("productionHttpRequest")
public class UserHttpRequestImpl implements UserHttpRequest {
		
	@Value("${THIRD_PARTY_FAILURE_ERROR}")
	private String THIRD_PARTY_FAILURE_ERROR;
	
	public List<User> requestUser(String userURL) throws Exception {
		
	    URL urlForGetRequest = new URL(userURL);
	    String readLine = null;
	    String result = "";
	    HttpURLConnection conection = (HttpURLConnection) urlForGetRequest.openConnection();
	    conection.setRequestMethod("GET");

	    int responseCode = conection.getResponseCode();
	    List<User> users = null;
	    
	    try	{	    
		    if (responseCode == HttpURLConnection.HTTP_OK) {
		        BufferedReader in = new BufferedReader(new InputStreamReader(conection.getInputStream()));
	
		        while ((readLine = in .readLine()) != null) {
	
		            result = result + readLine;
		        } 
		        in.close();	    	   
		        
		        users = JsonUtil.jsonListToJavaObjectList(result, User.class);
		        	      	      
		    } else {
		        throw new Exception();
		    }
	    }	catch (Exception exception)	{
	    	throw new Exception(THIRD_PARTY_FAILURE_ERROR);
	    }
	    return users;
	}	

	
	
//	//workaround for setting static field from properties file
//	@Value("${THIRD_PARTY_FAILURE_ERROR}")
//	public void setThirdPartyFailureError(String thirdPartyFailureError) {
//		THIRD_PARTY_FAILURE_ERROR = thirdPartyFailureError;
//	}  
		
}




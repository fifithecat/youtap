package com.fifi.java.practise.util;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class JsonUtil {

	public static <E> List jsonListToJavaObjectList(String jsonString, Class objClass)	{
		Gson gson = new Gson();	
		
        Type listType = TypeToken.getParameterized(ArrayList.class, objClass).getType();
        List list = gson.fromJson(jsonString, listType);	
        
        return list;
        
	}
}

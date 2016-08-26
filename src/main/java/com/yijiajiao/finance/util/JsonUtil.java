package com.yijiajiao.finance.util;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import org.codehaus.jackson.map.ObjectMapper;

import java.net.URLDecoder;
import java.util.List;

public class JsonUtil {
	// json转化成对象
	public static Object getTransObject(String param, Class<?> clazz)
			throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		Object obj = mapper.readValue(URLDecoder.decode(param, "UTF-8"), clazz);
		return obj;
	}
	//对象转换成json
	public static <T> String objectToJson(T t){
		JSONObject object = JSONObject.fromObject(t);
		return object.toString();
	}
	
	//json转换成list集合
	public static <T> List<T> jsonToList(String json,Class<T> clazz){
        System.out.println("json:"+json);
        JSONArray arry=JSONArray.fromObject(json);
        return JSONArray.toList(arry,clazz);
	}
	//lis转成json
    public static String listToJson(List<?> list)
    {
        return JSONSerializer.toJSON(list).toString();
    }
}

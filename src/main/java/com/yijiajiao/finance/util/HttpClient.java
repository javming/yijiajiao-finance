package com.yijiajiao.finance.util;

import java.net.URLDecoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

import javax.ws.rs.core.MediaType;

import org.codehaus.jackson.map.ObjectMapper;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.WebResource.Builder;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;

public class HttpClient {

	/**
	 * httpclient请求器 （格式1）
	 * 
	 * @param server
	 *            服务器地址
	 * @param url
	 *            资源URL
	 * @param headParams
	 *            请求头参数列表
	 * @param bodyParam
	 *            Body参数
	 * @param method
	 *            请求方式（POST,GET,PUT,DELETE）
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static String httpRest(String server, String url,
			Map<String, Object> headParams, Object bodyParam, String method) {
		ClientConfig config = new DefaultClientConfig();
		config.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING,
				Boolean.TRUE);
		Client c = Client.create(config);
		WebResource r = c.resource("http://"+server + url);
		System.out.println("请求其它系统路径："+"http://"+server + url);
		Builder builder = r.header("Content-Type", MediaType.APPLICATION_JSON)
				.type(MediaType.APPLICATION_JSON);
		ClientResponse response = null;
		if (headParams != null) {
			Iterator i = headParams.entrySet().iterator();
			while (i.hasNext()) {
				Map.Entry en = (Map.Entry) i.next();
				builder.header((String) en.getKey(), en.getValue());
			}
		}
		if (method.equals("POST")) {
			response = builder.post(ClientResponse.class, bodyParam);
		} else if (method.equals("GET")) {
			response = builder.get(ClientResponse.class);
		} else if (method.equals("PUT")) {
			response = builder.put(ClientResponse.class, bodyParam);
		} else if (method.equals("DELETE")) {
			response = builder.delete(ClientResponse.class);
		}
		String result = response.getEntity(String.class);

		return result;
	}

	/**
	 * httpclient请求器 （格式2） //不支持请求头放参数
	 * 
	 * @param url
	 *            服务器地址 + 资源URL
	 * @param bodyParam
	 *            Body参数
	 * @param method
	 *            请求方式（POST,GET,PUT,DELETE）
	 * @return
	 */

	public static ClientResponse resource(String url, Object bodyParam,
			String method) {
		ClientConfig config = new DefaultClientConfig();
		config.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING,
				Boolean.TRUE);
		Client c = Client.create(config);
		WebResource r = c.resource(url);
		ClientResponse response = null;
		if ("POST".equals(method)) {
			response = r.header("Content-Type", MediaType.APPLICATION_JSON)
					.type(MediaType.APPLICATION_JSON)
					.post(ClientResponse.class, bodyParam);
		} else if ("GET".equals(method)) {
			response = r.accept(MediaType.APPLICATION_JSON).get(
					ClientResponse.class);
		} else if ("PUT".equals(method)) {
			response = r.header("Content-Type", MediaType.APPLICATION_JSON)
					.type(MediaType.APPLICATION_JSON)
					.put(ClientResponse.class, bodyParam);
		}
		return response;
	}

	// json转化成对象
	public static Object getTransObject(String param, Class<?> clazz)
			throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		Object obj = mapper.readValue(URLDecoder.decode(param, "UTF-8"), clazz);
		return obj;
	}

	public static Date long2Date(long time) throws ParseException {
		Date result = null;
		String since = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
				.format(new Date(time * 1000));
		result = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(since);
		return result;
	}

	public static String getDateStr() throws ParseException {
		String result = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
				.format(new Date());
		return result;
	}

}

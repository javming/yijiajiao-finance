package com.yijiajiao.finance.controller;

import com.yijiajiao.finance.util.Config;
import com.yijiajiao.finance.util.HttpClient;
import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.XMemcachedClient;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;


@Controller
@Path("/testconnect")
public class TestConnect{
	private final String serverip="127.0.0.1:22040";
	private final String kestrel="/command";
	private final String server="/makeqrcode";

	@GET
	@Path("/kestrel")
	public String testKestrel(@Context HttpServletResponse resp) throws Exception{
		String memcachedIp = Config.getString("memcached.ip");
		String memcachedPort = Config.getString("memcached.port");
		String tag = RandomStringUtils.randomAlphanumeric(32);
		String data ="{\"tag\":\""+tag+"\",\"cmd\":\"ConnectTest\",\"token\":\"TK05E0921DE083A888T\",\"openId\":\"d6486b55-b6fa-42d0-8cde-163996940b\"}";
		HttpClient.httpRest(serverip, kestrel, null, data, "POST");
		MemcachedClient memcachedClient = getMemcacheClient(memcachedIp, memcachedPort);
		String result =null;
		for(int i=0;i<5;i++){
			Thread.sleep(500);
			  result  = memcachedClient.get(tag);
			  if(result != null) break;
		}
		if(result != null ){
			resp.setStatus(HttpServletResponse.SC_OK);
			resp.addHeader("code", "200");

		}else{
				resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				resp.addHeader("code", HttpServletResponse.SC_INTERNAL_SERVER_ERROR+"");
				throw new RuntimeException();
		}
		System.out.println("result ===|"+result);
		return "Connect successful!";
	}
	
	@GET
	@Path("/server")
	public String testServer(@Context HttpServletResponse resp)throws Exception{
		String httpRest = HttpClient.httpRest(serverip,server, null, null, "GET");
		System.out.println("请求其他系统返回"+httpRest);
		if(httpRest != null || "".equals(httpRest)){
			resp.setStatus(HttpServletResponse.SC_OK);
			resp.addHeader("code", "200");
		}else{
			resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			resp.addHeader("code", HttpServletResponse.SC_INTERNAL_SERVER_ERROR+"");
			throw new RuntimeException();
		}
		return "Connect successful!";
	}
	
	public MemcachedClient getMemcacheClient(String server, String port)throws Exception {
		int p = Integer.parseInt(port);
		MemcachedClient	client = new XMemcachedClient(server, p);
		return client;
	}
}

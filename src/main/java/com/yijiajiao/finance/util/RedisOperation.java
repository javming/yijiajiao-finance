package com.yijiajiao.finance.util;


import ch.qos.logback.classic.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;

import java.util.Set;


public class RedisOperation {

  private static Logger    log           = (Logger) LoggerFactory.getLogger(RedisOperation.class.getName());

  private Jedis            jedis         = null;
  private int expireTime = Config.getInt("redis.expireTime");
  
  public void putRedis(String userkey, String v_code,int expire) throws Exception {  
    log.info("v_code:" + v_code + " username:" + userkey );
    jedis = JedisPoolUtil.pool.getResource();
    jedis.setex(userkey,expire, v_code);
    JedisPoolUtil.pool.returnResource(jedis);
  }

  public boolean getToken(String token) {
    boolean flag = false;
    if ("".equals(token) || token == null) {
      return flag;
    }
    jedis = JedisPoolUtil.pool.getResource();
    flag = jedis.exists(token);
    JedisPoolUtil.pool.returnResource(jedis);
    return flag;
  }

  public String getValue(String token) {
    String value = "";
    if ("".equals(token) || token == null) {
      return value;
    }
    jedis = JedisPoolUtil.pool.getResource();
    value = jedis.get(token);
    JedisPoolUtil.pool.returnResource(jedis);
    return value;
  }
  
  public long expireToken(String key){
	    if ("".equals(key) || key == null) {
	        return -1;
	      }
	    jedis.expire(key, expireTime);
	    return jedis.ttl(key);
  }
  public long ttl(String key){
	    if ("".equals(key) || key == null) {
	        return -1;
	      }
	  return jedis.ttl(key);
  }
 public Set<String> keys(String value){
	 return jedis.keys(value);
 }

}

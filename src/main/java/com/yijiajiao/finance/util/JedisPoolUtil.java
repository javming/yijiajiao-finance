package com.yijiajiao.finance.util;


import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class JedisPoolUtil {

  public static JedisPool pool;

  private static String   redisIp = Config.getString("redis.ip");

  static {
    JedisPoolConfig config = new JedisPoolConfig();

    config.setTestOnBorrow(true);
    pool = new JedisPool(config, redisIp);
  }

}

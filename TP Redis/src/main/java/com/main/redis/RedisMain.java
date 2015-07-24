package com.main.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Scanner;

public class RedisMain {

	public static void main(String[] args) {
		JedisPool jedisPool = new JedisPool("localhost");
		try{
			Jedis jedis = jedisPool.getResource();
			String result = jedis.get("msg");
			System.out.println(result);
			
			jedis.set("msg_java","Hello Java");
			jedisPool.returnResource(jedis);
					
			String key, value;
			
			Scanner sc = new Scanner(System.in);
			do {
				System.out.println ("veuillez entrer la clé");
				key = sc.next();
				
				System.out.println ("veuillez entrer la valeur");
				value = sc.next();
				
				jedis.set(key,value);
				jedisPool.returnResource(jedis);
				
				System.out.println(key + " "+ value);
				System.out.println(key+ " "+jedis.get(key));
				
				
				
			} while (!key.equals(""));
			
			sc.close();
			
		} finally {
			jedisPool.destroy();
		}
		
	}

}

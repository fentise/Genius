package com.example.Genius.utils;

import com.example.Genius.model.Article;
import com.example.Genius.service.ArticleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class JedisAdapter implements InitializingBean {

    private static final Logger logger = LoggerFactory.getLogger(JedisAdapter.class);


    private JedisPool jedisPool;

    public static void print(int index,Object obj) {
        System.out.println(String.format("%d, %s",index, obj.toString()));
    }

    public static void main(String[] argv) {

        Jedis jedis = new Jedis("redis://localhost:6379/9");
        jedis.flushDB();

        jedis.set("test","this is a  key value");
        print(1,jedis.get("test"));
        jedis.rename("test","newTest");
        print(1,jedis.get("newTest"));

        jedis.setex("newTest",5,"value");

        jedis.set("pv","100");
        jedis.incr("pv");
        print(2,jedis.get("pv"));
        jedis.incrBy("pv",5);
        print(2,jedis.get("pv"));

        print(3,jedis.keys("*"));

        String listName = "list";

        jedis.del(listName);

        for(int i = 0;i < 10;++ i)
        {
            jedis.lpush(listName,"list-L-" + String.valueOf(i));
            jedis.rpush(listName,"list-R-" + String.valueOf(i));
        }
        print(3,jedis.lrange(listName,0,20));
        print(4,jedis.llen(listName));
        jedis.lpop(listName);
        print(5,jedis.lrange(listName,0,20));

        String hashName = "hash";
        Map<String,String> hashMap = new HashMap<>();
        hashMap.put("name","sun");
        hashMap.put("age","20");
        hashMap.put("school","scut");
        jedis.hmset(hashName,hashMap);
        print(6,jedis.hget(hashName,"name"));
        print(6,jedis.hgetAll(hashName));
        jedis.hincrBy(hashName,"age",1);
        print(6,jedis.hgetAll(hashName));
        print(7,jedis.hkeys(hashName));
        print(8,jedis.hvals(hashName));
        jedis.hsetnx(hashName,"name","bin");
        jedis.hsetnx(hashName,"mail","110@qq.com");
        print(9,jedis.hgetAll(hashName));

        String set1 = "set1";
        String set2 = "set2";

        for(int i = 0;i < 10;++ i) {
            jedis.sadd(set1,String.valueOf(i));
            jedis.sadd(set2,String.valueOf(i*i));
        }
        print(10,jedis.smembers(set1));
        print(11,jedis.sunion(set1,set2));
        print(12,jedis.sinter(set1,set2));

        jedis.srem(set2,"100");
        jedis.smove(set1,set2,"8");
        print(13,jedis.smembers(set1));
        print(14,jedis.smembers(set2));

        String zSet = "priority";
        jedis.zadd(zSet,1,"sun");
        print(14,jedis.zscore(zSet,"sun"));
        jedis.zadd(zSet,100,"sun");
        print(15,jedis.zrange(zSet,0,1));
        print(16,jedis.zscore(zSet,"sun"));

    }

    @Override
    public void afterPropertiesSet() throws Exception {
        jedisPool = new JedisPool("redis://localhost:6379/1");       //初始化连接数据库
    }

    public long sadd(String key,String value) {
        Jedis jedis = null;
        try{
            jedis = jedisPool.getResource();
            return jedis.sadd(key,value);
        }catch (Exception e){
            logger.error("发生异常" + e.getMessage());
        }finally {
            if(jedis != null)
                jedis.close();
        }
        return 0;
    }

    public long srem(String key,String value) {
        Jedis jedis = null;
        try{
            jedis = jedisPool.getResource();
            return jedis.srem(key,value);
        }catch (Exception e){
            logger.error("发生异常" + e.getMessage());
        }finally {
            if(jedis != null)
                jedis.close();
        }
        return 0;
    }

    public long scard(String key) {
        Jedis jedis = null;
        try{
            jedis = jedisPool.getResource();
            return jedis.scard(key);
        }catch (Exception e){
            logger.error("发生异常" + e.getMessage());
        }finally {
            if(jedis != null)
                jedis.close();
        }
        return 0;
    }

    public boolean sismember(String key,String value) {
        Jedis jedis = null;
        try{
            jedis = jedisPool.getResource();
            return jedis.sismember(key,value);
        }catch (Exception e){
            logger.error("发生异常" + e.getMessage());
        }finally {
            if(jedis != null)
                jedis.close();
        }
        return false;
    }


}

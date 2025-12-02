package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import javax.annotation.Resource;

/**
 * @author lidaye
 */
@SpringBootTest
public class RedisTest {
    @Resource
    private RedisTemplate<String,Object> redisTemplate;
    @Test
    public void test1() {

        redisTemplate.opsForValue().set("lxk4","123");
        Object lxk = redisTemplate.opsForValue().get("lxk4");
        System.out.println(lxk);
    }
    @Test
    public void test2() {

        Boolean lxkssg1 = redisTemplate.opsForValue().setIfAbsent("lxkssg", "123");
        Boolean lxkssg = redisTemplate.opsForValue().setIfAbsent("lxkssg", "231");
        System.out.println(lxkssg);
        System.out.println(lxkssg1);
        Boolean lxkssg2 = redisTemplate.delete("lxkssg");
        System.out.println(lxkssg2);
        Object lxk = redisTemplate.opsForValue().get("lxkssg");
        System.out.println(lxk);
    }

}


package com.wang.wiki.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * Redis缓存工具类
 *
 * @author Wang
 */
@Component
public class RedisUtil {

    private static final Logger LOG = LoggerFactory.getLogger(RedisUtil.class);

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 判断Redis缓存中是否存在相同key，是否过期
     *
     * @param key    key
     * @param second 过期时间单位s
     * @return true：不存在，放一个KEY, false：已存在
     */
    public boolean validateRepeat(String key, long second) {
        if (Boolean.TRUE.equals(redisTemplate.hasKey(key))) {
            LOG.info("key已存在：{}", key);
            return false;
        } else {
            LOG.info("key不存在，放入：{}，过期 {} 秒", key, second);
            redisTemplate.opsForValue().set(key, key, second, TimeUnit.SECONDS);
            return true;
        }
    }
}

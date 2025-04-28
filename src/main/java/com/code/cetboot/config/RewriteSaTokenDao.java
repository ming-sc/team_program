package com.code.cetboot.config;

import cn.dev33.satoken.dao.SaTokenDaoRedisJackson;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author : IMG
 * @create : 2025/4/26
 * @description : 重写 sa-token 的 Redis 存储类，添加前缀
 */
@Component
@Primary
public class RewriteSaTokenDao extends SaTokenDaoRedisJackson {

    @Value("${spring.cache.redis.key-prefix}")
    private String keyPrefix;

    /**
     * 重写获取Redis的key
     *
     * @param key Redis的key
     * @return Redis的key
     */
    public String getRedisKey(String key) {
        return key.startsWith(keyPrefix) ? key : keyPrefix + ":" + key;
    }

    @Override
    public String get(String key) {
        return super.get(getRedisKey(key));
    }

    @Override
    public void set(String key, String value, long timeout) {
        super.set(getRedisKey(key), value, timeout);
    }

    @Override
    public void update(String key, String value) {
        super.update(getRedisKey(key), value);
    }

    @Override
    public void delete(String key) {
        super.delete(getRedisKey(key));
    }

    @Override
    public long getTimeout(String key) {
        return super.getTimeout(getRedisKey(key));
    }

    @Override
    public void updateTimeout(String key, long timeout) {
        super.updateTimeout(getRedisKey(key), timeout);
    }

    @Override
    public Object getObject(String key) {
        return super.getObject(getRedisKey(key));
    }

    @Override
    public void setObject(String key, Object object, long timeout) {
        super.setObject(getRedisKey(key), object, timeout);
    }

    @Override
    public void updateObject(String key, Object object) {
        super.updateObject(getRedisKey(key), object);
    }

    @Override
    public void deleteObject(String key) {
        super.deleteObject(getRedisKey(key));
    }

    @Override
    public long getObjectTimeout(String key) {
        return super.getObjectTimeout(getRedisKey(key));
    }

    @Override
    public void updateObjectTimeout(String key, long timeout) {
        super.updateObjectTimeout(getRedisKey(key), timeout);
    }

    @Override
    public List<String> searchData(String prefix, String keyword, int start, int size, boolean sortType) {
        return super.searchData(getRedisKey(prefix), keyword, start, size, sortType);
    }
}

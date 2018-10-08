package com.daicy.ad.push.service.impl;

import com.daicy.ad.push.bean.TestB;
import com.daicy.ad.push.common.util.JsonUtils;
import com.daicy.ad.push.dao.TestBMapper;
import com.daicy.ad.push.redis.RedisUtil;
import com.daicy.ad.push.service.TestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
@Slf4j
public class TestServiceImpl implements TestService {

    @Resource
    private TestBMapper testBMapper;

    @Cacheable(value = "test2", key = "#id")
    @Override
    public TestB get(Long id) {
        // TODO 我们就假设它是从数据库读取出来的
        log.info("进入 get 方法");
        TestB testB = testBMapper.selectByPrimaryKey(id.intValue());
        return testB;
    }

    @CachePut(value = "test2", key = "#testB.id")
    @Override
    public Long insert(TestB testB) {
        int id =  testBMapper.insert(testB);
        log.info("进入 saveOrUpdate 方法");
        return Long.valueOf(id);
    }

    @CacheEvict(value = "test2", key = "#id", allEntries = true, beforeInvocation = true)
    @Override
    public void delete(Long id) {
        testBMapper.deleteByPrimaryKey(id.intValue());
        log.info("进入 delete 方法");
    }
}

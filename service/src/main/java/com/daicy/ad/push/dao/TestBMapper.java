package com.daicy.ad.push.dao;

import com.daicy.ad.push.bean.TestB;

public interface TestBMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TestB record);

    int insertSelective(TestB record);

    TestB selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TestB record);

    int updateByPrimaryKey(TestB record);
}
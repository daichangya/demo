package com.daicy.ad.push.service;


import com.daicy.ad.push.bean.TestB;

/**
 * @author Administrator
 */
public interface TestService {

    /**
     * add
     *
     * @param testB
     * @return 操作结果
     */
    Long insert(TestB testB);

    /**
     * get
     *
     * @param id key值
     * @return 返回结果
     */
    TestB get(Long id);

    /**
     * 删除
     *
     * @param id key值
     */
    void delete(Long id);
}

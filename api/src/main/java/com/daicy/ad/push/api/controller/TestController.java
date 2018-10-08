package com.daicy.ad.push.api.controller;

import com.alibaba.druid.stat.DruidStatManagerFacade;
import com.daicy.ad.push.common.util.JsonUtils;
import com.daicy.ad.push.service.TestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.metrics.CounterService;
import org.springframework.boot.actuate.metrics.GaugeService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
@Slf4j
public class TestController {

    @Autowired
    private CounterService counterService;
    @Autowired
    private GaugeService gaugeService;

    @Resource
    private TestService testService;

    private AtomicInteger atomicIntegerAl = new AtomicInteger(0);

    private AtomicInteger atomicIntegerTx = new AtomicInteger(0);


    @RequestMapping("/al123456")
    public String alSengdCode(@RequestParam("message") String message, HttpServletResponse response) {
        long startTime = System.currentTimeMillis();
        counterService.increment("daicy.al.count");
        try {
            Thread.sleep(3000);
            String result = "albb" + message;
            log.info(result + atomicIntegerAl.incrementAndGet());
            return result;
        } catch (Exception e) {
            log.error("dddddddddddd",e);
        }finally {
            gaugeService.submit("daicy.al.time", System.currentTimeMillis() - startTime + 8);
        }
        return null;
    }

    @RequestMapping("/get")
    public String get(@RequestParam("id") Long id, HttpServletResponse response) {
       return JsonUtils.encode(testService.get(id));
    }
    @RequestMapping("/del")
    public String del(@RequestParam("id") Long id, HttpServletResponse response) {
        testService.delete(id);
        return JsonUtils.encode(true);
    }
    @RequestMapping("/druid/stat")
    public Object druidStat(){
        // DruidStatManagerFacade#getDataSourceStatDataList 该方法可以获取所有数据源的监控数据，除此之外 DruidStatManagerFacade 还提供了一些其他方法，你可以按需选择使用。
        return DruidStatManagerFacade.getInstance().getDataSourceStatDataList();
    }

}

package com.wang.wiki.test.controller;

import com.wang.wiki.test.domain.Test;
import com.wang.wiki.test.service.TestService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Wang
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @Resource
    private TestService testService;

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String hello() {
        return "hello";
    }

    @RequestMapping(value = "/hello/post", method = RequestMethod.POST)
    public String hello(String name) {
        return "hello! post," + name;
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<Test> list() {
        return testService.list();
    }
}

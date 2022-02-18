package com.wang.wiki.test.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Wang
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String hello() {
        return "hello";
    }

    @RequestMapping(value = "/hello/post", method = RequestMethod.POST)
    public String hello(String name) {
        return "hello! post," + name;
    }
}

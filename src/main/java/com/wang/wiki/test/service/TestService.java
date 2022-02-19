package com.wang.wiki.test.service;

import com.wang.wiki.test.domain.Test;
import com.wang.wiki.test.mapper.TestMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Wang
 */
@Service
public class TestService {

    @Resource
    private TestMapper testMapper;

    public List<Test> list(){
        return testMapper.selectList(null);
    }
}

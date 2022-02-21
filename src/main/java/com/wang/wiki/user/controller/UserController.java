package com.wang.wiki.user.controller;

import com.alibaba.fastjson.JSONObject;
import com.wang.wiki.resp.CommonResp;
import com.wang.wiki.resp.PageResp;
import com.wang.wiki.user.service.UserService;
import com.wang.wiki.user.vo.UserVO;
import com.wang.wiki.util.SnowFlake;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.concurrent.TimeUnit;

/**
 * @author Wang
 */
@RestController
@RequestMapping("/user")
public class UserController {

    private static final Logger LOG = LoggerFactory.getLogger(UserController.class);

    @Resource
    private UserService userService;

    @Resource
    private SnowFlake snowFlake;

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 分页查询用户列表
     * @param req 查询条件
     * @return 查询结果
     */
    @GetMapping("/list")
    public CommonResp<PageResp<UserVO>> list(@Valid UserVO req) {
        CommonResp<PageResp<UserVO>> resp = new CommonResp<>();
        PageResp<UserVO> list = userService.list(req);
        resp.setContent(list);
        return resp;
    }

    /**
     * 新增/修改用户
     * @param req 新增/修改用户内容
     * @return 返回结果
     */
    @PostMapping("/save")
    public CommonResp<Integer> save(@Valid @RequestBody UserVO req) {
        req.setPassword(DigestUtils.md5DigestAsHex(req.getPassword().getBytes()));
        CommonResp<Integer> resp = new CommonResp<>();
        int index = userService.save(req);
        resp.setContent(index);
        return resp;
    }

    /**
     * 删除用户
     * @param id 用户Id
     * @return 返回结果
     */
    @DeleteMapping("/delete/{id}")
    public CommonResp<Integer> delete(@PathVariable Long id) {
        CommonResp<Integer> resp = new CommonResp<>();
        int index = userService.delete(id);
        resp.setContent(index);
        return resp;
    }

    /**
     * 修改密码
     * @param req 修改用户内容
     * @return 返回结果
     */
    @PostMapping("/reset-password")
    public CommonResp<Integer> resetPassword(@Valid @RequestBody UserVO req) {
        req.setPassword(DigestUtils.md5DigestAsHex(req.getPassword().getBytes()));
        CommonResp<Integer> resp = new CommonResp<>();
        int index = userService.resetPassword(req);
        resp.setContent(index);
        return resp;
    }

    /**
     * 登录
     * @param req 登录用户内容
     * @return 返回结果
     */
    @PostMapping("/login")
    public CommonResp<UserVO> login(@Valid @RequestBody UserVO req) {
        req.setPassword(DigestUtils.md5DigestAsHex(req.getPassword().getBytes()));
        CommonResp<UserVO> resp = new CommonResp<>();
        UserVO userLoginResp = userService.login(req);

        Long token = snowFlake.nextId();
        LOG.info("生成单点登录token：{}，并放入redis中", token);
        userLoginResp.setToken(token.toString());
        redisTemplate.opsForValue().set(token.toString(), JSONObject.toJSONString(userLoginResp), 3600 * 24, TimeUnit.SECONDS);

        resp.setContent(userLoginResp);
        return resp;
    }

    /**
     * 登出
     * @param token 登出用户token
     * @return 返回结果
     */
    @GetMapping("/logout/{token}")
    public CommonResp<Boolean> logout(@PathVariable String token) {
        CommonResp<Boolean> resp = new CommonResp<>();
        boolean bo = Boolean.TRUE.equals(redisTemplate.delete(token));
        resp.setContent(bo);
        LOG.info("从redis中删除token: {}", token);
        return resp;
    }
}

package com.wang.wiki.user.vo;

import com.wang.wiki.req.PageReq;
import lombok.Data;
import lombok.ToString;

/**
 * 用户
 * @author Wang
 */
@Data
@ToString
public class UserVO extends PageReq {
    private Long id;

    /**
     * 登陆名
     */
    private String loginName;

    /**
     * 昵称
     */
    private String name;

    /**
     * 密码
     */
    private String password;

    /**
     * 令牌
     */
    private String token;
}
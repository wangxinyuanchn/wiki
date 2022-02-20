package com.wang.wiki.user.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.ToString;

/**
 * 用户
 *
 * @author Wang
 */
@Data
@ToString
@TableName("wiki_user")
public class UserEntity {
    @TableId("c_oid")
    private Long id;

    /**
     * 登陆名
     */
    @TableField("c_login_name")
    private String loginName;

    /**
     * 昵称
     */
    @TableField("c_name")
    private String name;

    /**
     * 密码
     */
    @TableField("c_password")
    private String password;
}
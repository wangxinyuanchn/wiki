package com.wang.wiki.test.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.ToString;

/**
 * @author Wang
 */
@Data
@ToString
@TableName("sys_test")
public class Test {
    @TableId("c_oid")
    private Integer id;

    @TableField("c_name")
    private String name;

    @TableField("c_password")
    private String password;
}

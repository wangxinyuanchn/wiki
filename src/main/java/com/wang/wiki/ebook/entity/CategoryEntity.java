package com.wang.wiki.ebook.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.ToString;

/**
 * 分类
 *
 * @author Wang
 */
@Data
@ToString
@TableName("wiki_category")
public class CategoryEntity {
    @TableId("c_oid")
    private Long id;

    /**
     * 父类
     */
    @TableField("c_parent")
    private Long parent;

    /**
     * 名称
     */
    @TableField("c_name")
    private String name;

    /**
     * 顺序
     */
    @TableField("c_sort")
    private Integer sort;
}
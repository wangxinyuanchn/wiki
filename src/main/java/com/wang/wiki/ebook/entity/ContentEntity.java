package com.wang.wiki.ebook.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.ToString;

/**
 * 文档内容
 *
 * @author Wang
 */
@Data
@ToString
@TableName("wiki_content")
public class ContentEntity {
    @TableId("c_oid")
    private Long id;

    /**
     * 文档内容
     */
    @TableField("c_content")
    private String content;
}
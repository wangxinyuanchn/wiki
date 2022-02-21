package com.wang.wiki.ebook.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.ToString;

/**
 * 电子书文档
 *
 * @author Wang
 */
@Data
@ToString
@TableName("wiki_doc")
public class DocEntity {
    @TableId("c_oid")
    private Long id;

    /**
     * 电子书id
     */
    @TableField("c_ebook_id")
    private Long ebookId;

    /**
     * 父id
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

    /**
     * 阅读数
     */
    @TableField("c_view_count")
    private Integer viewCount;

    /**
     * 点赞数
     */
    @TableField("c_vote_count")
    private Integer voteCount;

}
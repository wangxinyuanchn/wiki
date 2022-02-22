package com.wang.wiki.ebook.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.ToString;

/**
 * @author Wang
 * @Overview 电子书
 */
@Data
@ToString
@TableName("wiki_ebook")
public class EbookEntity {
    @TableId("c_oid")
    private Long id;

    /**
     * 名称
     */
    @TableField("c_name")
    private String name;

    /**
     * 分类1
     */
    @TableField("c_category1_id")
    private String categoryId1;

    /**
     * 分类2
     */
    @TableField("c_category2_id")
    private String categoryId2;

    /**
     * 描述
     */
    @TableField("c_description")
    private String description;

    /**
     * 封面
     */
    @TableField("c_cover")
    private Long cover;

    /**
     * 文档数
     */
    @TableField("c_doc_count")
    private Integer docCount;

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

    /**
     * 备注
     */
    @TableField("c_remark")
    private String remark;
}

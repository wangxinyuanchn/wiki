package com.wang.wiki.ebook.vo;

import com.wang.wiki.req.PageReq;
import lombok.Data;
import lombok.ToString;

/**
 * @author Wang
 * @Overview 电子书
 */
@Data
@ToString
public class EbookVO extends PageReq {
    private Integer id;

    /**
     * 名称
     */
    private String name;

    /**
     * 分类1
     */
    private String categoryId1;

    /**
     * 分类2
     */
    private String categoryId2;

    /**
     * 描述
     */
    private String description;

    /**
     * 封面
     */
    private String cover;

    /**
     * 文档数
     */
    private String docCount;

    /**
     * 阅读数
     */
    private String viewCount;

    /**
     * 点赞数
     */
    private String voteCount;
}

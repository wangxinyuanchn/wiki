package com.wang.wiki.ebook.vo;

import com.wang.wiki.req.PageReq;
import lombok.Data;
import lombok.ToString;

/**
 * 电子书
 *
 * @author Wang
 */
@Data
@ToString
public class EbookVO extends PageReq {
    private Long id;

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
    private Integer docCount;

    /**
     * 阅读数
     */
    private Integer viewCount;

    /**
     * 点赞数
     */
    private Integer voteCount;
}

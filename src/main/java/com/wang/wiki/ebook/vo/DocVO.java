package com.wang.wiki.ebook.vo;

import com.wang.wiki.req.PageReq;
import lombok.Data;
import lombok.ToString;

/**
 * 电子书文档
 *
 * @author Wang
 */
@Data
@ToString
public class DocVO extends PageReq {
    private Long id;

    /**
     * 电子书id
     */
    private Long ebookId;

    /**
     * 父id
     */
    private Long parent;

    /**
     * 名称
     */
    private String name;

    /**
     * 顺序
     */
    private Integer sort;

    /**
     * 阅读数
     */
    private Integer viewCount;

    /**
     * 点赞数
     */
    private Integer voteCount;

    /**
     * 内容
     */
    private String content;

}
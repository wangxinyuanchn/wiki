package com.wang.wiki.ebook.vo;

import com.wang.wiki.req.PageReq;
import lombok.Data;
import lombok.ToString;

/**
 * 分类
 *
 * @author Wang
 */
@Data
@ToString
public class CategoryVO extends PageReq {
    private Long id;

    /**
     * 父类
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
}
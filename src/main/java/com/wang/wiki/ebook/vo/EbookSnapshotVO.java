package com.wang.wiki.ebook.vo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.wang.wiki.req.PageReq;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * 电子书快照表
 *
 * @author Wang
 */
@Data
@ToString
public class EbookSnapshotVO extends PageReq {
    @TableId("c_oid")
    private Long id;

    /**
     * 电子书id
     */
    private Long ebookId;

    /**
     * 快照日期
     */
    private Date date;

    /**
     * 阅读数
     */
    private Integer viewCount;

    /**
     * 点赞数
     */
    private Integer voteCount;

    /**
     * 阅读增长
     */
    private Integer viewIncrease;

    /**
     * 点赞增长
     */
    private Integer voteIncrease;

}
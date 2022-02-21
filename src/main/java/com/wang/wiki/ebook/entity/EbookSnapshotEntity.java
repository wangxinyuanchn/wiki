package com.wang.wiki.ebook.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
@TableName("wiki_ebook_snapshot")
public class EbookSnapshotEntity {
    @TableId("c_oid")
    private Long id;

    /**
     * 电子书id
     */
    @TableField("c_ebook_id")
    private Long ebookId;

    /**
     * 快照日期
     */
    @TableField("c_date")
    private Date date;

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
     * 阅读增长
     */
    @TableField("c_view_increase")
    private Integer viewIncrease;

    /**
     * 点赞增长
     */
    @TableField("c_vote_increase")
    private Integer voteIncrease;

}
package com.wang.wiki.blob.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.ToString;

import java.sql.Blob;

/**
 * 附件表
 *
 * @author Wang
 */
@Data
@ToString
@TableName("wiki_blob")
public class BlobEntity {
    @TableId("c_oid")
    private Long id;

    /**
     * 附件名
     */
    @TableField("c_file_name")
    private String fileName;

    /**
     * 附件内容
     */
    @TableField("c_blob")
    private Blob file;

    /**
     * 附件大小
     */
    @TableField("c_size")
    private Long size;
}
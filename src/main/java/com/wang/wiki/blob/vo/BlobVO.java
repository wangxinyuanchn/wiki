package com.wang.wiki.blob.vo;

import lombok.Data;
import lombok.ToString;

/**
 * 附件表
 *
 * @author Wang
 */
@Data
@ToString
public class BlobVO {
    private Long id;

    /**
     * 附件名
     */
    private String fileName;

    /**
     * 附件内容
     */
    private byte[] fileByte;

    /**
     * 附件大小
     */
    private Long size;
}
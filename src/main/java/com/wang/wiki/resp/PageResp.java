package com.wang.wiki.resp;

import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * @author Wang
 */
@Data
@ToString
public class PageResp<T> {
    private long total;

    private List<T> list;
}

package com.wang.wiki.job.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wang.wiki.ebook.entity.DocEntity;
import com.wang.wiki.ebook.entity.EbookEntity;
import com.wang.wiki.ebook.mapper.DocMapper;
import com.wang.wiki.ebook.mapper.EbookMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 更新电子书信息
 *
 * @author Wang
 */
@Component
public class DocJobService {

    @Resource
    private DocMapper docMapper;

    @Resource
    private EbookMapper ebookMapper;

    /**
     * 更新电子书信息
     */
    public void updateEbookInfo() {
        QueryWrapper<DocEntity> wrapper = new QueryWrapper<>();
        wrapper.select("c_ebook_id c_oid, count(1) c_doc_count, sum(c_view_count) c_view_count, sum(c_vote_count) c_vote_count");
        wrapper.groupBy("c_ebook_id");
        List<Map<String, Object>> ebookList = docMapper.selectMaps(wrapper);
        for (Map<String, Object> map : ebookList) {
            EbookEntity ebookEntity = new EbookEntity();
            ebookEntity.setId(Long.parseLong(String.valueOf(map.get("c_oid"))));
            ebookEntity.setDocCount(Integer.parseInt(String.valueOf(map.get("c_doc_count"))));
            ebookEntity.setViewCount(Integer.parseInt(String.valueOf(map.get("c_view_count"))));
            ebookEntity.setVoteCount(Integer.parseInt(String.valueOf(map.get("c_vote_count"))));
            ebookMapper.updateById(ebookEntity);
        }
    }
}

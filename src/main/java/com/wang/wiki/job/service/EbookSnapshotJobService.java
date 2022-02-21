package com.wang.wiki.job.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wang.wiki.ebook.entity.EbookEntity;
import com.wang.wiki.ebook.entity.EbookSnapshotEntity;
import com.wang.wiki.ebook.mapper.EbookMapper;
import com.wang.wiki.ebook.mapper.EbookSnapshotMapper;
import com.wang.wiki.util.DateUtil;
import com.wang.wiki.util.SnowFlake;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 定时任务
 * 生成今日电子书快照
 *
 * @author Wang
 */
@Component
public class EbookSnapshotJobService {

    @Resource
    private EbookSnapshotMapper ebookSnapshotMapper;

    @Resource
    private EbookMapper ebookMapper;

    @Resource
    private SnowFlake snowFlake;

    public void genSnapshot() {
        Date today = new Date();
        Date yesterday = DateUtil.getDate(today, -1);

        QueryWrapper<EbookSnapshotEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("c_date", DateUtil.YMDSDF.format(today));
        ebookSnapshotMapper.delete(wrapper);

        List<EbookEntity> ebookEntityList = ebookMapper.selectList(null);
        for (EbookEntity ebookEntity : ebookEntityList) {
            EbookSnapshotEntity ebookSnapshotEntity = new EbookSnapshotEntity();
            ebookSnapshotEntity.setId(snowFlake.nextId());
            ebookSnapshotEntity.setEbookId(ebookEntity.getId());
            ebookSnapshotEntity.setDate(today);
            ebookSnapshotEntity.setViewCount(ebookEntity.getViewCount());
            ebookSnapshotEntity.setVoteCount(ebookEntity.getVoteCount());
            ebookSnapshotEntity.setViewIncrease(ebookEntity.getViewCount());
            ebookSnapshotEntity.setVoteIncrease(ebookEntity.getVoteCount());

            QueryWrapper<EbookSnapshotEntity> lastWrapper = new QueryWrapper<>();
            lastWrapper.eq("c_date", DateUtil.YMDSDF.format(yesterday));
            List<EbookSnapshotEntity> ebookSnapshotEntityList = ebookSnapshotMapper.selectList(lastWrapper);
            if (ebookSnapshotEntityList.size() > 0) {
                EbookSnapshotEntity old = ebookSnapshotEntityList.get(0);
                ebookSnapshotEntity.setViewIncrease(ebookEntity.getViewCount() - old.getViewIncrease());
                ebookSnapshotEntity.setVoteIncrease(ebookEntity.getVoteCount() - old.getVoteIncrease());
            }
            ebookSnapshotMapper.insert(ebookSnapshotEntity);
        }
    }

}

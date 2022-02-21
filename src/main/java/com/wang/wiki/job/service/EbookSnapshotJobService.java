package com.wang.wiki.job.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wang.wiki.ebook.entity.EbookEntity;
import com.wang.wiki.ebook.entity.EbookSnapshotEntity;
import com.wang.wiki.ebook.mapper.EbookMapper;
import com.wang.wiki.ebook.mapper.EbookSnapshotMapper;
import com.wang.wiki.ebook.vo.EbookSnapshotVO;
import com.wang.wiki.util.CopyUtil;
import com.wang.wiki.util.SnowFlake;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public void genSnapshot() {
        Date today = new Date();
        Date yesterday = getDate(today, -1);

        QueryWrapper<EbookSnapshotEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("c_date", sdf.format(today));
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
            lastWrapper.eq("c_date", sdf.format(yesterday));
            List<EbookSnapshotEntity> ebookSnapshotEntityList = ebookSnapshotMapper.selectList(lastWrapper);
            if (ebookSnapshotEntityList.size() > 0) {
                EbookSnapshotEntity old = ebookSnapshotEntityList.get(0);
                ebookSnapshotEntity.setViewIncrease(ebookEntity.getViewCount() - old.getViewIncrease());
                ebookSnapshotEntity.setVoteIncrease(ebookEntity.getVoteCount() - old.getVoteIncrease());
            }
            ebookSnapshotMapper.insert(ebookSnapshotEntity);
        }
    }

    /**
     * 30天数值统计
     */
    public List<EbookSnapshotVO> get30Statistic() {
        QueryWrapper<EbookSnapshotEntity> wrapper = new QueryWrapper<>();
        wrapper.select("c_date, sum(c_view_increase) c_view_increase, sum(c_vote_increase) c_vote_increase");
        wrapper.between("c_date", getDate(new Date(), -30), getDate(new Date(), -1));
        wrapper.groupBy("c_date");
        wrapper.orderByAsc("c_date");
        List<EbookSnapshotEntity> ebookSnapshotEntityList = ebookSnapshotMapper.selectList(wrapper);

        return CopyUtil.copyList(ebookSnapshotEntityList, EbookSnapshotVO.class);
    }

    private Date getDate(Date today, int index) {
        Calendar c = Calendar.getInstance();
        c.setTime(today);
        c.add(Calendar.DAY_OF_MONTH, index);
        return c.getTime();
    }

}

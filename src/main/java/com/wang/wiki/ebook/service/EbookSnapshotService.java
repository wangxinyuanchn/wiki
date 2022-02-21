package com.wang.wiki.ebook.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wang.wiki.ebook.entity.EbookSnapshotEntity;
import com.wang.wiki.ebook.mapper.EbookSnapshotMapper;
import com.wang.wiki.ebook.vo.EbookSnapshotVO;
import com.wang.wiki.util.CopyUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 电子书快照表
 *
 * @author Wang
 */
@Service
public class EbookSnapshotService {

    @Resource
    private EbookSnapshotMapper ebookSnapshotMapper;

    /**
     * 获取首页数值数据：总阅读数、总点赞数、今日阅读数、今日点赞数、今日预计阅读数、今日预计阅读增长
     */
    public List<EbookSnapshotVO> getStatistic() {
        Date today = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(today);
        c.add(Calendar.DAY_OF_MONTH, -1);

        QueryWrapper<EbookSnapshotEntity> wrapper = new QueryWrapper<>();
        wrapper.select("c_date, sum(c_view_count) c_view_count, sum(c_vote_count) c_vote_count, sum(c_view_increase) c_view_increase, sum(c_vote_increase) c_vote_increase");
        wrapper.apply("c_date>={0}", c.getTime());
        wrapper.groupBy("c_date");
        wrapper.orderByAsc("c_date");
        List<EbookSnapshotEntity> ebookSnapshotEntityList = ebookSnapshotMapper.selectList(wrapper);

        return CopyUtil.copyList(ebookSnapshotEntityList, EbookSnapshotVO.class);
    }

}

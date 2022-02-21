package com.wang.wiki.ebook.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wang.wiki.ebook.entity.EbookSnapshotEntity;
import com.wang.wiki.ebook.mapper.EbookSnapshotMapper;
import com.wang.wiki.ebook.vo.EbookSnapshotVO;
import com.wang.wiki.util.DateUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
        QueryWrapper<EbookSnapshotEntity> wrapper = new QueryWrapper<>();
        wrapper.select("c_date date, sum(c_view_count) viewCount, sum(c_vote_count) voteCount, sum(c_view_increase) viewIncrease, sum(c_vote_increase) voteIncrease");
        wrapper.apply("c_date>={0}", DateUtil.getDateStr(new Date(), -1));
        wrapper.groupBy("c_date");
        wrapper.orderByAsc("c_date");
        List<Map<String, Object>> ebookSnapshotEntityMap = ebookSnapshotMapper.selectMaps(wrapper);
        List<EbookSnapshotVO> list = new ArrayList<>();
        for (Map<String, Object> map : ebookSnapshotEntityMap) {
            EbookSnapshotVO ebookSnapshotVO = new EbookSnapshotVO();
            ebookSnapshotVO.setDate(DateUtil.parse(String.valueOf(map.get("date"))));
            ebookSnapshotVO.setViewCount(Integer.parseInt(String.valueOf(map.get("viewCount"))));
            ebookSnapshotVO.setVoteCount(Integer.parseInt(String.valueOf(map.get("voteCount"))));
            ebookSnapshotVO.setViewIncrease(Integer.parseInt(String.valueOf(map.get("viewIncrease"))));
            ebookSnapshotVO.setVoteIncrease(Integer.parseInt(String.valueOf(map.get("voteIncrease"))));
            list.add(ebookSnapshotVO);
        }
        return list;
    }

    /**
     * 30天数值统计
     */
    public List<EbookSnapshotVO> get30Statistic() {
        QueryWrapper<EbookSnapshotEntity> wrapper = new QueryWrapper<>();
        wrapper.select("c_date date, sum(c_view_increase) viewIncrease, sum(c_vote_increase) voteIncrease");
        wrapper.between("c_date", DateUtil.getDateStr(new Date(), -30), DateUtil.getDateStr(new Date(), -1));
        wrapper.groupBy("c_date");
        wrapper.orderByAsc("c_date");
        List<Map<String, Object>> ebookSnapshotEntityMap = ebookSnapshotMapper.selectMaps(wrapper);
        List<EbookSnapshotVO> list = new ArrayList<>();
        for (Map<String, Object> map : ebookSnapshotEntityMap) {
            EbookSnapshotVO ebookSnapshotVO = new EbookSnapshotVO();
            ebookSnapshotVO.setDate(DateUtil.parse(String.valueOf(map.get("date"))));
            ebookSnapshotVO.setViewIncrease(Integer.parseInt(String.valueOf(map.get("viewIncrease"))));
            ebookSnapshotVO.setVoteIncrease(Integer.parseInt(String.valueOf(map.get("voteIncrease"))));
            list.add(ebookSnapshotVO);
        }
        return list;
    }

}

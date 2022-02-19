package com.wang.wiki.ebook.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wang.wiki.ebook.entity.EbookEntity;
import com.wang.wiki.ebook.mapper.EbookMapper;
import com.wang.wiki.ebook.vo.EbookVO;
import com.wang.wiki.util.CopyUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Wang
 */
@Service
public class EbookService {

    @Resource
    private EbookMapper ebookMapper;

    /**
     * 电子书列表查询
     * @param ebook 查询条件
     * @return 查询结果
     */
    public List<EbookVO> list(EbookVO ebook){
        QueryWrapper<EbookEntity> wrapper = new QueryWrapper<>();
        wrapper.like("c_name", ebook.getName());
        List<EbookEntity> list = ebookMapper.selectList(wrapper);
        return CopyUtil.copyList(list, EbookVO.class);
    }
}

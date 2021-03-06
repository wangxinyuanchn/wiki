package com.wang.wiki.ebook.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wang.wiki.blob.service.BlobService;
import com.wang.wiki.ebook.entity.EbookEntity;
import com.wang.wiki.ebook.mapper.EbookMapper;
import com.wang.wiki.ebook.vo.EbookVO;
import com.wang.wiki.resp.PageResp;
import com.wang.wiki.util.CopyUtil;
import com.wang.wiki.util.SnowFlake;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Wang
 */
@Service
public class EbookService {

    private static final Logger LOG = LoggerFactory.getLogger(CategoryService.class);

    @Resource
    private EbookMapper ebookMapper;

    @Resource
    private BlobService blobService;

    @Resource
    private SnowFlake snowFlake;

    /**
     * 查询全部电子书列表
     *
     * @return 查询结果
     */
    public List<EbookVO> all() throws SQLException {
        List<EbookEntity> categoryList = ebookMapper.selectList(null);

        return copyList(categoryList);
    }

    /**
     * 分页查询电子书列表
     *
     * @param req 查询条件
     * @return 查询结果
     */
    public PageResp<EbookVO> list(EbookVO req) throws SQLException {
        QueryWrapper<EbookEntity> wrapper = new QueryWrapper<>();
        if (!ObjectUtils.isEmpty(req.getName())) {
            wrapper.like("c_name", req.getName());
        }
        if (!ObjectUtils.isEmpty(req.getCategoryId2())) {
            wrapper.eq("c_category2_id", req.getCategoryId2());
        }
        Page<EbookEntity> page = new Page<>(req.getPage(), req.getSize());
        IPage<EbookEntity> ebookEntityPage = ebookMapper.selectPage(page, wrapper);

        LOG.info("总行数：{}", ebookEntityPage.getTotal());
        LOG.info("总页数：{}", ebookEntityPage.getPages());

        // 列表复制
        List<EbookVO> list = copyList(ebookEntityPage.getRecords());

        PageResp<EbookVO> pageResp = new PageResp<>();
        pageResp.setTotal(ebookEntityPage.getTotal());
        pageResp.setList(list);

        return pageResp;
    }

    /**
     * 新增/修改电子书
     *
     * @param req 新增/修改电子书内容
     * @return 返回结果
     */
    public int save(EbookVO req) {
        EbookEntity category = CopyUtil.copy(req, EbookEntity.class);
        if (ObjectUtils.isEmpty(req.getId())) {
            // 新增
            category.setId(snowFlake.nextId());
            return ebookMapper.insert(category);
        } else {
            // 更新
            return ebookMapper.updateById(category);
        }
    }

    /**
     * 删除电子书
     *
     * @param id 电子书Id
     * @return 返回结果
     */
    public int delete(Long id) {
        return ebookMapper.deleteById(id);
    }

    /**
     * 列表复制
     *
     * @param source 原对象list
     * @return 目标对象list
     */
    public List<EbookVO> copyList(List<EbookEntity> source) throws SQLException {
        List<EbookVO> target = new ArrayList<>();
        if (!CollectionUtils.isEmpty(source)) {
            for (EbookEntity ebookEntity : source) {
                EbookVO ebookVO = CopyUtil.copy(ebookEntity, EbookVO.class);
                ebookVO.setBlobVO(blobService.search(ebookVO.getCover()));
                target.add(ebookVO);
            }
        }
        return target;
    }
}

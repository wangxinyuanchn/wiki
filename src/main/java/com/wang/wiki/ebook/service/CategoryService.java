package com.wang.wiki.ebook.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wang.wiki.ebook.entity.CategoryEntity;
import com.wang.wiki.ebook.mapper.CategoryMapper;
import com.wang.wiki.ebook.vo.CategoryVO;
import com.wang.wiki.resp.PageResp;
import com.wang.wiki.util.CopyUtil;
import com.wang.wiki.util.SnowFlake;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * 分类
 *
 * @author Wang
 */
@Service
public class CategoryService {

    private static final Logger LOG = LoggerFactory.getLogger(CategoryService.class);

    @Resource
    private CategoryMapper categoryMapper;

    @Resource
    private SnowFlake snowFlake;

    /**
     * 查询全部分类列表
     *
     * @return 查询结果
     */
    public List<CategoryVO> all() {
        QueryWrapper<CategoryEntity> wrapper = new QueryWrapper<>();
        wrapper.orderByAsc("c_sort");
        List<CategoryEntity> categoryList = categoryMapper.selectList(wrapper);

        return CopyUtil.copyList(categoryList, CategoryVO.class);
    }

    /**
     * 分页查询分类列表
     *
     * @param req 查询条件
     * @return 查询结果
     */
    public PageResp<CategoryVO> list(CategoryVO req) {
        QueryWrapper<CategoryEntity> wrapper = new QueryWrapper<>();
        wrapper.orderByAsc("c_sort");
        Page<CategoryEntity> page = new Page<>(req.getPage(), req.getSize());
        IPage<CategoryEntity> categoryEntityIPage = categoryMapper.selectPage(page, wrapper);

        LOG.info("总行数：{}", categoryEntityIPage.getTotal());
        LOG.info("总页数：{}", categoryEntityIPage.getPages());

        // 列表复制
        List<CategoryVO> list = CopyUtil.copyList(categoryEntityIPage.getRecords(), CategoryVO.class);

        PageResp<CategoryVO> pageResp = new PageResp<>();
        pageResp.setTotal(categoryEntityIPage.getTotal());
        pageResp.setList(list);

        return pageResp;
    }

    /**
     * 新增/修改分类
     *
     * @param req 新增/修改分类内容
     * @return 返回结果
     */
    public int save(CategoryVO req) {
        CategoryEntity category = CopyUtil.copy(req, CategoryEntity.class);
        if (ObjectUtils.isEmpty(req.getId())) {
            // 新增
            category.setId(snowFlake.nextId());
            return categoryMapper.insert(category);
        } else {
            // 更新
            return categoryMapper.updateById(category);
        }
    }

    /**
     * 删除分类
     *
     * @param id 分类Id
     * @return 返回结果
     */
    public int delete(Long id) {
        return categoryMapper.deleteById(id);
    }
}

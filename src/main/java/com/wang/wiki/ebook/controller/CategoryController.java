package com.wang.wiki.ebook.controller;

import com.wang.wiki.ebook.service.CategoryService;
import com.wang.wiki.ebook.vo.CategoryVO;
import com.wang.wiki.resp.CommonResp;
import com.wang.wiki.resp.PageResp;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * 分类
 *
 * @author Wang
 */
@RestController
@RequestMapping("/category")
public class CategoryController {

    @Resource
    private CategoryService categoryService;

    /**
     * 查询全部分类列表
     *
     * @return 查询结果
     */
    @GetMapping("/all")
    public CommonResp<List<CategoryVO>> all() {
        CommonResp<List<CategoryVO>> resp = new CommonResp<>();
        List<CategoryVO> list = categoryService.all();
        resp.setContent(list);
        return resp;
    }

    /**
     * 分页查询分类列表
     *
     * @param req 查询条件
     * @return 查询结果
     */
    @GetMapping("/list")
    public CommonResp<PageResp<CategoryVO>> list(@Valid CategoryVO req) {
        CommonResp<PageResp<CategoryVO>> resp = new CommonResp<>();
        PageResp<CategoryVO> list = categoryService.list(req);
        resp.setContent(list);
        return resp;
    }

    /**
     * 新增/修改分类
     *
     * @param req 新增/修改分类内容
     * @return 返回结果
     */
    @PostMapping("/save")
    public CommonResp<Integer> save(@Valid @RequestBody CategoryVO req) {
        CommonResp<Integer> resp = new CommonResp<>();
        int index = categoryService.save(req);
        resp.setContent(index);
        return resp;
    }

    /**
     * 删除分类
     *
     * @param id 分类Id
     * @return 返回结果
     */
    @DeleteMapping("/delete/{id}")
    public CommonResp<Integer> delete(@PathVariable Long id) {
        CommonResp<Integer> resp = new CommonResp<>();
        int index = categoryService.delete(id);
        resp.setContent(index);
        return resp;
    }
}

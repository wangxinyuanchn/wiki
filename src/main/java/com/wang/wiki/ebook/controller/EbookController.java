package com.wang.wiki.ebook.controller;

import com.wang.wiki.ebook.service.EbookService;
import com.wang.wiki.ebook.vo.EbookVO;
import com.wang.wiki.resp.CommonResp;
import com.wang.wiki.resp.PageResp;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * 电子书
 *
 * @author Wang
 */
@RestController
@RequestMapping("/ebook")
public class EbookController {

    @Resource
    private EbookService ebookService;

    /**
     * 查询全部电子书列表
     *
     * @return 查询结果
     */
    @GetMapping("/all")
    public CommonResp<List<EbookVO>> all() {
        CommonResp<List<EbookVO>> resp = new CommonResp<>();
        List<EbookVO> list = ebookService.all();
        resp.setContent(list);
        return resp;
    }

    /**
     * 分页查询电子书列表
     *
     * @param req 查询条件
     * @return 查询结果
     */
    @GetMapping("/list")
    public CommonResp<PageResp<EbookVO>> list(@Valid EbookVO req) {
        CommonResp<PageResp<EbookVO>> resp = new CommonResp<>();
        PageResp<EbookVO> list = ebookService.list(req);
        resp.setContent(list);
        return resp;
    }

    /**
     * 新增/修改电子书
     *
     * @param req 新增/修改电子书内容
     * @return 返回结果
     */
    @PostMapping("/save")
    public CommonResp<Integer> save(@Valid @RequestBody EbookVO req) {
        CommonResp<Integer> resp = new CommonResp<>();
        int index = ebookService.save(req);
        resp.setContent(index);
        return resp;
    }

    /**
     * 删除电子书
     *
     * @param id 电子书Id
     * @return 返回结果
     */
    @DeleteMapping("/delete/{id}")
    public CommonResp<Integer> delete(@PathVariable Long id) {
        CommonResp<Integer> resp = new CommonResp<>();
        int index = ebookService.delete(id);
        resp.setContent(index);
        return resp;
    }
}

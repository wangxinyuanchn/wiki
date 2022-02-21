package com.wang.wiki.ebook.controller;

import com.wang.wiki.ebook.service.DocService;
import com.wang.wiki.ebook.vo.DocVO;
import com.wang.wiki.resp.CommonResp;
import com.wang.wiki.resp.PageResp;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;

/**
 * 电子书文案
 *
 * @author Wang
 */
@RestController
@RequestMapping("/doc")
public class DocController {

    @Resource
    private DocService docService;

    /**
     * 按电子书Id查询电子书文案列表
     *
     * @param ebookId 电子书Id
     * @return 查询结果
     */
    @GetMapping("/all/{ebookId}")
    public CommonResp<List<DocVO>> all(@PathVariable Long ebookId) {
        CommonResp<List<DocVO>> resp = new CommonResp<>();
        List<DocVO> list = docService.all(ebookId);
        resp.setContent(list);
        return resp;
    }

    /**
     * 分页查询电子书文案列表
     *
     * @param req 查询条件
     * @return 查询结果
     */
    @GetMapping("/list")
    public CommonResp<PageResp<DocVO>> list(@Valid DocVO req) {
        CommonResp<PageResp<DocVO>> resp = new CommonResp<>();
        PageResp<DocVO> list = docService.list(req);
        resp.setContent(list);
        return resp;
    }

    /**
     * 新增/修改电子书文案
     *
     * @param req 新增/修改电子书文案内容
     * @return 返回结果
     */
    @PostMapping("/save")
    public CommonResp<Boolean> save(@Valid @RequestBody DocVO req) {
        CommonResp<Boolean> resp = new CommonResp<>();
        boolean bo = docService.save(req);
        resp.setContent(Boolean.TRUE.equals(bo));
        return resp;
    }

    /**
     * 批量删除电子书文案
     *
     * @param idsStr 电子书文案Id，用","分割
     * @return 返回结果
     */
    @DeleteMapping("/delete/{idsStr}")
    public CommonResp<Integer> delete(@PathVariable String idsStr) {
        CommonResp<Integer> resp = new CommonResp<>();
        List<String> list = Arrays.asList(idsStr.split(","));
        int index = docService.delete(list);
        resp.setContent(index);
        return resp;
    }

    /**
     * 电子书文案增加阅读数
     *
     * @param id 电子书文案Id
     * @return 返回结果
     */
    @GetMapping("/find-content/{id}")
    public CommonResp<String> findContent(@PathVariable Long id) {
        CommonResp<String> resp = new CommonResp<>();
        String content = docService.findContent(id);
        resp.setContent(content);
        return resp;
    }

    /**
     * 电子书文案增加点赞数
     * 远程IP+doc.id作为key，24小时内不能重复
     *
     * @param id 电子书文案Id
     * @return 返回结果
     */
    @GetMapping("/vote/{id}")
    public CommonResp<Boolean> vote(@PathVariable Long id) {
        CommonResp<Boolean> commonResp = new CommonResp<>();
        boolean bo = docService.vote(id);
        commonResp.setContent(bo);
        return commonResp;
    }
}

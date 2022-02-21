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
 * @author Wang
 */
@RestController
@RequestMapping("/doc")
public class DocController {

    @Resource
    private DocService docService;

    @GetMapping("/all/{ebookId}")
    public CommonResp all(@PathVariable Long ebookId) {
        CommonResp<List<DocVO>> resp = new CommonResp<>();
        List<DocVO> list = docService.all(ebookId);
        resp.setContent(list);
        return resp;
    }

    @GetMapping("/list")
    public CommonResp list(@Valid DocVO req) {
        CommonResp<PageResp<DocVO>> resp = new CommonResp<>();
        PageResp<DocVO> list = docService.list(req);
        resp.setContent(list);
        return resp;
    }

    @PostMapping("/save")
    public CommonResp save(@Valid @RequestBody DocVO req) {
        CommonResp resp = new CommonResp<>();
        docService.save(req);
        return resp;
    }

    @DeleteMapping("/delete/{idsStr}")
    public CommonResp delete(@PathVariable String idsStr) {
        CommonResp resp = new CommonResp<>();
        List<String> list = Arrays.asList(idsStr.split(","));
        docService.delete(list);
        return resp;
    }

    @GetMapping("/find-content/{id}")
    public CommonResp findContent(@PathVariable Long id) {
        CommonResp<String> resp = new CommonResp<>();
        String content = docService.findContent(id);
        resp.setContent(content);
        return resp;
    }

    @GetMapping("/vote/{id}")
    public CommonResp vote(@PathVariable Long id) {
        CommonResp commonResp = new CommonResp();
        docService.vote(id);
        return commonResp;
    }
}

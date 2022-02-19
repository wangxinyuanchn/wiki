package com.wang.wiki.ebook.controller;

import com.wang.wiki.ebook.service.EbookService;
import com.wang.wiki.ebook.vo.EbookVO;
import com.wang.wiki.resp.CommonResp;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Wang
 * @Overview 电子书
 */
@RestController
@RequestMapping("/ebook")
public class EbookController {

    @Resource
    private EbookService ebookService;

    /**
     * 电子书列表查询
     * @param ebook 查询条件
     * @return 查询结果
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public CommonResp<List<EbookVO>> list(EbookVO ebook) {
        CommonResp<List<EbookVO>> resp = new CommonResp<>();
        List<EbookVO> list = ebookService.list(ebook);
        resp.setContent(list);
        return resp;
    }
}

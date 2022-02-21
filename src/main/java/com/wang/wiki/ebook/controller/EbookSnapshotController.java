package com.wang.wiki.ebook.controller;

import com.wang.wiki.ebook.service.EbookSnapshotService;
import com.wang.wiki.ebook.vo.EbookSnapshotVO;
import com.wang.wiki.resp.CommonResp;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 首页显示信息
 *
 * @author Wang
 */
@RestController
@RequestMapping("/ebook-snapshot")
public class EbookSnapshotController {

    @Resource
    private EbookSnapshotService ebookSnapshotService;

    @GetMapping("/get-statistic")
    public CommonResp<List<EbookSnapshotVO>> getStatistic() {
        CommonResp<List<EbookSnapshotVO>> commonResp = new CommonResp<>();
        List<EbookSnapshotVO> statisticResp = ebookSnapshotService.getStatistic();
        commonResp.setContent(statisticResp);
        return commonResp;
    }

    @GetMapping("/get-30-statistic")
    public CommonResp<List<EbookSnapshotVO>> get30Statistic() {
        CommonResp<List<EbookSnapshotVO>> commonResp = new CommonResp<>();
        List<EbookSnapshotVO> statisticResp = ebookSnapshotService.get30Statistic();
        commonResp.setContent(statisticResp);
        return commonResp;
    }

}

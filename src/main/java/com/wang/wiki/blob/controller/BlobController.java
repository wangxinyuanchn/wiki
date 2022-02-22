package com.wang.wiki.blob.controller;

import com.wang.wiki.blob.service.BlobService;
import com.wang.wiki.blob.vo.BlobVO;
import com.wang.wiki.resp.CommonResp;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.io.IOException;
import java.sql.SQLException;

/**
 * 附件表
 *
 * @author Wang
 */
@RestController
@RequestMapping("/blob")
public class BlobController {

    @Resource
    private BlobService blobService;

    /**
     * 查询附件
     *
     * @param id 附件Id
     * @return 查询结果
     */
    @GetMapping("/search/{id}")
    public CommonResp<BlobVO> search(@PathVariable Long id) throws SQLException {
        CommonResp<BlobVO> resp = new CommonResp<>();
        BlobVO blobVO = blobService.search(id);
        resp.setContent(blobVO);
        return resp;
    }

    /**
     * 上传附件
     *
     * @param file 附件内容
     * @return 附件Id
     */
    @PostMapping("/save")
    public CommonResp<Long> save(@Valid @RequestBody MultipartFile file) throws SQLException, IOException {
        CommonResp<Long> resp = new CommonResp<>();
        long id = blobService.save(file);
        resp.setContent(id);
        return resp;
    }
}

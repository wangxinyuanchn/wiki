package com.wang.wiki.blob.service;

import com.wang.wiki.blob.entity.BlobEntity;
import com.wang.wiki.blob.mapper.BlobMapper;
import com.wang.wiki.blob.vo.BlobVO;
import com.wang.wiki.util.SnowFlake;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;

/**
 * 附件表
 *
 * @author Wang
 */
@Service
public class BlobService {

    @Resource
    private BlobMapper blobMapper;

    @Resource
    private SnowFlake snowFlake;

    /**
     * 查询附件
     *
     * @param id 附件Id
     * @return 查询结果
     */
    public BlobVO search(Long id) throws SQLException {
        BlobEntity blobEntity = blobMapper.selectById(id);
        BlobVO blobVO = new BlobVO();
        blobVO.setId(blobEntity.getId());
        blobVO.setFileName(blobEntity.getFileName());
        blobVO.setSize(blobEntity.getSize());
        Blob file = blobEntity.getFile();
        blobVO.setFileByte(file.getBytes(1, (int) file.length()));
        return blobVO;
    }

    /**
     * 上传附件
     *
     * @param file 附件内容
     * @return 附件Id
     */
    public long save(MultipartFile file) throws SQLException, IOException {
        Blob blob = new SerialBlob(file.getBytes());

        BlobEntity blobEntity = new BlobEntity();
        blobEntity.setId(snowFlake.nextId());
        blobEntity.setFileName(file.getOriginalFilename());
        blobEntity.setFile(blob);
        blobEntity.setSize(file.getSize());

        blobMapper.insert(blobEntity);

        return blobEntity.getId();
    }
}

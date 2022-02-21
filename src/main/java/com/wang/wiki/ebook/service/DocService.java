package com.wang.wiki.ebook.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wang.wiki.ebook.entity.ContentEntity;
import com.wang.wiki.ebook.entity.DocEntity;
import com.wang.wiki.ebook.mapper.ContentMapper;
import com.wang.wiki.ebook.mapper.DocMapper;
import com.wang.wiki.ebook.vo.DocVO;
import com.wang.wiki.exception.BusinessException;
import com.wang.wiki.exception.BusinessExceptionCode;
import com.wang.wiki.resp.PageResp;
import com.wang.wiki.util.CopyUtil;
import com.wang.wiki.util.RedisUtil;
import com.wang.wiki.util.RequestContext;
import com.wang.wiki.util.SnowFlake;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Wang
 */
@Service
public class DocService {

    private static final Logger LOG = LoggerFactory.getLogger(DocService.class);
    @Resource
    public RedisUtil redisUtil;
    @Resource
    public WsService wsService;
    @Resource
    private DocMapper docMapper;
    @Resource
    private ContentMapper contentMapper;
    @Resource
    private SnowFlake snowFlake;

    /**
     * 按电子书Id查询电子书文案列表
     *
     * @param ebookId 电子书Id
     * @return 查询结果
     */
    public List<DocVO> all(Long ebookId) {
        QueryWrapper<DocEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("c_ebook_id", ebookId);
        wrapper.orderByAsc("c_sort");
        List<DocEntity> docList = docMapper.selectList(wrapper);

        return CopyUtil.copyList(docList, DocVO.class);
    }

    /**
     * 分页查询电子书文案列表
     *
     * @param req 查询条件
     * @return 查询结果
     */
    public PageResp<DocVO> list(DocVO req) {
        QueryWrapper<DocEntity> wrapper = new QueryWrapper<>();
        wrapper.orderByAsc("c_sort");
        Page<DocEntity> page = new Page<>(req.getPage(), req.getSize());
        IPage<DocEntity> docEntityIPage = docMapper.selectPage(page, wrapper);

        LOG.info("总行数：{}", docEntityIPage.getTotal());
        LOG.info("总页数：{}", docEntityIPage.getPages());

        // 列表复制
        List<DocVO> list = CopyUtil.copyList(docEntityIPage.getRecords(), DocVO.class);

        PageResp<DocVO> pageResp = new PageResp();
        pageResp.setTotal(docEntityIPage.getTotal());
        pageResp.setList(list);

        return pageResp;
    }

    /**
     * 新增/修改电子书文案
     *
     * @param req 新增/修改电子书文案内容
     * @return 返回结果
     */
    @Transactional
    public void save(DocVO req) {
        DocEntity doc = CopyUtil.copy(req, DocEntity.class);
        ContentEntity content = CopyUtil.copy(req, ContentEntity.class);
        if (ObjectUtils.isEmpty(req.getId())) {
            // 新增
            doc.setId(snowFlake.nextId());
            doc.setViewCount(0);
            doc.setVoteCount(0);
            docMapper.insert(doc);

            content.setId(doc.getId());
            contentMapper.insert(content);
        } else {
            // 更新
            docMapper.updateById(doc);
            int count = contentMapper.updateById(content);
            if (count == 0) {
                contentMapper.insert(content);
            }
        }
    }

    /**
     * 批量删除电子书文案
     *
     * @param ids 电子书文案Id
     * @return 返回结果
     */
    public void delete(List<String> ids) {
        QueryWrapper<DocEntity> wrapper = new QueryWrapper<>();
        wrapper.in("c_oid", ids);
        docMapper.delete(wrapper);
    }

    /**
     * 电子书文案增加阅读数
     *
     * @param id 电子书文案Id
     * @return 返回结果
     */
    public String findContent(Long id) {
        ContentEntity content = contentMapper.selectById(id);
        // 文档阅读数+1
        DocEntity doc = docMapper.selectById(id);
        doc.setViewCount(doc.getViewCount() + 1);
        docMapper.updateById(doc);
        if (ObjectUtils.isEmpty(content)) {
            return "";
        } else {
            return content.getContent();
        }
    }

    /**
     * 电子书文案增加点赞数
     * 远程IP+doc.id作为key，24小时内不能重复
     *
     * @param id 电子书文案Id
     * @return 返回结果
     */
    public void vote(Long id) {
        // 远程IP+doc.id作为key，24小时内不能重复
        String ip = RequestContext.getRemoteAddress();
        if (redisUtil.validateRepeat("DOC_VOTE_" + id + "_" + ip, 5000)) {
            DocEntity doc = docMapper.selectById(id);
            doc.setVoteCount(doc.getVoteCount() + 1);
            docMapper.updateById(doc);
        } else {
            throw new BusinessException(BusinessExceptionCode.VOTE_REPEAT);
        }

        // 推送消息
        DocEntity docDb = docMapper.selectById(id);
        String logId = MDC.get("LOG_ID");
        wsService.sendInfo("【" + docDb.getName() + "】被点赞！", logId);
    }
}

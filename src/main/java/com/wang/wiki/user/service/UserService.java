package com.wang.wiki.user.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wang.wiki.exception.BusinessException;
import com.wang.wiki.exception.BusinessExceptionCode;
import com.wang.wiki.resp.PageResp;
import com.wang.wiki.user.entity.UserEntity;
import com.wang.wiki.user.mapper.UserMapper;
import com.wang.wiki.user.vo.UserVO;
import com.wang.wiki.util.CopyUtil;
import com.wang.wiki.util.SnowFlake;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Wang
 */
@Service
public class UserService {

    private static final Logger LOG = LoggerFactory.getLogger(UserService.class);

    @Resource
    private UserMapper userMapper;

    @Resource
    private SnowFlake snowFlake;

    /**
     * 分页查询用户列表
     *
     * @param req 查询条件
     * @return 查询结果
     */
    public PageResp<UserVO> list(UserVO req) {
        QueryWrapper<UserEntity> wrapper = new QueryWrapper<>();
        if (!ObjectUtils.isEmpty(req.getLoginName())) {
            wrapper.eq("c_login_name", req.getLoginName());
        }
        Page<UserEntity> page = new Page<>(req.getPage(), req.getSize());
        IPage<UserEntity> userEntityPage = userMapper.selectPage(page, wrapper);

        LOG.info("总行数：{}", userEntityPage.getTotal());
        LOG.info("总页数：{}", userEntityPage.getPages());

        // 列表复制
        List<UserVO> list = CopyUtil.copyList(userEntityPage.getRecords(), UserVO.class);

        PageResp<UserVO> pageResp = new PageResp<>();
        pageResp.setTotal(userEntityPage.getTotal());
        pageResp.setList(list);

        return pageResp;
    }

    /**
     * 新增/修改用户
     *
     * @param req 新增/修改用户内容
     * @return 返回结果
     */
    public int save(UserVO req) {
        UserEntity user = CopyUtil.copy(req, UserEntity.class);
        if (ObjectUtils.isEmpty(req.getId())) {
            UserEntity userDb = selectByLoginName(req.getLoginName());
            if (ObjectUtils.isEmpty(userDb)) {
                // 新增
                user.setId(snowFlake.nextId());
                return userMapper.insert(user);
            } else {
                // 用户名已存在
                throw new BusinessException(BusinessExceptionCode.USER_LOGIN_NAME_EXIST);
            }
        } else {
            // 更新
            user.setLoginName(null);
            user.setPassword(null);
            return userMapper.updateById(user);
        }
    }

    /**
     * 删除用户
     *
     * @param id 用户Id
     * @return 返回结果
     */
    public int delete(Long id) {
        return userMapper.deleteById(id);
    }

    /**
     * 根据登陆名查询用户
     *
     * @param loginName 登陆名
     * @return 查询结果
     */
    public UserEntity selectByLoginName(String loginName) {
        QueryWrapper<UserEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("c_login_name", loginName);

        List<UserEntity> userList = userMapper.selectList(wrapper);
        if (CollectionUtils.isEmpty(userList)) {
            return null;
        } else {
            return userList.get(0);
        }
    }

    /**
     * 修改密码
     *
     * @param req 修改用户内容
     * @return 返回结果
     */
    public int resetPassword(UserVO req) {
        UserEntity user = CopyUtil.copy(req, UserEntity.class);
        return userMapper.updateById(user);
    }

    /**
     * 登录
     *
     * @param req 登录用户内容
     * @return 返回结果
     */
    public UserVO login(UserVO req) {
        UserEntity userDb = selectByLoginName(req.getLoginName());
        if (ObjectUtils.isEmpty(userDb)) {
            // 用户名不存在
            LOG.info("用户名不存在, {}", req.getLoginName());
            throw new BusinessException(BusinessExceptionCode.LOGIN_USER_ERROR);
        } else {
            if (userDb.getPassword().equals(req.getPassword())) {
                // 登录成功
                return CopyUtil.copy(userDb, UserVO.class);
            } else {
                // 密码不对
                LOG.info("密码不对, 输入密码：{}, 数据库密码：{}", req.getPassword(), userDb.getPassword());
                throw new BusinessException(BusinessExceptionCode.LOGIN_USER_ERROR);
            }
        }
    }
}

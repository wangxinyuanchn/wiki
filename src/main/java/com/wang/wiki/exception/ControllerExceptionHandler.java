package com.wang.wiki.exception;

import com.wang.wiki.resp.CommonResp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 统一异常处理、数据预处理等
 *
 * @author Wang
 */
@ControllerAdvice
public class ControllerExceptionHandler {

    private static final Logger LOG = LoggerFactory.getLogger(ControllerExceptionHandler.class);

    /**
     * 校验异常统一处理
     *
     * @param e 异常
     * @return 返回异常
     */
    @ExceptionHandler(value = BindException.class)
    @ResponseBody
    public CommonResp<String> validExceptionHandler(BindException e) {
        CommonResp<String> commonResp = new CommonResp<>();
        LOG.warn("参数校验失败：{}", e.getBindingResult().getAllErrors().get(0).getDefaultMessage());
        commonResp.setSuccess(false);
        commonResp.setMessage(e.getBindingResult().getAllErrors().get(0).getDefaultMessage());
        return commonResp;
    }

    /**
     * 校验异常统一处理
     *
     * @param e 异常
     * @return 返回异常
     */
    @ExceptionHandler(value = BusinessException.class)
    @ResponseBody
    public CommonResp<String> validExceptionHandler(BusinessException e) {
        CommonResp<String> commonResp = new CommonResp<>();
        LOG.warn("业务异常：{}", e.getCode().getDesc());
        commonResp.setSuccess(false);
        commonResp.setMessage(e.getCode().getDesc());
        return commonResp;
    }

    /**
     * 校验异常统一处理
     *
     * @param e 异常
     * @return 返回异常
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public CommonResp<String> validExceptionHandler(Exception e) {
        CommonResp<String> commonResp = new CommonResp<>();
        LOG.error("系统异常：", e);
        commonResp.setSuccess(false);
        commonResp.setMessage("系统出现异常，请联系管理员");
        return commonResp;
    }
}

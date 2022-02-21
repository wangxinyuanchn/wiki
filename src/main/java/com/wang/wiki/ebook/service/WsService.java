package com.wang.wiki.ebook.service;

import com.wang.wiki.websocket.WebSocketServer;
import org.slf4j.MDC;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author Wang
 */
@Service
public class WsService {

    @Resource
    public WebSocketServer webSocketServer;

    @Async
    public boolean sendInfo(String message, String logId) {
        MDC.put("LOG_ID", logId);
        return webSocketServer.sendInfo(message);
    }
}

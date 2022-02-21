package com.wang.wiki.util;


import java.io.Serializable;

/**
 * 请求体Context
 *
 * @author Wang
 */
public class RequestContext implements Serializable {

    /**
     * 请求IP地址
     * */
    private static final ThreadLocal<String> REMOTE_ADDRESS = new ThreadLocal<>();

    public static String getRemoteAddress() {
        return REMOTE_ADDRESS.get();
    }

    public static void setRemoteAddress(String remoteAddress) {
        RequestContext.REMOTE_ADDRESS.set(remoteAddress);
    }

}

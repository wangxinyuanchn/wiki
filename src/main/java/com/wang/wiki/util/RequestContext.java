package com.wang.wiki.util;


import java.io.Serializable;

/**
 * @author Wang
 */
public class RequestContext implements Serializable {

    private static final ThreadLocal<String> REMOTE_ADDRESS = new ThreadLocal<>();

    public static String getRemoteAddress() {
        return REMOTE_ADDRESS.get();
    }

    public static void setRemoteAddress(String remoteAddress) {
        RequestContext.REMOTE_ADDRESS.set(remoteAddress);
    }

}

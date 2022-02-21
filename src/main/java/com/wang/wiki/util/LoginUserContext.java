package com.wang.wiki.util;

import com.wang.wiki.user.vo.UserVO;

import java.io.Serializable;

/**
 * @author Wang
 */
public class LoginUserContext implements Serializable {

    private static ThreadLocal<UserVO> user = new ThreadLocal<>();

    public static UserVO getUser() {
        return user.get();
    }

    public static void setUser(UserVO user) {
        LoginUserContext.user.set(user);
    }

}

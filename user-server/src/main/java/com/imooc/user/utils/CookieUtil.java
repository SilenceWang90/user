package com.imooc.user.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Classname CookieUtil
 * @Description TODO
 * @Date 2019/12/15 18:18
 * @Created by wangpeng116
 */
public class CookieUtil {
    public static void set(HttpServletResponse httpServletResponse, String name, String value, int magAge) {
        Cookie cookie = new Cookie(name, value);
        cookie.setDomain("localhost");
        cookie.setPath("/");
        cookie.setMaxAge(magAge);
        httpServletResponse.addCookie(cookie);
    }

    public static Cookie get(HttpServletRequest httpServletRequest, String name) {
        Cookie[] cookies = httpServletRequest.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (name.equals(cookie.getName())) {
                    return cookie;
                }
            }
        }
        return null;
    }
}

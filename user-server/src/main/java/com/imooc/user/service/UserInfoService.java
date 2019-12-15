package com.imooc.user.service;

import com.imooc.user.dataobject.UserInfo;

/**
 * @Classname UserInfoService
 * @Description TODO
 * @Date 2019/12/15 18:00
 * @Created by wangpeng116
 */
public interface UserInfoService {
    UserInfo findByOpenid(String openid);
}

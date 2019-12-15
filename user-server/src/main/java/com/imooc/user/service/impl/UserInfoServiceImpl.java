package com.imooc.user.service.impl;

import com.imooc.user.dataobject.UserInfo;
import com.imooc.user.repository.UserInfoRepository;
import com.imooc.user.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Classname UserInfoServiceImpl
 * @Description TODO
 * @Date 2019/12/15 18:00
 * @Created by wangpeng116
 */
@Service
public class UserInfoServiceImpl implements UserInfoService {
    @Autowired
    private UserInfoRepository userInfoRepository;

    @Override
    public UserInfo findByOpenid(String openid) {
        return userInfoRepository.findByOpenid(openid);
    }
}

package com.imooc.user.repository;

import com.imooc.user.dataobject.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @Classname UserInfoRepository
 * @Description TODO
 * @Date 2019/12/15 17:25
 * @Created by wangpeng116
 */
@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo, String> {
    UserInfo findByOpenid(String openid);
}

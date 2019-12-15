package com.imooc.user.dataobject;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @Classname UserInfo
 * @Description TODO
 * @Date 2019/12/15 17:19
 * @Created by wangpeng116
 */
@Data
@Entity
public class UserInfo {
    @Id
    private String id;

    private String username;

    private String password;

    private String openid;

    private Integer role;

}

package com.imooc.user.enums;

import lombok.Getter;

/**
 * @Classname RoleEnum
 * @Description TODO
 * @Date 2019/12/15 18:15
 * @Created by wangpeng116
 */
@Getter
public enum RoleEnum {
    BUYER(1,"买家"),
    SELLER(2,"卖家"),
    ;

    private Integer code;
    private String message;

    RoleEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}

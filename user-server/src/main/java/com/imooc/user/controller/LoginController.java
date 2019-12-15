package com.imooc.user.controller;

import com.imooc.user.VO.ResultVO;
import com.imooc.user.constants.CookieConstant;
import com.imooc.user.dataobject.UserInfo;
import com.imooc.user.enums.ResultEnum;
import com.imooc.user.enums.RoleEnum;
import com.imooc.user.service.UserInfoService;
import com.imooc.user.utils.CookieUtil;
import com.imooc.user.utils.ResultVOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 * @Classname LoginController
 * @Description TODO
 * @Date 2019/12/15 18:02
 * @Created by wangpeng116
 */
@RestController
@RequestMapping("/login")
public class LoginController {
    @Autowired
    private UserInfoService userInfoService;

    /**
     * 买家登陆
     *
     * @param openid
     * @param httpServletResponse
     * @return
     */
    @GetMapping("/buyer")
    public ResultVO buyer(@RequestParam("openid") String openid, HttpServletResponse httpServletResponse) {
        //1、通过openid查询数据库
        UserInfo userInfo = userInfoService.findByOpenid(openid);
        if (userInfo == null) {
            return ResultVOUtil.error(ResultEnum.LOGIN_FAIL);
        }
        //2、判断角色
        if (!RoleEnum.BUYER.getCode().equals(userInfo.getRole())) {
            return ResultVOUtil.error(ResultEnum.ROLE_ERROR);
        }
        //3、设置cookie
        CookieUtil.set(httpServletResponse, CookieConstant.OPENID, openid, CookieConstant.expire);
        return ResultVOUtil.success();
    }


    /**
     * 卖家登陆
     *
     * @param openid
     * @param httpServletResponse
     * @return
     */
    @GetMapping("/seller")
    public ResultVO seller(@RequestParam("openid") String openid, HttpServletResponse httpServletResponse) {
        return null;
    }
}

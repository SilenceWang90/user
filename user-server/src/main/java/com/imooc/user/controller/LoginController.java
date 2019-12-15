package com.imooc.user.controller;

import com.imooc.user.VO.ResultVO;
import com.imooc.user.constants.CookieConstant;
import com.imooc.user.dataobject.UserInfo;
import com.imooc.user.enums.ResultEnum;
import com.imooc.user.enums.RoleEnum;
import com.imooc.user.service.UserInfoService;
import com.imooc.user.utils.CookieUtil;
import com.imooc.user.utils.ResultVOUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static com.imooc.user.constants.RedisConstant.TOKEN_TEMPLATE;

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
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

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
    public ResultVO seller(@RequestParam("openid") String openid,
                           HttpServletRequest httpServletRequest,
                           HttpServletResponse httpServletResponse) {
        //判断是否已登录
        Cookie cookie = CookieUtil.get(httpServletRequest, CookieConstant.TOKEN);
        if (cookie != null &&
                StringUtils.isNotBlank(stringRedisTemplate.opsForValue()
                        .get(String.format(TOKEN_TEMPLATE, cookie.getValue())))) {
            return ResultVOUtil.success();
        }
        //1、通过openid查询数据库
        UserInfo userInfo = userInfoService.findByOpenid(openid);
        if (userInfo == null) {
            return ResultVOUtil.error(ResultEnum.LOGIN_FAIL);
        }
        //2、判断角色
        if (!RoleEnum.SELLER.getCode().equals(userInfo.getRole())) {
            return ResultVOUtil.error(ResultEnum.ROLE_ERROR);
        }

        //3、redis里设置key=UUID，value=xyz
        String token = UUID.randomUUID().toString();
        Integer expire = CookieConstant.expire;
        stringRedisTemplate.opsForValue().set(String.format(TOKEN_TEMPLATE, token)
                , openid, expire, TimeUnit.SECONDS);
        //4、设置cookie，openid=xyz
        CookieUtil.set(httpServletResponse, CookieConstant.TOKEN, token, CookieConstant.expire);
        return ResultVOUtil.success();
    }
}

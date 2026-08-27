package com.sky.controller.user;

import com.sky.constant.JwtClaimsConstant;
import com.sky.properties.JwtProperties;
import com.sky.result.Result;
import com.sky.utils.JwtUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
@Api(tags = "【测试用】模拟用户登录，不需要微信")
@Slf4j
public class MockUserLoginController {

    @Autowired
    private JwtProperties jwtProperties;

    /**
     * 模拟小程序用户登录，直接返回token，写死userId=1
     */
    @PostMapping("/mockLogin")
    @ApiOperation("模拟用户登录，获取user的token，测试购物车用")
    public Result<Map<String,Object>> mockLogin(){
        Long mockUserId = 1L; //模拟用户id，数据库user表要有id=1记录

        Map<String,Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.USER_ID, mockUserId);

        //生成user端token，使用user秘钥
        String token = JwtUtil.createJWT(
                jwtProperties.getUserSecretKey(),
                jwtProperties.getUserTtl(),
                claims
        );

        Map<String,Object> res = new HashMap<>();
        res.put("id",mockUserId);
        res.put("token",token);
        log.info("模拟用户登录成功，userId={},token={}",mockUserId,token);
        return Result.success(res);
    }
}


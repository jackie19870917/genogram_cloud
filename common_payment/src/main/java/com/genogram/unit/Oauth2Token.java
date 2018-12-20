package com.genogram.unit;

import lombok.Data;

/**
 * 类名: WeixinOauth2Token </br>
 * 描述:  网页授权信息  </br>
 * 创建时间：  2015-11-27 </br>
 * 发布版本：V1.0  </br>
 *
 * @author Administrator
 */
@Data
public class Oauth2Token {
    /**
     * 网页授权接口调用凭证
     */
    private String accessToken;
    /**
     * 凭证有效时长
     */
    private int expiresIn;
    /**
     * 用于刷新凭证
     */
    private String refreshToken;
    /**
     * 用户标识
     */
    private String openId;
    /**
     * 用户授权作用域
     */
    private String scope;

}
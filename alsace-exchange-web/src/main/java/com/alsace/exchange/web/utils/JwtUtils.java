package com.alsace.exchange.web.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.google.common.base.Throwables;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

/**
 * JWT相关工具类
 */
@Slf4j
public class JwtUtils {

  private static final String LOGIN_ACCOUNT = "loginAccount";
  private static final String USER_NAME = "userName";

  /**
   * 校验token是否正确
   *
   * @param token  密钥
   * @param secret 用户的密码
   * @return 是否正确
   */
  public static boolean verify(String token, String loginAccount, String userName,String secret) {
    try {
      //根据密码生成JWT效验器
      Algorithm algorithm = Algorithm.HMAC256(secret);
      JWTVerifier verifier = JWT.require(algorithm)
          .withClaim(LOGIN_ACCOUNT, loginAccount)
          .withClaim(USER_NAME, userName)
          .build();
      //效验TOKEN
      DecodedJWT jwt = verifier.verify(token);
      return true;
    } catch (Exception exception) {
      log.error(Throwables.getStackTraceAsString(exception));
      return false;
    }
  }

  /**
   * 获得token中的信息无需secret解密也能获得
   *
   * @return token中包含的用户名
   */
  public static String getLoginAccount(String token) {
    try {
      DecodedJWT jwt = JWT.decode(token);
      return jwt.getClaim(LOGIN_ACCOUNT).asString();
    } catch (JWTDecodeException exception) {
      log.error(Throwables.getStackTraceAsString(exception));
      return null;
    }
  }


  /**
   * 获得token中的信息无需secret解密也能获得
   *
   * @return token中包含的用户姓名
   */
  public static String getUserName(String token) {
    try {
      DecodedJWT jwt = JWT.decode(token);
      return jwt.getClaim(USER_NAME).asString();
    } catch (JWTDecodeException exception) {
      log.error(Throwables.getStackTraceAsString(exception));
      return null;
    }
  }
  /**
   * 生成签名
   *
   * @param loginAccount 用户名
   * @param secret       用户的密码
   * @return 加密的token
   */
  public static String sign(String loginAccount,String userName, String secret, int expireMinute) {
    Date date = new Date(System.currentTimeMillis() + expireMinute * 60000L);
    Algorithm algorithm = Algorithm.HMAC256(secret);
    // 附带loginAccount信息
    return JWT.create()
        .withClaim(LOGIN_ACCOUNT, loginAccount)
        .withClaim(USER_NAME,userName)
        .withExpiresAt(date)
        .sign(algorithm);

  }

}

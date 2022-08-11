package com.pangpangyu.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.pangpangyu.po.User;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JWTUtils {

    /**
     * 过期时间为1天
     */
    private static final long EXPIRE_TIME = 24*60*60*1000;

    /**
     * token私钥
     */
    private static final String TOKEN_SECRET = "joijsdfjlsjfljfljl5135313135";

    public static String createToken(User user) {
        //过期时间
        Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);  //前面加上currentTimeMillis来保证每次生成的token不一样

        //私钥及加密算法
        Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);

        //设置头信息
        Map<String, Object> header = new HashMap<>(2);
        header.put("typ", "JWT");
        header.put("alg", "HS256");

        String token = JWT.create().withHeader(header)
                .withClaim("id", String.valueOf(user.getId())).withClaim("username", user.getUsername())
                .withClaim("avatar", user.getAvatar()).withClaim("email", user.getEmail())
                .withExpiresAt(date).sign(algorithm);  //withHeader是封装Token的header //withClaim是传入payload  //sign 是signature

        return token;
    }

    public static DecodedJWT verifyToken(String token) throws Exception {
        Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
        JWTVerifier verifier = JWT.require(algorithm).build();
        //DecodedJWT 将jwt解密 将xxx.yyy.zzz 分开
        return verifier.verify(token);
    }

    public static String parserToken(String token, String claim) {
        return JWT.decode(token).getClaim(claim).asString();
    }
}

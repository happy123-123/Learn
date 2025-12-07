package com.happy.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.Map;

/**
 * JWT 工具类，用于生成和解析 JWT 令牌
 */
public class JwtUtils {

    // 签名密钥（Base64 编码字符串，实际使用中应谨慎保管）
    private static final String SIGN_KEY = "SVRIRUlNQQ==";

    // 令牌过期时间：12小时（单位：毫秒）
    private static final Long EXPIRE = 43200000L;

    /**
     * 生成 JWT 令牌
     *
     * @param claims 要存入 JWT 的自定义载荷信息（键值对）
     * @return 生成的 JWT 字符串
     */
    public static String generateJwt(Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims) // 设置载荷
                .signWith(SignatureAlgorithm.HS256, SIGN_KEY) // 签名算法与密钥
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE)) // 过期时间
                .compact();
    }

    /**
     * 解析 JWT 令牌
     *
     * @param jwt JWT 令牌字符串
     * @return 解析出的 Claims 对象，包含原始载荷数据
     * @throws io.jsonwebtoken.ExpiredJwtException   如果令牌已过期
     * @throws io.jsonwebtoken.MalformedJwtException 如果令牌格式错误
     * @throws io.jsonwebtoken.SignatureException    如果签名不匹配
     */
    public static Claims parseJWT(String jwt) {
        return Jwts.parser()
                .setSigningKey(SIGN_KEY) // 设置签名密钥
                .parseClaimsJws(jwt)     // 解析并验证 JWT
                .getBody();              // 返回 payload 内容
    }
}

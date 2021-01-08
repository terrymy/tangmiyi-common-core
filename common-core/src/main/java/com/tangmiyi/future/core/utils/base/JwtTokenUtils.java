package com.tangmiyi.future.core.utils.base;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.DatatypeConverter;
import java.io.Serializable;
import java.security.Key;
import java.util.Date;

/**
 * JWT 工具类
 */
@Component
@Slf4j
@RefreshScope
public class JwtTokenUtils implements Serializable {

    private static final long serialVersionUID = 1L;


    @Value("${jwt.header:Authorization}")
    private String tokenHeader;

    @Value("${jwt.token.head:Bearer}")
    private String tokenHead;

    @Value("${jwt.secret:jgrjirj!@5678}")
    private String secret;

    private final static String KEY = "!@5678";

    /**
     * 设置登录过期时间为 一周
     */
    private final static long KEEP_TIME = 7 * 24 * 60 * 60 * 1000;

    /**
     * 获取密钥
     *
     * @return
     */
    private Key getKeyInstance() {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(KEY);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
        return signingKey;
    }

    /**
     * 生成 jwt
     *
     * @param id
     * @param subject
     * @return
     */
    public String createJWT(String id, String subject,String audience,String iss) {
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        JwtBuilder builder = Jwts.builder().setAudience(audience).setIssuer(iss)
                .setId(id)
                .setIssuedAt(now)
                /**
                 * 代表这个JWT的主体，即它的所有人，这个是一个json格式的字符串
                 */
                .setSubject(subject)
                /**
                 * 设置签名使用的签名算法和签名使用的秘钥
                 */
                .signWith(SignatureAlgorithm.HS256, getKeyInstance());
        // 设置过期时间
        long expMillis = nowMillis + KEEP_TIME;
        Date exp = new Date(expMillis);
        builder.setExpiration(exp);
        return builder.compact();
    }

    /**
     * 解析jwtToken
     *
     * @param jwtToken
     * @return
     */
    public Claims verifyJWT(String jwtToken,String audience,String iss) {
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(getKeyInstance()).requireAudience(audience).requireIssuer(iss)
                    .parseClaimsJws(jwtToken).getBody();
        } catch (Exception e) {
            log.error("JWT signature does not match:"+ jwtToken);
            return null;
        }
        return claims;
    }

    /**
     * jwt过期时，重新生成新的 jwt
     *
     * @param token
     * @return
     */
    public String updateJWT(String token,String audience,String iss) {
        try {
            Claims claims = verifyJWT(token,audience,iss);
            String id = claims.getId();
            String subject = claims.getSubject();
            return createJWT(id, subject,audience,iss);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "0";
    }

    /**
     * 从http head获取token
     * @param serverHttpRequest
     * @return
     */
    public String getTokenFromHttpHead(ServerHttpRequest serverHttpRequest) {
        String authHeader = serverHttpRequest.getHeaders().getFirst(tokenHeader);
        if (authHeader != null && authHeader.startsWith(tokenHead)) {
            return authHeader.substring(tokenHead.length());
        }
        return null;
    }

    /**
     * 从http head获取token
     * @param httpServletRequest
     * @return
     */
    public String getTokenFromHttpHead(HttpServletRequest httpServletRequest) {
        String authHeader = httpServletRequest.getHeader(this.tokenHeader);
        if (authHeader != null && authHeader.startsWith(tokenHead)) {
            return authHeader.substring(tokenHead.length());
        }
        return null;
    }
}
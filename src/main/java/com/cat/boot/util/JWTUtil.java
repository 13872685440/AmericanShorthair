package com.cat.boot.util;

import java.util.Date;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;

public class JWTUtil {

	private static final long EXPIRE_TIME = 24 * 60 * 30 * 1000;
	public static final String SECRET_KEY = "cat_123456";
	private static final String ISSUER = "issuer";

	public static String generateToken(String userId) {
		Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY); // 算法
		String token = JWT.create().withIssuer(ISSUER) // 签发人
				.withIssuedAt(new Date()) // 签发时间
				.withExpiresAt(new Date(System.currentTimeMillis() + EXPIRE_TIME)) // 过期时间
				.withClaim("userId", userId) // 保存身份标识
				.sign(algorithm);
		return token;
	}

	public static String getUserId(String token) {
		try {
			DecodedJWT jwt = JWT.decode(token);

			return jwt.getClaim("userId").asString();
		} catch (JWTDecodeException e) {
			return null;
		}
	}

}

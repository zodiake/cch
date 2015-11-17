package com.by.utils;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.util.HashMap;
import java.util.Map;

import com.auth0.jwt.JWTSigner;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.JWTVerifyException;
import com.by.model.Member;

public class JWTUtils {
	private final static String secrect = "crm";

	public static String encode(Member member) {
		JWTSigner signer = new JWTSigner(secrect);
		Map<String, Object> u = new HashMap<>();
		u.put("user", member);
		return signer.sign(u);
	}

	@SuppressWarnings("unchecked")
	public static Map<Object, Object> decode(String token) throws InvalidKeyException, NoSuchAlgorithmException,
			IllegalStateException, SignatureException, IOException, JWTVerifyException {
		Map<String, Object> decodedPayload = new JWTVerifier(secrect).verify(token);
		Map<Object, Object> map = (Map<Object, Object>) decodedPayload.get("user");
		return map;
	}
}

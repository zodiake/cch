package com.by.interceptor;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.auth0.jwt.JWTVerifier;
import com.by.model.User;

public class JWTInterceptor extends HandlerInterceptorAdapter {

	@Override
	@SuppressWarnings("unchecked")
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String authorization = request.getHeader("Authorization");
		if (authorization == null)
			return false;
		String[] tokens = authorization.split(" ");
		if (tokens.length != 2 && !tokens[0].equals("Bearer"))
			return false;
		Map<String, Object> decodedPayload = new JWTVerifier("crm").verify(tokens[1]);
		Map<Object, Object> map = (Map<Object, Object>) decodedPayload.get("user");
		String name = (String) map.get("name");
		Integer id = (Integer) map.get("id");
		request.setAttribute("user", new User(id.longValue(), name));
		return true;
	}
}

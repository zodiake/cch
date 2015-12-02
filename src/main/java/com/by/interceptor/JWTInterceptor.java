package com.by.interceptor;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.by.model.Member;
import com.by.utils.JWTUtils;

public class JWTInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String authorization = request.getHeader("Authorization");
		if (authorization == null)
			return false;
		String[] tokens = authorization.split(" ");
		if (tokens.length != 2 && !tokens[0].equals("Bearer"))
			return false;
		Map<Object, Object> map = JWTUtils.decode(tokens[1]);
		String name = (String) map.get("name");
		Integer id = (Integer) map.get("id");
		request.setAttribute("member", new Member(id.longValue(), name));
		return true;
	}
}

package com.by.security;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

public class AuthorityUtil {
	public static Set<SimpleGrantedAuthority> createAuthority(final String... name) {
		return Arrays.stream(name).map(i -> new SimpleGrantedAuthority(i)).collect(Collectors.toSet());
	}
}

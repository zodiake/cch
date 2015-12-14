package com.by.utils;

public class PageUtils {
	private static final int maxSize = 7;

	public static int computeLastPage(int totalPages) {
		if (maxSize > totalPages)
			return totalPages;
		else
			return maxSize;
	}
}

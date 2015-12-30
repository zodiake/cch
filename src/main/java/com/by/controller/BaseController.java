package com.by.controller;

import com.by.model.*;
import com.by.security.UserContext;
import com.by.service.MenuCategoryService;
import com.by.typeEnum.ValidEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

/**
 * Created by yagamai on 15-12-16.
 */ @Component public abstract class BaseController implements UtilContoller {
	protected final String SUCCESS = "保存成功";
	private final int maxSize = 7;
	@Autowired
	protected MenuCategoryService menuCategoryService;
	@Autowired
	protected UserContext userContext;

	protected Map<MenuCategory, List<Menu>> menus(User user) {
		return menuCategoryService.getCategoryAndMenu(user);
	}

	protected void addMenu(Model uiModel) {
		uiModel.addAttribute("menus", menus(userContext.getCurrentUser()));
		uiModel.addAttribute("subMenu", getSubMenu());
	}

	protected int computeLastPage(int totalPages) {
		if (maxSize > totalPages)
			return totalPages == 0 ? 1 : totalPages;
		else
			return maxSize;
	}

	protected void addClass(Rule rule, Model uiModel) {
		Calendar today = Calendar.getInstance();
		CouponStatusMessage message = null;
		if (rule.getBeginTime() == null && rule.getEndTime() == null) {
			message = getUsingMessage();
		}
		if (rule.getBeginTime() != null && rule.getEndTime() != null) {
			if (rule.getBeginTime().after(today)) {
				message = getEditMessage();
			} else if (rule.getEndTime().before(today)) {
				message = getCancelMessage();
			} else if (rule.getEndTime().after(today) && rule.getBeginTime().before(today)) {
				message = getUsingMessage();
			}
		}
		if (rule.getValid().equals(ValidEnum.INVALID)) {
			message = getCloseMessage(false);
			if (rule.getEndTime().before(today) && rule.getBeginTime().after(today)) {
				message = getCloseMessage(true);
			}
		}
		uiModel.addAttribute("message", message);
	}

	public abstract Menu getSubMenu();

	private CouponStatusMessage getUsingMessage() {
		CouponStatusMessage message = new CouponStatusMessage();
		message.setStatus("using-bar");
		message.setCanValid(true);
		message.setCanUpdate(false);
		message.setState("使用中");
		return message;
	}

	private CouponStatusMessage getEditMessage() {
		CouponStatusMessage message = new CouponStatusMessage();
		message.setStatus("edit-bar");
		message.setCanValid(true);
		message.setCanUpdate(true);
		message.setState("未生效");
		return message;
	}

	private CouponStatusMessage getCancelMessage() {
		CouponStatusMessage message = new CouponStatusMessage();
		message.setStatus("cancel-bar");
		message.setCanValid(false);
		message.setCanUpdate(false);
		message.setState("已过期");
		return message;
	}

	private CouponStatusMessage getCloseMessage(boolean canValid) {
		CouponStatusMessage message = new CouponStatusMessage();
		message.setStatus("close-bar");
		message.setCanValid(canValid);
		message.setCanUpdate(false);
		message.setState("已关闭");
		return message;
	}
}

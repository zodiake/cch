package com.by.controller;

import java.util.List;
import java.util.Locale;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.by.exception.Fail;
import com.by.exception.Status;
import com.by.exception.Success;
import com.by.model.Authority;
import com.by.model.Menu;
import com.by.model.Message;
import com.by.model.User;
import com.by.service.AuthorityService;
import com.by.service.UserService;

/**
 * Created by yagamai on 15-12-11.
 */
@Controller
@RequestMapping(value = "/admin/user")
public class AdminUserController extends BaseController {
	private final String LISTS = "admin/user/lists";
	private final String EDIT = "admin/user/edit";
	private final String CREATE = "admin/user/create";
	private final String REDIRECT = "redirect:/admin/user/";
	private final Menu subMenu = new Menu(15);
	@Autowired
	private UserService service;
	@Autowired
	private AuthorityService authorityService;
	@Autowired
	private MessageSource messageSource;

	@ModelAttribute("authorities")
	public List<Authority> authorities() {
		return authorityService.findAll();
	}

	@RequestMapping(value = "/duplicate", method = RequestMethod.GET)
	@ResponseBody
	public Status duplicate(@RequestParam("name") String name) {
		if (service.countByName(name) > 0)
			return new Fail("fail");
		return new Success<String>("success");
	}

	@RequestMapping(method = RequestMethod.GET)
	public String list(Model uiModel,
			@PageableDefault(page = INIT_PAGE, size = PAGE_SIZE, sort = "beginTime", direction = Sort.Direction.DESC) Pageable pageable) {
		Page<User> lists = service.findAll(null, pageable);
		uiModel.addAttribute("uses", lists);
		uiModel.addAttribute("last", computeLastPage(lists.getTotalPages()));
		addMenu(uiModel);
		return LISTS;
	}

	@RequestMapping(params = "form", method = RequestMethod.GET)
	public String form(Model uiModel) {
		uiModel.addAttribute("user", new User());
		addMenu(uiModel);
		return CREATE;
	}

	@RequestMapping(params = "form", method = RequestMethod.POST)
	public String save(@Valid @ModelAttribute("user") User user, BindingResult result, Model uiModel,
			RedirectAttributes redirectAttributes) {
		if (result.hasErrors()) {
			uiModel.addAttribute("user", user);
			addMenu(uiModel);
			uiModel.addAttribute("message",
					new Message("fail", messageSource.getMessage("save.fail", new Object[] {}, Locale.CHINESE)));
			return CREATE;
		}
		User u = service.save(user);
		redirectAttributes.addFlashAttribute("message",
				new Message("fail", messageSource.getMessage("save.success", new Object[] {}, Locale.CHINESE)));
		return REDIRECT + u.getId();
	}

	@RequestMapping(value = "/json", method = RequestMethod.GET)
	@ResponseBody
	public Page<User> listJson(
			@PageableDefault(page = INIT_PAGE, size = PAGE_SIZE, sort = "beginTime", direction = Sort.Direction.DESC) Pageable pageable) {
		Page<User> users = service.findAll(null, pageable);
		return users;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public String edit(@PathVariable("id") int id, Model uiModel) {
		User user = service.findOne(id);
		uiModel.addAttribute("user", user);
		addMenu(uiModel);
		return EDIT;
	}

	@Override
	public Menu getSubMenu() {
		return subMenu;
	}
}

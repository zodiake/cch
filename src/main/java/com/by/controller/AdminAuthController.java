package com.by.controller;

import com.by.model.Authority;
import com.by.model.Menu;
import com.by.model.Message;
import com.by.service.AuthorityService;
import com.by.service.MenuService;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Locale;

/**
 * Created by yagamai on 16-1-4.
 */
@Controller
@RequestMapping(value = "/admin/authorities")
public class AdminAuthController extends BaseController {
	private final Menu subMenu = new Menu(14);
	private final String LIST = "admin/authority/lists";
	private final String EDIT = "admin/authority/edit";
	private final String CREATE = "admin/authority/create";
	private final String REDIRECT = "redirect:/admin/authorities/";
	@Autowired
	private AuthorityService service;
	@Autowired
	private MenuService menuService;
	@Autowired
	private MessageSource messageSource;

	@RequestMapping(method = RequestMethod.GET)
	public String list(Model uiModel,
			@PageableDefault(page = INIT_PAGE, size = PAGE_SIZE, sort = "createdTime", direction = Sort.Direction.DESC) Pageable pageable) {
		Page<Authority> lists = service.findAll(pageable);
		uiModel.addAttribute("lists", lists);
		uiModel.addAttribute("last", computeLastPage(lists.getTotalPages()));
		addMenu(uiModel);
		return LIST;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public String edit(@PathVariable("id") int id, Model uiModel) {
		Authority authority = service.findOne(id);
		uiModel.addAttribute("authority", authority);
		uiModel.addAttribute("authMenus", menuService.findAll());
		addMenu(uiModel);
		return EDIT;
	}

	@RequestMapping(params = "form", method = RequestMethod.GET)
	public String form(Model uiModel) {
		Authority authority = new Authority();
		uiModel.addAttribute("authority", authority);
		uiModel.addAttribute("authMenus", menuService.findAll());
		addMenu(uiModel);
		return CREATE;
	}

	@RequestMapping(params = "form", method = RequestMethod.POST)
	public String save(@Valid @ModelAttribute("authority") Authority authority, BindingResult result, Model uiModel,
			RedirectAttributes redirectAttributes) {
		if (result.hasErrors()) {
			uiModel.addAttribute("authority", authority);
			uiModel.addAttribute("authMenus", menuService.findAll());
			uiModel.addAttribute("message",
					new Message("fail", messageSource.getMessage("save.fail", new Object[] {}, Locale.CHINESE)));
			addMenu(uiModel);
			return CREATE;
		}
		Authority a = service.save(authority);
		redirectAttributes.addFlashAttribute("message",
				new Message("success", messageSource.getMessage("save.success", new Object[] {}, Locale.CHINESE)));
		return REDIRECT + a.getId();
	}

	@Override
	public Menu getSubMenu() {
		return subMenu;
	}
}

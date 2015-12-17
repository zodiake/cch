package com.by.controller;

import com.by.utils.UpYun;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

/**
 * Created by yagamai on 15-12-17.
 */
@Controller
@RequestMapping("/admin/upload")
public class UploadController {
	@Autowired
	private UpYun yun;

	@RequestMapping(method = RequestMethod.POST)
	public String upload(MultipartFile file) {
		InputStream stream = null;
		try {
			stream = file.getInputStream();
			String fileName = UUID.randomUUID() + "." + file.getOriginalFilename().split("\\.")[1];
			Path filePath = Paths.get("/home/yagamai/Pictures/", fileName);
			Files.copy(stream, filePath);
			yun.writeFile("/test", new File(filePath.toString()), true);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "admin/card/edit";
	}

	@RequestMapping(method = RequestMethod.GET)
	public String get() {
		return "admin/card/upload";
	}

}

package com.by.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Calendar;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.by.utils.UpYun;

/**
 * Created by yagamai on 15-12-17.
 */
@Controller
@RequestMapping("/admin/upload")
public class UploadController {
	@Autowired
	private UpYun yun;

	@RequestMapping(method = RequestMethod.POST, produces = "text/html")
	@ResponseBody
	public String upload(MultipartFile file) {
		InputStream stream = null;
		String fileName;
		String serverFilePath;
		try {
			Calendar today = Calendar.getInstance();
			int year = today.get(Calendar.YEAR);
			int month = today.get(Calendar.MONTH);
			Path dir = Paths.get("Pictures", year + "", month + "");
			if (!Files.exists(dir)) {
				Files.createDirectories(dir);
			}
			stream = file.getInputStream();
			fileName = UUID.randomUUID() + "." + file.getOriginalFilename().split("\\.")[1];
			Path filePath = dir.resolve(fileName);
			Files.copy(stream, filePath);
			serverFilePath = "/" + year + "/" + month + "/" + fileName;
			yun.writeFile(serverFilePath, new File(filePath.toString()), true);
		} catch (IOException e) {
			return "fail";
		}
		return serverFilePath;
	}

	@RequestMapping(method = RequestMethod.GET)
	public String get() {
		return "admin/card/upload";
	}

}

package com.claz.serviceImpl;

import java.io.File;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.claz.service.UploadService;
@Service
public class UploadServiceImpl implements UploadService {

	@Autowired
	ServletContext app;
	
	public File save(MultipartFile file, String folder) {
		File dir = new File(app.getRealPath(folder));
		if (!dir.exists()) {
			dir.mkdirs();
		}
		String name = file.getOriginalFilename();
		try {
			File saveFile = new File(dir, name);
			file.transferTo(saveFile);
			System.out.println(saveFile.getAbsolutePath());
			return saveFile;
		} catch (Exception e) {
			throw new RuntimeException();
		}
	}

}

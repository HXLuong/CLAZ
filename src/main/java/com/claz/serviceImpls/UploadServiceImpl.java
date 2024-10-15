package com.claz.serviceImpls;

import java.io.File;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

<<<<<<< HEAD:src/main/java/com/claz/serviceImpl/UploadServiceImpl.java
import com.claz.service.UploadService;
=======
import com.claz.services.UploadService;




>>>>>>> cefbaa9380fbe81f6f5454181f197dfe67734ff9:src/main/java/com/claz/serviceImpls/UploadServiceImpl.java
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

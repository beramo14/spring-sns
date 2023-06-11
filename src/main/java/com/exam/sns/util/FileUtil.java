package com.exam.sns.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.NoSuchFileException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

@Component
public class FileUtil {
	
	
	@Value("${file.profile}")
	private String fileProfileDir;
	
	@Value("${file.root}")
	private String fileRootDir;
	
	
	
	public File getProfileImageFile(String fileName) throws Exception {
		File imageFile = new File(fileProfileDir + "/" + fileName);
		
		if(imageFile.exists() != true) {
			throw new NoSuchFileException(fileName + "is not found");
		} else if(imageFile.isFile() != true) {
			throw new NoSuchFileException(fileName + "is not File");
		}
		
		return imageFile;
	}
	
	
	public Resource getImageResource(File file) throws IOException{
		
		InputStream fis = new FileInputStream(file);
		return new InputStreamResource(fis);
		
	}
	
	
}

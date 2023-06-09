package com.exam.sns.util;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.NoSuchFileException;

import javax.imageio.ImageIO;
import java.awt.image.DataBufferByte;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

@Component
public class FileUtil {
	
	@Value("${file.root}")
	private String fileRootDir;
	
	public File getImageFile(String fileName) throws Exception {
		File imageFile = new File(fileRootDir + "/" + fileName);
		
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

/*
public byte[] extractBytes (String ImageName) throws IOException {
 // open image
 File imgPath = new File(ImageName);
 BufferedImage bufferedImage = ImageIO.read(imgPath);

 // get DataBufferBytes from Raster
 WritableRaster raster = bufferedImage .getRaster();
 DataBufferByte data   = (DataBufferByte) raster.getDataBuffer();

 return ( data.getData() );
}



 * 
 * */
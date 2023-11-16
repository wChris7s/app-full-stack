package com.chris.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;

public interface UploadFileService {
   Resource loadImage(String photoName) throws MalformedURLException;

   String copy(MultipartFile file) throws IOException;

   Boolean delete(String photoName);

   Path getPath(String photoName);
}

package com.chris.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class UploadFileServiceImpl implements UploadFileService {
   private static final Logger logger = LoggerFactory.getLogger(UploadFileServiceImpl.class);

   private final static String DIR = "upload";

   @Override
   public Resource loadImage(String photoName) throws MalformedURLException {
      Path path = getPath(photoName);

      logger.info(path.toString());

      Resource resource = new UrlResource(path.toUri());

      if (!resource.exists() && !resource.isReadable()) {
         path = Paths.get("src/main/resources/static/images").resolve("Empty-User.png").toAbsolutePath();
         resource = new UrlResource(path.toUri());
         logger.error("Error, no se pudo cargar la imagen: " + photoName);
      }

      return resource;
   }

   @Override
   public String copy(MultipartFile file) throws IOException {
      String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename().replace(" ", "");
      Path path = getPath(fileName);
      logger.info(path.toString());
      Files.copy(file.getInputStream(), path);
      return fileName;
   }

   @Override
   public Boolean delete(String photoName) {
      if (photoName != null && !photoName.isEmpty()) {
         Path lastPhotoDir = getPath(photoName);
         File lastPhotoFile = lastPhotoDir.toFile();

         if (lastPhotoFile.exists() && lastPhotoFile.canRead()) {
            return lastPhotoFile.delete();
         }
      }
      return false;
   }

   @Override
   public Path getPath(String photoName) {
      return Paths.get(DIR).resolve(photoName).toAbsolutePath();
   }
}

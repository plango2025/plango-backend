package com.example.plango.file.service;

import com.example.plango.file.model.StorageFile;
import com.example.plango.user.model.UserInfo;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public interface FileService {
    String uploadFileAndGetUrl(MultipartFile file, UserInfo uploader) throws IOException;
    StorageFile upload(MultipartFile file, UserInfo uploader) throws IOException;
    Resource readResource(String filename) throws FileNotFoundException;
    void delete(String filename) throws IOException;
    void deleteByFilenames(List<String> filenames);
    String getResourceContentType(Resource resource) throws IOException;
    String convertFilenameToUrl(String filename);
    String convertUrlToFilename(String url);
}

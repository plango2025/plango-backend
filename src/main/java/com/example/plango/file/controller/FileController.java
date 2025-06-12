package com.example.plango.file.controller;

import com.example.plango.common.dto.SuccessResponse;
import com.example.plango.common.security.SecurityService;
import com.example.plango.file.service.FileService;
import com.example.plango.user.model.UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/files")
public class FileController {
    private final FileService fileService;
    private SecurityService securityService;

    @PostMapping(value = "/upload", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SuccessResponse> uploadFiles(List<MultipartFile> files) throws Exception {
        // 사용자 정보 가져오기
        UserInfo uploader=securityService.getUserInfo();

        // 파일 업로드 및 URL 생성
        List<String> urls=new LinkedList<>();
        for(MultipartFile file: files){
            String url=fileService.uploadFileAndGetUrl(file, uploader);
            urls.add(url);
        }

        // 응답 반환
        return ResponseEntity.status(HttpStatus.CREATED).body(SuccessResponse.of(urls));
    }

    /**
     * 파일 조회 API
     * @param filename 파일명
     * @return  (status) 200,
     *          (contentType) 파일 형식
     *          (body) 파일 자원
     * @throws IOException IOException
     */
    @GetMapping("/{filename}")
    public ResponseEntity<Resource> read(@PathVariable(name = "filename") String filename) throws IOException {
        // 파일 조회
        Resource resource=fileService.readResource(filename);

        // 파일 형식 조회
        String contentType=fileService.getResourceContentType(resource);
        MediaType mediaType=MediaType.parseMediaType(contentType);

        return ResponseEntity.status(HttpStatus.OK)
                .contentType(mediaType)
                .body(resource);
    }
}

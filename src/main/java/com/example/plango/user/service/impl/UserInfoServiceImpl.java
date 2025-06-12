package com.example.plango.user.service.impl;

import com.example.plango.common.security.SecurityService;
import com.example.plango.file.model.StorageFile;
import com.example.plango.file.service.FileService;
import com.example.plango.review.repository.ReviewRepository;
import com.example.plango.user.dto.UserInfoReadResponseDTO;
import com.example.plango.user.dto.UserInfoUpdateRequestDTO;
import com.example.plango.user.model.UserInfo;
import com.example.plango.user.repository.UserInfoRepository;
import com.example.plango.user.service.UserInfoService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import com.example.plango.common.enums.TargetType;

import java.io.IOException;
import java.time.LocalDate;


@Log4j2
@Service
@Transactional
@RequiredArgsConstructor
public class UserInfoServiceImpl implements UserInfoService {
    private final UserInfoRepository userInfoRepository;
    private final SecurityService securityService;
    private final FileService fileService;
    private final ReviewRepository reviewRepository;


    /**
     * 나의 사용자 정보 조회
     * @return 나의 사용자 정보
     */
    @Override
    public UserInfoReadResponseDTO readMe(){
        // 사용자 ID 추출
        String userId=securityService.getUserInfo().getId();

        // 사용자 정보 조회
        UserInfo userInfo=userInfoRepository.findById(userId)
                .orElseThrow(()-> new EntityNotFoundException("존재하지 않는 사용자입니다. (id = "+userId+")"));

        StorageFile profileImage=userInfo.getProfileImage();
        String profileImageUrl=fileService.convertFilenameToUrl(profileImage.getFilename());

        long reviewCount = reviewRepository.countByTargetTypeAndUser_Id(TargetType.SCHEDULE_REVIEW, userId);
        return UserInfoReadResponseDTO.builder()
                .id(userInfo.getId())
                .nickname(userInfo.getNickname())
                .profileImage(profileImageUrl)
                .address(userInfo.getAddress())
                .birth(userInfo.getBirth())
                .tripCount(reviewCount)
                .about(userInfo.getAbout())
                .build();
    }

    /**
     * 사용자 정보 설정
     * @param userInfoUpdateRequestDTO 사용자 정보 설정 요청
     * @return 사용자 ID
     */
    @Override
    public String update(String userId, UserInfoUpdateRequestDTO userInfoUpdateRequestDTO){
        // 사용자 ID 추출
        String myUserId= securityService.getUserInfo().getId();
        if(!myUserId.equals(userId)){
            throw new AccessDeniedException("사용자 본인의 정보만 수정할 수 있습니다.");
        }

        // 기존 사용자 정보 가져오기
        UserInfo userInfo=userInfoRepository.findById(userId)
                .orElseThrow(()-> new EntityNotFoundException("존재하지 않는 사용자입니다. (id = "+userId+")"));

        // 사용자 정보 수정
        String nickname = userInfoUpdateRequestDTO.getNickname()!=null? userInfoUpdateRequestDTO.getNickname():userInfo.getNickname();
        String address = userInfoUpdateRequestDTO.getAddress()!=null? userInfoUpdateRequestDTO.getAddress():userInfo.getAddress();
        LocalDate birth = userInfoUpdateRequestDTO.getBirth()!=null? userInfoUpdateRequestDTO.getBirth():userInfo.getBirth();
        String about = userInfoUpdateRequestDTO.getAbout()!=null? userInfoUpdateRequestDTO.getAbout():userInfo.getAbout();

        UserInfo newUserInfo=userInfo.toBuilder()
                .nickname(nickname)
                .address(address)
                .birth(birth)
                .about(about)
                .build();

        // DB 저장
        userInfoRepository.save(newUserInfo);

        // 유저 ID 반환
        return userId;
    }

    /**
     * 사용자 프로필 사진 업로드
     * @param file 프로필 사진 파일
     * @return 프로필 사진 URL
     */
    @Override
    public String updateProfileImage(String userId, MultipartFile file) throws Exception{
        // 사용자 ID 추출
        String myUserId= securityService.getUserInfo().getId();
        if(!myUserId.equals(userId)){
            throw new AccessDeniedException("사용자 본인의 정보만 수정할 수 있습니다.");
        }

        // 사용자 정보 가져오기
        UserInfo oldUserInfo=userInfoRepository.findById(userId)
                .orElseThrow(()-> new EntityNotFoundException("존재하지 않는 사용자입니다. (id = "+userId+")"));

        StorageFile oldProfileImage=oldUserInfo.getProfileImage();

        StorageFile newProfileImage=null;
        try{
            // 저장소에 새로운 프로필 사진 업로드
            UserInfo uploader=oldUserInfo;
            newProfileImage=fileService.upload(file, uploader);

            // 새로운 UserInfo 엔티티 생성
            UserInfo newUserInfo=oldUserInfo.toBuilder()
                    .profileImage(newProfileImage)
                    .build();

            // DB 저장
            userInfoRepository.save(newUserInfo);

        }catch(Exception e){
            log.error(e.getMessage());

            // 저장소에 새로 업로드한 파일이 있다면
            if(newProfileImage!=null){
                try{
                    // 업로드한 파일 삭제
                    fileService.delete(newProfileImage.getFilename());
                } catch (IOException ioException){
                    // 만약 삭제하는 것도 실패했다면, 로그를 남긴다.
                    log.error(ioException.getMessage());
                }
            }

            throw e;
        }

        // 기존에 프로필 이미지가 존재하는 상태였다면
        if(oldProfileImage!=null){
            // 기존 프로필 이미지를 저장소에서 삭제
            try{
                fileService.delete(oldProfileImage.getFilename());
            } catch (IOException ioException){
                // 만약 삭제에 실패했다면, 로그를 남긴다.
                log.error(ioException.getMessage());
            }
        }

        return fileService.convertFilenameToUrl(newProfileImage.getFilename());
    }
}

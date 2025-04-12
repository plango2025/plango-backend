package com.example.plango.common.url;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class UrlManager {
    @Value("${plango.front.domain}")
    private String frontDomain;
    @Value("${plango.back.domain}")
    private String backDomain;
    @Value("${plango.python.domain}")
    private String pythonDomain;


    public String getUrl(UrlPath urlPath){
        UrlSource source=urlPath.getSource();

        if(source!=null){
            return switch (source) {
                case FRONT    -> frontDomain + urlPath.getPath();   // 프론트 엔드 URL
                case BACK     -> backDomain + urlPath.getPath();    // 백엔드 서버 URL
                case PYTHON   -> pythonDomain + urlPath.getPath();    // 파이썬 서버 URL
                case EXTERNAL -> urlPath.getPath();                 // 외부 URL
            };
        }
        else{
            throw new RuntimeException("urlPath의 source가 null로 설정되어 있습니다.");
        }
    }
}

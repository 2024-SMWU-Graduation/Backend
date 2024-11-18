package smwu.project.domain.enums;

public enum OAuthProvider {
    ORIGIN,
    KAKAO,
    NAVER;

    public static OAuthProvider fromString(String provider) {
        switch(provider) {
            case "kakao" : return KAKAO;
            case "naver" : return NAVER;
            default : return ORIGIN;
        }
    }
}

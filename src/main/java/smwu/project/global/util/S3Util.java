package smwu.project.global.util;

import org.springframework.web.multipart.MultipartFile;
import smwu.project.global.exception.CustomException;
import smwu.project.global.exception.errorCode.S3ErrorCode;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public abstract class S3Util {

    private static final String INTRODUCE_DIR = "introduce";
    private static final String USER_DIR = "user";
    private static final String RANDOM_DIR = "random";

    /**
     * 파일 존재 여부 검사
     */
    public static boolean doesFileExist(MultipartFile multipartFile) {
        return !(multipartFile == null || multipartFile.isEmpty());
    }

    /**
     * 파일 확장자 검사
     */
    public static String getValidateVideoExtension(String fileName) {
        List<String> validExtensionList = Arrays.asList("mp4", "avi");

        int extensionIndex = fileName.lastIndexOf(".");

        String extension = fileName.substring(extensionIndex + 1).toLowerCase();

        if (!validExtensionList.contains(extension)) {
            throw new CustomException(S3ErrorCode.INVALID_EXTENSION);
        }

        return extension;
    }

    public static String createIntroduceDir(Long userId) {
        return INTRODUCE_DIR + "/" + USER_DIR + "/" + userId;
    }

    /**
     * 파일명 랜덤 생성
     */
    public static String createFileName(String extension) {
        return UUID.randomUUID().toString().concat("." + extension);
    }
}
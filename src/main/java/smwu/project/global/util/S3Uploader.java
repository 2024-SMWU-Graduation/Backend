package smwu.project.global.util;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import smwu.project.global.exception.CustomException;
import smwu.project.global.exception.errorCode.S3ErrorCode;

import java.io.InputStream;

import static smwu.project.global.util.S3Util.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class S3Uploader {

    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public String uploadIntroduceInterview(MultipartFile file, Long userId, Long interviewId) {
        String imageDir = createIntroduceDir(userId); // 파일 저장 경로 생성
        ObjectMetadata metadata = setMetadataForIntroduceFeedback(file, interviewId);
        return uploadVideo(file, imageDir, metadata);
    }

//    public String uploadRandomInterview(MultipartFile file, Long storeId, Long userId) {
//        String imageDir = createThemeImageDir(storeId, themeId);
//        return uploadVideo(file, imageDir);
//    }

    private String uploadVideo(MultipartFile file, String videoDir, ObjectMetadata metadata) {
        if(!doesFileExist(file)) {
            return null;
        }
        String extension = getValidateVideoExtension(file.getOriginalFilename());
        String uploadFileName = videoDir + S3Util.createFileName(extension);
        return uploadFileToS3(file, uploadFileName, metadata);
    }

    private ObjectMetadata setMetadataForIntroduceFeedback(MultipartFile file, Long interviewId) {
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(file.getContentType());
        objectMetadata.addUserMetadata("interview-id", Long.toString(interviewId));
        return objectMetadata;
    }

    /**
     * 실제 S3에 이미지 파일 업로드
     */
    private String uploadFileToS3(MultipartFile file, String uploadFileName, ObjectMetadata metadata) {
        try (InputStream inputStream = file.getInputStream()) {
            amazonS3Client.putObject(new PutObjectRequest(bucket, uploadFileName, inputStream, metadata).withCannedAcl(CannedAccessControlList.PublicRead));
        } catch (Exception e) {
            log.error("이미지 업로드 중 오류가 발생했습니다.", e);
            throw new CustomException(S3ErrorCode.IMAGE_STREAM_ERROR);
        }
        return amazonS3Client.getUrl(bucket, uploadFileName).toString();
    }

    /**
     * DB에 저장된 이미지 링크로 S3의 단일 이미지 파일 삭제
     */
    public void deleteFileFromS3(String imagePath) {
        if(imagePath == null) {
            return;
        }
        String splitStr = ".com/";
        String fileName = imagePath.substring(imagePath.lastIndexOf(splitStr) + splitStr.length());

        amazonS3Client.deleteObject(new DeleteObjectRequest(bucket, fileName));
    }

    /**
     * 특정 디렉토리 내의 모든 파일 삭제
     */
    public void deleteFilesFromS3(String imageDir) {
        ObjectListing objectListing = amazonS3Client.listObjects(bucket, imageDir);

        if (objectListing.getObjectSummaries().isEmpty()) {
            log.info("파일이 존재하지 않습니다.");
            return;
        }

        while(true) {
            for (S3ObjectSummary summary : objectListing.getObjectSummaries()) {
                amazonS3Client.deleteObject(new DeleteObjectRequest(bucket, summary.getKey()));
                log.info("삭제 : " + summary.getKey());
            }

            if(objectListing.isTruncated()) {
                objectListing = amazonS3Client.listNextBatchOfObjects(objectListing);
            } else {
                break;
            }
        }
    }

}
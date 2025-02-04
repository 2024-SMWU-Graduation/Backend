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
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import static smwu.project.global.util.S3Util.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class S3Uploader {

    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public String uploadIntroduceInterview(MultipartFile file, Long userId, Long interviewId) {
        String videoDir = createIntroduceDir(userId); // 파일 저장 경로 생성
        ObjectMetadata metadata = setMetadataForIntroduceFeedback(file, interviewId);
        return uploadVideo(file, videoDir, metadata);
    }

    public String uploadRandomQuestion(MultipartFile file, Long userId, Long interviewId, Long questionId, String questionData) {
        String videoDir = createRandomDir(userId, interviewId);
        ObjectMetadata metadata = setMetadataForRandomFeedback(file, questionId, questionData);
        return uploadVideo(file, videoDir, metadata);
    }

    private String uploadVideo(MultipartFile file, String videoDir, ObjectMetadata metadata) {
        if(!doesFileExist(file)) {
            return null;
        }
        String extension = getValidateVideoExtension(file.getOriginalFilename());
        String uploadFileName = videoDir + S3Util.createFileName(extension);
        return uploadFileToS3(file, uploadFileName, metadata);
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

    private ObjectMetadata setMetadataForIntroduceFeedback(MultipartFile file, Long interviewId) {
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(file.getContentType());
        metadata.addUserMetadata("interview-id", Long.toString(interviewId));
        return metadata;
    }

    private ObjectMetadata setMetadataForRandomFeedback(MultipartFile file, Long questionId, String questionData) {
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(file.getContentType());
        metadata.addUserMetadata("question-id", Long.toString(questionId));

        String encodedQuestion = URLEncoder.encode(questionData, StandardCharsets.UTF_8);
        metadata.addUserMetadata("content", encodedQuestion);
        return metadata;
    }
}
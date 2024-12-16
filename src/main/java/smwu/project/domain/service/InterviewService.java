package smwu.project.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import smwu.project.domain.repository.InterviewRepository;
import smwu.project.global.util.S3Uploader;

@Service
@RequiredArgsConstructor
public class InterviewService {
    private final InterviewRepository interviewRepository;
    private final S3Uploader s3Uploader;

    public String uploadInterviewVideo(MultipartFile file) {
        return s3Uploader.uploadIntroduceInterview(file, 1L);
    }
}

package smwu.project.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import smwu.project.domain.dto.response.IntroduceInterviewListResponseDto;
import smwu.project.domain.dto.response.IntroduceInterviewResponseDto;
import smwu.project.domain.entity.IntroduceInterview;
import smwu.project.domain.entity.User;
import smwu.project.domain.repository.IntroduceInterviewRepository;
import smwu.project.global.util.S3Uploader;

import java.util.List;

@Service
@RequiredArgsConstructor
public class IntroduceInterviewService {
    private final IntroduceInterviewRepository introduceInterviewRepository;
    private final S3Uploader s3Uploader;

    @Transactional
    public IntroduceInterviewResponseDto uploadInterviewVideo(User user, MultipartFile file) {
        Long userId = user.getId();
        String videoUrl = s3Uploader.uploadIntroduceInterview(file, userId);

        IntroduceInterview introduceInterview = IntroduceInterview.builder()
                .user(user)
                .title("임시 제목이여")
                .videoUrl(videoUrl)
                .build();

        introduceInterviewRepository.save(introduceInterview);
        return IntroduceInterviewResponseDto.of(introduceInterview);
    }

    public IntroduceInterviewListResponseDto readIntroduceInterviewList(User user) {
        List<IntroduceInterview> interviewList =  introduceInterviewRepository.findAllByUser(user);

        return IntroduceInterviewListResponseDto.of(interviewList);
    }
}

package smwu.project.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import smwu.project.domain.dto.request.EditTitleRequestDto;
import smwu.project.domain.dto.response.IntroduceInterviewListResponseDto;
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

    private static final String INTERVIEW_DEFAULT_TITLE = "자기소개 모의 면접";

    @Transactional
    public String uploadInterviewVideo(User user, MultipartFile file) {
        Long userId = user.getId();
        String videoUrl = s3Uploader.uploadIntroduceInterview(file, userId);

        IntroduceInterview introduceInterview = IntroduceInterview.builder()
                .user(user)
                .title(INTERVIEW_DEFAULT_TITLE)
                .videoUrl(videoUrl)
                .build();

        introduceInterviewRepository.save(introduceInterview);
        return videoUrl;
    }

    public IntroduceInterviewListResponseDto readIntroduceInterviewList(User user) {
        List<IntroduceInterview> interviewList =  introduceInterviewRepository.findAllByUser(user);
        return IntroduceInterviewListResponseDto.of(interviewList);
    }

    @Transactional
    public void editInterviewTitle(User user, EditTitleRequestDto requestDto) {
        IntroduceInterview introduceInterview = introduceInterviewRepository.findByIdAndUserOrElseThrow(requestDto.getInterviewId(), user);
        introduceInterview.setTitle(requestDto.getTitle());
    }
}

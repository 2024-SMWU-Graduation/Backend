package smwu.project.domain.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import smwu.project.domain.dto.response.CreateRandomInterviewResponseDto;
import smwu.project.domain.service.RandomInterviewService;
import smwu.project.global.response.Response;
import smwu.project.global.security.UserDetailsImpl;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/interview/random")
public class RandomInterviewController {
    private final RandomInterviewService randomInterviewService;

    // 랜덤 인터뷰가 생성될 때 시작 버튼을 눌렀을 때,
    // RandomInterview 엔티티 생성..?
    @PostMapping
    public ResponseEntity<Response<CreateRandomInterviewResponseDto>> createRandomInterview(
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        CreateRandomInterviewResponseDto responseDto = randomInterviewService.createRandomInterview(userDetails.getUser());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Response.of("직무별 꼬리 질문 면접 생성", responseDto));
    }
}

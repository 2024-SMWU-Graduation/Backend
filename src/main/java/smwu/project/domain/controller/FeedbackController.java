package smwu.project.domain.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import smwu.project.domain.dto.request.FeedbackRequestDto;
import smwu.project.domain.service.IntroduceFeedbackService;
import smwu.project.global.response.Response;
import smwu.project.global.security.UserDetailsImpl;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/interview/feedback")
public class FeedbackController {
    private final IntroduceFeedbackService introduceFeedbackService;

    @PostMapping
    public ResponseEntity<Response<Void>> saveInterviewFeedback(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestBody FeedbackRequestDto requestDto
    ) {
        introduceFeedbackService.saveInterviewFeedback(userDetails.getUser(), requestDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(Response.of("분석 결과가 저장되었습니다."));
    }
}

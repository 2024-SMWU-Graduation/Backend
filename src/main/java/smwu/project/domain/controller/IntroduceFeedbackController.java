package smwu.project.domain.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import smwu.project.domain.dto.request.IntroduceAnalyzeUpdateRequestDto;
import smwu.project.domain.dto.request.IntroduceFeedbackRequestDto;
import smwu.project.domain.dto.response.FeedbackResponseDto;
import smwu.project.domain.service.IntroduceFeedbackService;
import smwu.project.global.response.Response;
import smwu.project.global.security.UserDetailsImpl;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/feedback/introduce")
public class IntroduceFeedbackController {
    private final IntroduceFeedbackService introduceFeedbackService;

    @PostMapping
    public ResponseEntity<Response<Void>> saveInterviewFeedback(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestBody IntroduceFeedbackRequestDto requestDto
    ) {
        introduceFeedbackService.saveInterviewFeedback(userDetails.getUser(), requestDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(Response.of("자기소개 면접 표정 분석 결과 저장"));
    }

    @PatchMapping
    public ResponseEntity<Response<Void>> updateAnalyzeLink(
            @RequestBody @Valid IntroduceAnalyzeUpdateRequestDto requestDto
    ) {
        introduceFeedbackService.updateAnalyzeLink(requestDto);
        return ResponseEntity.status(HttpStatus.OK)
                .body(Response.of("답변 내용 분석 결과 저장"));
    }

    @GetMapping("/{interviewId}")
    public ResponseEntity<Response<FeedbackResponseDto>> readFeedback(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long interviewId
    ) {
        FeedbackResponseDto responseDto = introduceFeedbackService.readFeedback(userDetails.getUser(), interviewId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(Response.of("자기소개 피드백 결과 조회 완료", responseDto));
    }
}

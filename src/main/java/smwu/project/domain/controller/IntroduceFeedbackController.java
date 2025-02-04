package smwu.project.domain.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import smwu.project.domain.dto.request.AnalyzeUpdateRequestDto;
import smwu.project.domain.dto.request.FeedbackRequestDto;
import smwu.project.domain.dto.response.FeedbackResponseDto;
import smwu.project.domain.service.IntroduceFeedbackService;
import smwu.project.global.response.Response;
import smwu.project.global.security.UserDetailsImpl;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/feedback")
public class IntroduceFeedbackController {
    private final IntroduceFeedbackService introduceFeedbackService;

    @PostMapping("/introduce")
    public ResponseEntity<Response<Void>> saveInterviewFeedback(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestBody FeedbackRequestDto requestDto
    ) {
        introduceFeedbackService.saveInterviewFeedback(userDetails.getUser(), requestDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(Response.of("분석 결과가 저장되었습니다."));
    }

    @PatchMapping("/introduce")
    public ResponseEntity<Response<Void>> updateAnalyzeLink(
            @RequestBody @Valid AnalyzeUpdateRequestDto requestDto
    ) {
        introduceFeedbackService.updateAnalyzeLink(requestDto);

        return ResponseEntity.status(HttpStatus.OK)
                .body(Response.of("답변 내용 분석 결과 저장 완료"));
    }

    @GetMapping("/introduce/{interviewId}")
    public ResponseEntity<Response<FeedbackResponseDto>> readFeedback(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long interviewId
    ) {
        FeedbackResponseDto responseDto = introduceFeedbackService.readFeedback(userDetails.getUser(), interviewId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(Response.of("분석 결과 조회가 완료되었습니다.", responseDto));
    }
}

package smwu.project.domain.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import smwu.project.domain.dto.request.RandomAnalyzeUpdateRequestDto;
import smwu.project.domain.dto.request.RandomFeedbackRequestDto;
import smwu.project.domain.service.RandomFeedbackService;
import smwu.project.global.response.Response;
import smwu.project.global.security.UserDetailsImpl;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/feedback/random")
public class RandomFeedbackController {
    private final RandomFeedbackService randomFeedbackService;

    @PostMapping("/question")
    public ResponseEntity<Response<Void>> saveQuestionFeedback(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestBody RandomFeedbackRequestDto requestDto
    ) {
        randomFeedbackService.saveQuestionFeedback(userDetails.getUser(), requestDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Response.of("직무별 면접 영상 표정 분석 결과가 저장되었습니다."));
    }

    @PatchMapping
    public ResponseEntity<Response<Void>> updateAnalyzeLink(
            @RequestBody RandomAnalyzeUpdateRequestDto requestDto
    ) {
        randomFeedbackService.updateAnalyzeLink(requestDto);
        return ResponseEntity.status(HttpStatus.OK)
                .body(Response.of("답변 내용 분석 결과가 저장되었습니다."));
    }
}

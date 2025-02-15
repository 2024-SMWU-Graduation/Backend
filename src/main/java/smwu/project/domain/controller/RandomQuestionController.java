package smwu.project.domain.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import smwu.project.domain.dto.request.CreateRandomQuestionRequestDto;
import smwu.project.domain.dto.request.TailQuestionRequestDto;
import smwu.project.domain.dto.response.RandomQuestionResponseDto;
import smwu.project.domain.dto.response.TailQuestionResponseDto;
import smwu.project.domain.service.RandomQuestionService;
import smwu.project.global.response.Response;
import smwu.project.global.security.UserDetailsImpl;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/interview/random")
public class RandomQuestionController {
    private final RandomQuestionService randomQuestionService;

    @PostMapping("/question")
    public ResponseEntity<Response<RandomQuestionResponseDto>> uploadRandomQuestion(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestPart(value = "file") MultipartFile file,
            @RequestPart CreateRandomQuestionRequestDto requestDto
    ) {
        RandomQuestionResponseDto responseDto = randomQuestionService.uploadRandomQuestion(userDetails.getUser(), file, requestDto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Response.of("직무별 질문 영상 업로드 완료", responseDto));
    }

    @PostMapping("/question/tail")
    public ResponseEntity<Response<Void>> createTailQuestion(
            @RequestBody TailQuestionRequestDto requestDto
    ) {
        randomQuestionService.createTailQuestion(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Response.of("꼬리 질문 데이터 저장 완료"));
    }

    @PostMapping("/question/{questionId}")
    public ResponseEntity<Response<RandomQuestionResponseDto>> uploadTailQuestion(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long questionId,
            @RequestPart(value = "file") MultipartFile file
    ) {
        RandomQuestionResponseDto responseDto = randomQuestionService.uploadTailQuestion(userDetails.getUser(), questionId, file);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Response.of("직무별 꼬리 질문 영상 업로드 완료", responseDto));
    }

    @GetMapping("/{interviewId}/question/tail")
    public ResponseEntity<Response<TailQuestionResponseDto>> getTailQuestion(
            @PathVariable Long interviewId
    ) {
        TailQuestionResponseDto responseDto = randomQuestionService.getTailQuestion(interviewId);

        return ResponseEntity.status(HttpStatus.OK)
                .body(Response.of("가장 최근에 생성된 꼬리 질문 조회 완료", responseDto));
    }
}

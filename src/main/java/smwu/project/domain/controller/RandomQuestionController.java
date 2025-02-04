package smwu.project.domain.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import smwu.project.domain.dto.request.CreateRandomQuestionRequestDto;
import smwu.project.domain.dto.response.RandomQuestionResponseDto;
import smwu.project.domain.service.RandomQuestionService;
import smwu.project.global.response.Response;
import smwu.project.global.security.UserDetailsImpl;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/interview/random/question")
public class RandomQuestionController {
    private final RandomQuestionService randomQuestionService;

    @PostMapping
    public ResponseEntity<Response<RandomQuestionResponseDto>> uploadRandomQuestion(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestPart(value = "file") MultipartFile file,
            @RequestPart CreateRandomQuestionRequestDto requestDto
    ) {
        RandomQuestionResponseDto responseDto = randomQuestionService.uploadRandomQuestion(userDetails.getUser(), file, requestDto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Response.of("직무 꼬리 질문 영상 업로드 완료", responseDto));
    }
}

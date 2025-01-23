package smwu.project.domain.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import smwu.project.domain.dto.request.EditTitleRequestDto;
import smwu.project.domain.dto.response.IntroduceInterviewListResponseDto;
import smwu.project.domain.service.IntroduceInterviewService;
import smwu.project.global.response.Response;
import smwu.project.global.security.UserDetailsImpl;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/interview")
public class IntroduceInterviewController {
    private final IntroduceInterviewService introduceInterviewService;

    @PostMapping
    public ResponseEntity<Response<String>> uploadInterviewVideo(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestPart(value = "file", required = false) MultipartFile file
    ) {
        String uploadUrl = introduceInterviewService.uploadInterviewVideo(userDetails.getUser(), file);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(Response.of("인터뷰 영상 업로드 완료", uploadUrl));
    }

    @GetMapping
    public ResponseEntity<Response<IntroduceInterviewListResponseDto>> readIntroduceInterviewList(
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        IntroduceInterviewListResponseDto responseDto = introduceInterviewService.readIntroduceInterviewList(userDetails.getUser());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(Response.of("자기소개 영상 조회 완료", responseDto));
    }

    @PatchMapping("/title")
    public ResponseEntity<Response<String>> editInterviewTitle(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestBody EditTitleRequestDto requestDto
    ) {
        introduceInterviewService.editInterviewTitle(userDetails.getUser(), requestDto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(Response.of("제목 수정 완료", requestDto.getTitle()));
    }

    @DeleteMapping("/introduce/{interviewId}")
    public ResponseEntity<Response<Void>> deleteInterview(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long interviewId
    ) {
        introduceInterviewService.deleteInterview(userDetails.getUser(), interviewId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(Response.of("인터뷰 삭제 완료"));
    }
}

package smwu.project.domain.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import smwu.project.domain.service.InterviewService;
import smwu.project.global.response.Response;
import smwu.project.global.security.UserDetailsImpl;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/interview")
public class InterviewController {
    private final InterviewService interviewService;

    @PostMapping
    public ResponseEntity<Response<String>> uploadInterviewVideo(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestPart(value = "file", required = false) MultipartFile file
    ) {
        String uploadUrl = interviewService.uploadInterviewVideo(userDetails.getUser(), file);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(Response.of("인터뷰 영상 업로드 완료", uploadUrl));
    }
}

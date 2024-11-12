package smwu.project.domain.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import smwu.project.domain.dto.request.EmailRequestDto;
import smwu.project.domain.dto.request.VerificationCodeRequestDto;
import smwu.project.domain.service.MailService;
import smwu.project.global.response.Response;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/mail")
public class MailController {
    private final MailService mailService;

    @GetMapping
    public ResponseEntity<Response<Void>> verifyMail(
            @RequestBody @Valid EmailRequestDto requestDto
    ) {
        mailService.verifyMail(requestDto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(Response.of("사용 가능한 이메일입니다."));
    }

    @PostMapping
    public ResponseEntity<Response<Void>> sendCertificationCode(
            @RequestBody @Valid EmailRequestDto requestDto
    ) {
        mailService.sendCertificationMail(requestDto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(Response.of("인증번호가 전송되었습니다."));
    }

    @GetMapping("/verification-code")
    public ResponseEntity<Response<Void>> validateVerificationCode(
            @RequestBody @Valid VerificationCodeRequestDto requestDto
    ) {
        mailService.validateVerificationCode(requestDto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(Response.of("이메일 인증이 완료되었습니다."));
    }
}

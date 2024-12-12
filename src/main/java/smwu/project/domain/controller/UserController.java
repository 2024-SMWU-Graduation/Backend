package smwu.project.domain.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import smwu.project.domain.dto.request.EditPasswordRequestDto;
import smwu.project.domain.dto.request.SignUpRequestDto;
import smwu.project.domain.dto.request.WithdrawRequestDto;
import smwu.project.domain.dto.response.UserInfoResponseDto;
import smwu.project.domain.service.UserService;
import smwu.project.global.response.Response;
import smwu.project.global.security.UserDetailsImpl;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<Response<Void>> signup(
            @RequestBody @Valid SignUpRequestDto signUpRequestDto
    ) {
        userService.signup(signUpRequestDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(Response.of(HttpStatus.CREATED.value(), "회원가입 성공"));
    }

    @GetMapping("/profile")
    public ResponseEntity<Response<UserInfoResponseDto>> readUserInfo(
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        UserInfoResponseDto responseDto = userService.readUserInfo(userDetails.getUser());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(Response.of("회원 정보 조회 성공", responseDto));
    }

    @PatchMapping("/password")
    public ResponseEntity<Response<Void>> editPassword(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestBody @Valid EditPasswordRequestDto requestDto
    ) {
        userService.editPassword(userDetails.getUser(), requestDto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(Response.of("비밀번호 수정 성공"));
    }

    @PatchMapping("/withdrawal")
    public ResponseEntity<Response<Void>> withdraw(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestBody @Valid WithdrawRequestDto requestDto
    ) {
        userService.withdraw(userDetails.getUser(), requestDto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(Response.of("회원 탈퇴 성공"));
    }

    @PatchMapping("/logout")
    public ResponseEntity<Response<Void>> logout(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        userService.logout(userDetails.getUser());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(Response.of("회원 로그아웃 성공"));
    }

    // TODO : 비밀번호 찾기 -> 이메일 적용 이후에 시도
}

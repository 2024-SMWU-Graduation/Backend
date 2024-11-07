package smwu.project.domain.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import smwu.project.domain.dto.request.SignUpRequestDto;
import smwu.project.domain.dto.response.UserInfoResponseDto;
import smwu.project.domain.service.UserService;
import smwu.project.global.response.Response;
import smwu.project.global.security.UserDetailsImpl;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {
    private final UserService userService;

    @PostMapping("/users/signup")
    public ResponseEntity<Response<Void>> signup(
            @RequestBody @Valid SignUpRequestDto signUpRequestDto
    ) {
        userService.signup(signUpRequestDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(Response.of(HttpStatus.CREATED.value(), "회원가입 성공"));
    }

    @GetMapping("/users")
    public ResponseEntity<Response<UserInfoResponseDto>> readUserInfo(
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        UserInfoResponseDto responseDto = userService.readUserInfo(userDetails.getUser());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(Response.of(HttpStatus.OK.value(), "회원 정보 조회 성공", responseDto));
    }

    // TODO : 비밀번호 수정
    // TODO : 비밀번호 찾기 -> 이메일 적용 이후에 시도
}

package smwu.project.domain.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import smwu.project.domain.dto.request.SignUpRequestDto;
import smwu.project.domain.service.UserService;
import smwu.project.global.response.Response;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {
    private final UserService userService;

    @PostMapping("/users/signup")
    public ResponseEntity<Response<Void>> signup(@RequestBody @Valid SignUpRequestDto signUpRequestDto) {
        userService.signup(signUpRequestDto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(Response.of(HttpStatus.CREATED.value(), "회원가입 성공"));
    }
}

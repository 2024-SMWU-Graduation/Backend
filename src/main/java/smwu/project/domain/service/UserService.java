package smwu.project.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import smwu.project.domain.Repository.UserRepository;
import smwu.project.domain.dto.request.SignUpRequestDto;
import smwu.project.domain.entity.User;
import smwu.project.domain.enums.UserRole;
import smwu.project.domain.enums.UserStatus;
import smwu.project.global.exception.CustomException;
import smwu.project.global.exception.UserErrorCode;

@Service
@RequiredArgsConstructor
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Transactional
    public void signup(SignUpRequestDto requestDto) {
        checkEmailExists(requestDto.getEmail());

        User newUser = User.builder()
                .email(requestDto.getEmail())
                .password(passwordEncoder.encode(requestDto.getPassword()))
                .name(requestDto.getName())
                .userStatus(UserStatus.ACTIVATE)
                .userRole(UserRole.USER)
                .build();

        userRepository.save(newUser);
    }

    private void checkEmailExists(String email) {
        if(userRepository.existsByEmail(email))
            throw new CustomException(UserErrorCode.EMAIL_ALREADY_EXISTS);
    }
}

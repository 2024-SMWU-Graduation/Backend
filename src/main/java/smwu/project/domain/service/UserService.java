package smwu.project.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import smwu.project.domain.Repository.UserRepository;
import smwu.project.domain.dto.request.EditPasswordRequestDto;
import smwu.project.domain.dto.request.SignUpRequestDto;
import smwu.project.domain.dto.response.UserInfoResponseDto;
import smwu.project.domain.entity.User;
import smwu.project.domain.enums.UserRole;
import smwu.project.domain.enums.UserStatus;
import smwu.project.global.exception.CustomException;
import smwu.project.global.exception.UserErrorCode;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
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

    public UserInfoResponseDto readUserInfo(User user) {
        return UserInfoResponseDto.of(user);
    }

    @Transactional
    public void editPassword(User user, EditPasswordRequestDto requestDto) {
        String currentPassword = user.getPassword();
        String inputPassword = requestDto.getPassword();
        String newPassword = requestDto.getNewPassword();

        checkPasswordMatch(currentPassword, inputPassword);
        checkPasswordUnchanged(currentPassword, newPassword);

        String newEncodedPassword = passwordEncoder.encode(requestDto.getNewPassword());
        user.editPassword(newEncodedPassword);
        userRepository.save(user);
    }

    private void checkEmailExists(String email) {
        if(userRepository.existsByEmail(email))
            throw new CustomException(UserErrorCode.EMAIL_ALREADY_EXISTS);
    }

    private void checkPasswordMatch(String realPassword, String inputPassword) {
        if(!passwordEncoder.matches(inputPassword, realPassword)) { // 인코딩 이전 값, 인코딩 이후 값
            throw new CustomException(UserErrorCode.PASSWORD_MISMATCH);
        }
    }

    private void checkPasswordUnchanged(String currentPassword, String newPassword) {
        if(passwordEncoder.matches(newPassword, currentPassword)) {
            throw new CustomException(UserErrorCode.PASSWORD_SAME_AS_OLD);
        }
    }
}

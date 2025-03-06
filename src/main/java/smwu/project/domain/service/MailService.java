package smwu.project.domain.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import smwu.project.domain.entity.User;
import smwu.project.domain.repository.MailRepository;
import smwu.project.domain.repository.UserRepository;
import smwu.project.domain.dto.request.EmailRequestDto;
import smwu.project.domain.dto.request.VerificationCodeRequestDto;
import smwu.project.global.exception.CustomException;
import smwu.project.global.exception.errorCode.MailErrorCode;
import smwu.project.global.exception.errorCode.UserErrorCode;
import smwu.project.global.util.StringGenerator;

import java.security.SecureRandom;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class MailService {
    private final PasswordEncoder passwordEncoder;
    private final JavaMailSender javaMailSender;
    private final UserRepository userRepository;
    private final MailRepository mailRepository;

    public void verifyMail(EmailRequestDto requestDto) {
        userRepository.checkEmailExists(requestDto.getEmail());
    }

    @Transactional
    public void sendVerificationMail(EmailRequestDto requestDto) {
        String email = requestDto.getEmail();
        String verificationCode = StringGenerator.generateRandomNumberString(6);
        mailRepository.saveVerificationCode(email, verificationCode);

        MimeMessage message = setMailMessage(email, verificationCode);
        javaMailSender.send(message);
    }

    @Transactional
    public void sendTempPassword(EmailRequestDto requestDto) {
        String email = requestDto.getEmail();
        User user = userRepository.findByEmailOrElseThrow(email);

        String tempPassword = StringGenerator.generateRandomString(15);
        MimeMessage message = setTempPasswordMessage(email, tempPassword);
        javaMailSender.send(message);

        user.editPassword(passwordEncoder.encode(tempPassword));
    }

    public void validateVerificationCode(VerificationCodeRequestDto requestDto) {
        String email = requestDto.getEmail();
        String verificationCode = requestDto.getVerificationCode();

        if(!verificationCode.equals(mailRepository.getVerificationCode(email))) {
            throw new CustomException(MailErrorCode.INVALID_VERIFICATION_CODE);
        }

        mailRepository.removeVerificationCode(email);
    }

    private MimeMessage setMailMessage(String email, String verificationCode) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "utf-8");

            String content =
                    "회원 가입을 위해 이메일 인증을 완료해주세요!" +
                    "<br><br>" +
                    "인증 코드는 " + verificationCode + " 입니다." +
                    "<br><br>" +
                    "인증 코드란에 위 코드를 입력해주세요.";

            messageHelper.setTo(email);
            messageHelper.setSubject("[EGTree] 이메일 인증을 완료해주세요.");
            messageHelper.setText(content, true);
            return message;
        } catch (MessagingException e) {
            throw new CustomException(MailErrorCode.INVALID_MAIL_SETTING);
        }
    }

    private MimeMessage setTempPasswordMessage(String email, String tempPassword) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "utf-8");

            String content =
                    "임시 비밀번호가 발급되었습니다." +
                            "<br><br>" +
                            "비밀번호는 " + tempPassword + " 입니다." +
                            "<br><br>" +
                            "임시 비밀번호를 사용해 로그인한 후, 비밀번호 변경을 진행해주세요.";

            messageHelper.setTo(email);
            messageHelper.setSubject("[EGTree] 임시 비밀번호가 발급되었습니다.");
            messageHelper.setText(content, true);
            return message;
        } catch (MessagingException e) {
            throw new CustomException(MailErrorCode.INVALID_MAIL_SETTING);
        }
    }
}

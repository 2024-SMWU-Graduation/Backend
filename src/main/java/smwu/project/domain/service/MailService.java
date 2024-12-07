package smwu.project.domain.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import smwu.project.domain.repository.MailRepository;
import smwu.project.domain.repository.UserRepository;
import smwu.project.domain.dto.request.EmailRequestDto;
import smwu.project.domain.dto.request.VerificationCodeRequestDto;
import smwu.project.global.exception.CustomException;
import smwu.project.global.exception.errorCode.MailErrorCode;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class MailService {
    private final JavaMailSender javaMailSender;
    private final UserRepository userRepository;
    private final MailRepository mailRepository;

    public void verifyMail(EmailRequestDto requestDto) {
        if(userRepository.existsByEmail(requestDto.getEmail())) {
            throw new CustomException(MailErrorCode.EMAIL_ALREADY_EXISTS);
        }
    }

    @Transactional
    public void sendCertificationMail(EmailRequestDto requestDto) {
        String email = requestDto.getEmail();
        String verificationCode = createCertificationCode();
        mailRepository.saveVerificationCode(email, verificationCode);

        MimeMessage message = setMailMessage(email, verificationCode);
        javaMailSender.send(message);
    }

    public void validateVerificationCode(VerificationCodeRequestDto requestDto) {
        String email = requestDto.getEmail();
        String verificationCode = requestDto.getVerificationCode();

        if(!verificationCode.equals(mailRepository.getVerificationCode(email))) {
            throw new CustomException(MailErrorCode.INVALID_VERIFICATION_CODE);
        }

        mailRepository.removeVerificationCode(email);
    }

    private String createCertificationCode() {
        Random random = new Random();
        StringBuilder certificationCode = new StringBuilder();

        for (int i = 0; i < 6; i++) {
            String randomNumber = Integer.toString(random.nextInt(10));
            certificationCode.append(randomNumber);
        }
        return certificationCode.toString();
    }

    private MimeMessage setMailMessage(String email, String verificationCode) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "utf-8");

            String content =
                    "회원 가입을 위해 이메일 인증을 완료해주세요!" +
                    "<br><br>" +
                    "인증 코드는 " + verificationCode + "입니다." +
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
}

package smwu.project.domain.Repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import smwu.project.global.exception.CustomException;
import smwu.project.global.exception.MailErrorCode;

import java.time.Duration;

@Repository
@RequiredArgsConstructor
public class MailRepository {
    private final RedisTemplate<String, Object> redisTemplate;

    private static final String EMAIL_PREFIX = "email verification : ";
    private static final int EMAIL_VERIFICATION_LIMIT_IN_SECONDS = 300;

    public void saveVerificationCode(String email, String verificationCode) {
        String key = createEmailPrefix(email);
        redisTemplate.opsForValue()
                .set(key, verificationCode, Duration.ofSeconds(EMAIL_VERIFICATION_LIMIT_IN_SECONDS));
    }

    public String getVerificationCode(String email) {
        String verificationCode = (String) redisTemplate.opsForValue().get(createEmailPrefix(email));
        if(verificationCode == null) {
            throw new CustomException(MailErrorCode.VERIFICATION_CODE_NOT_FOUND);
        }
        return verificationCode;
    }

    public void removeVerificationCode(String email) {
        redisTemplate.delete(createEmailPrefix(email));
    }

    private String createEmailPrefix(String email) {
        return EMAIL_PREFIX + email;
    }
}

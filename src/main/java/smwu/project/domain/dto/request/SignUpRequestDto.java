package smwu.project.domain.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import smwu.project.domain.enums.UserRole;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SignUpRequestDto {
    @NotBlank(message = "이메일은 필수값입니다.")
    @Email(message = "이메일 형식에 맞게 입력해주세요.")
    private String email;

    @NotBlank(message = "비밀번호는 필수값입니다.")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d\\W]{8,15}$", message = "비밀번호는 8-15자, 숫자와 알파벳을 포함해야 합니다.")
    @Size(min = 8, max = 15)
    private String password;

    @NotBlank(message = "이름은 필수값입니다.")
    @Size(min = 1, max = 100)
    private String name;

    private UserRole userRole;
}

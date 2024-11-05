package smwu.project.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import smwu.project.domain.enums.UserRole;
import smwu.project.domain.enums.UserStatus;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Size(min = 1, max = 100)
    private String name;

    @NotBlank
    @Size(min = 8, max = 30)
    private String password;

    @NotBlank
    @Enumerated(EnumType.STRING)
    private UserStatus userStatus;

    @NotBlank
    @Enumerated(EnumType.STRING)
    private UserRole userRole;
}

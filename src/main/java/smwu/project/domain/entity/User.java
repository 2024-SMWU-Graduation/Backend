package smwu.project.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import smwu.project.domain.enums.UserStatus;

@Entity
@Getter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Email
    private String email;

    @Size(min = 1, max = 100)
    private String name;

    @Size(min = 8, max = 30)
    private String password;

    @Enumerated(EnumType.STRING)
    private UserStatus userStatus;
}

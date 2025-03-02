package smwu.project.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import smwu.project.domain.enums.InterviewStatus;

import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RandomInterview extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "randomInterview", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RandomQuestion> randomQuestions;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private InterviewStatus interviewStatus;
}

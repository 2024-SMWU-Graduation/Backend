package smwu.project.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import smwu.project.domain.enums.InterviewStatus;


@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class IntroduceInterview extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToOne(mappedBy = "introduceInterview", cascade = CascadeType.ALL, orphanRemoval = true)
    private IntroduceFeedback introduceFeedback;

    @Setter
    private String videoUrl;

    @Setter
    @Column(nullable = false)
    private String title;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private InterviewStatus interviewStatus;

    public void updateInterviewStatusToCompleted() {
        this.interviewStatus = InterviewStatus.COMPLETED;
    }
}

package smwu.project.domain.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class IntroduceFeedback extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "introduce_interview_id")
    private IntroduceInterview introduceInterview;

    @Column(nullable = false)
    private float negativePercentage;

    @Column(nullable = false, length = 500)
    private String timelines;
}

package smwu.project.domain.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RandomQuestion extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "random_interview_id", nullable = false)
    private RandomInterview randomInterview;

    @Setter
    @OneToOne(mappedBy = "randomQuestion", cascade = CascadeType.ALL, orphanRemoval = true)
    private RandomFeedback randomFeedback;

    private String questionData;

    @Setter
    private String videoUrl;
}

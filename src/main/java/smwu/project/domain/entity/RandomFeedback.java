package smwu.project.domain.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RandomFeedback extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "random_question_id", nullable = false)
    private RandomQuestion randomQuestion;

    @Column(nullable = false)
    private float negativePercentage;

    @Column(nullable = false, length = 500)
    private String timelines;

    @Setter
    private String analyzeUrl;
}

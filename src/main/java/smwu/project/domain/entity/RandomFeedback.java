package smwu.project.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RandomFeedback extends Feedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "random_question_id", nullable = false)
    private RandomQuestion randomQuestion;

    public RandomFeedback(RandomQuestion randomQuestion, float negativePercentage, List<FeedbackTimeline> timelines) {
        super(negativePercentage, timelines);
        this.randomQuestion = randomQuestion;
    }
}

package smwu.project.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class Feedback extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private float negativePercentage;

//    private String summary;

    @Setter
    private String analyzeUrl;

    @OneToMany(mappedBy = "feedback", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FeedbackTimeline> timelines = new ArrayList<>();

    public Feedback(float negativePercentage, List<FeedbackTimeline> timelines) {
        this.negativePercentage = negativePercentage;
        this.timelines = timelines;
        this.timelines.forEach(timeline -> timeline.setFeedback(this));
    }
}
package smwu.project.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class IntroduceFeedback extends Feedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "introduce_interview_id")
    private IntroduceInterview introduceInterview;

    public IntroduceFeedback(IntroduceInterview interview, float negativePercentage, List<FeedbackTimeline> feedbackTimelines) {
        super(negativePercentage, feedbackTimelines);
        this.introduceInterview = interview;
    }

//    public static IntroduceFeedback of(IntroduceInterview introduceInterview, IntroduceFeedbackRequestDto requestDto) {
//        return new IntroduceFeedback(
//                requestDto.getNegativePercentage(),
//        )
//    }
}

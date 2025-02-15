package smwu.project.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import smwu.project.domain.entity.RandomInterview;

@Getter
@AllArgsConstructor
public class CreateRandomInterviewResponseDto {
    private Long randomInterviewId;

    public static CreateRandomInterviewResponseDto of(RandomInterview randomInterview) {
        return new CreateRandomInterviewResponseDto(randomInterview.getId());
    }
}

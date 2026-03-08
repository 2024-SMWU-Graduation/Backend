package smwu.project.domain.dto.response;

import lombok.Builder;
import lombok.Getter;
import smwu.project.domain.entity.IntroduceInterview;

import java.util.List;

@Getter
@Builder
public class IntroduceInterviewListResponseDto {
    private int totalInterview;
    private List<IntroduceInterviewResponseDto> responseDtoList;

    public static IntroduceInterviewListResponseDto of(List<IntroduceInterview> interviewList) {
        return IntroduceInterviewListResponseDto.builder()
                .totalInterview(interviewList.size())
                .responseDtoList(interviewList.stream().map(IntroduceInterviewResponseDto::of).toList())
                .build();
    }
}

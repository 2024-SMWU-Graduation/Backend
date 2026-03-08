package smwu.project.domain.dto.response;

import lombok.Builder;
import lombok.Getter;
import smwu.project.domain.entity.RandomInterview;

import java.util.List;

@Getter
@Builder
public class RandomInterviewListResponseDto {
    private int totalInterview;
    private List<RandomInterviewResponseDto> responseDtoList;

    public static RandomInterviewListResponseDto of(List<RandomInterview> interviewList) {
        return RandomInterviewListResponseDto.builder()
                .totalInterview(interviewList.size())
                .responseDtoList(interviewList.stream().map(RandomInterviewResponseDto::of).toList())
                .build();
    }
}


package smwu.project.global.util;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FormatUtil {
    public static String parseDateTime(LocalDateTime date) {
        return date.getYear() + "." +
                date.getMonthValue() + "." +
                date.getDayOfMonth() + " " +
                date.getHour() + ":" +
                date.getMinute();
    }
    public static List<String> parseTimelines(String rawData) {
        // 대괄호 제거 및 공백 제거
        String cleanedData = rawData.replaceAll("[\\[\\]]", "").trim();

        // 쉼표 기준으로 분리
        String[] splitData = cleanedData.split(",\\s*");

        // 배열을 리스트로 변환
        return new ArrayList<>(Arrays.asList(splitData));
    }
}

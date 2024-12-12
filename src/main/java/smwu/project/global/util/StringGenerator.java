package smwu.project.global.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.security.SecureRandom;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StringGenerator {
    public static String generateRandomNumberString(int length) {
        SecureRandom secureRandom = new SecureRandom();
        StringBuilder numberString = new StringBuilder();

        for (int i = 0; i < length; i++) {
            String randomNumber = Integer.toString(secureRandom.nextInt(10));
            numberString.append(randomNumber);
        }
        return numberString.toString();
    }

    public static String generateRandomString(int length) {
        SecureRandom secureRandom = new SecureRandom();
        StringBuilder builder = new StringBuilder();

        String string = IntStream.rangeClosed(33, 126)
                .mapToObj(o -> String.valueOf((char) o))
                .collect(Collectors.joining());

        for (int i = 0; i < length; i++) {
            builder.append(string.charAt(secureRandom.nextInt(string.length())));
        }
        return builder.toString();
    }
}

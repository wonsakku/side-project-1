package com.study.member.domain;

import org.springframework.stereotype.Component;

@Component
public class DefaultPasswordValidationStrategy implements PasswordValidationStrategy {

    private static final int MIN_LENGTH = 4;
    private static final int MAX_LENGTH = 16;

    @Override
    public void validate(String rawPassword) {
        if (rawPassword == null || rawPassword.isBlank()) {
            throw new IllegalArgumentException("비밀번호는 필수입니다.");
        }

        if (rawPassword.length() < MIN_LENGTH || rawPassword.length() > MAX_LENGTH) {
            throw new IllegalArgumentException(
                    String.format("비밀번호는 %d자리 이상 %d자리 이하여야 합니다.", MIN_LENGTH, MAX_LENGTH));
        }

        if (!containsUpperCase(rawPassword)) {
            throw new IllegalArgumentException("비밀번호는 영어 대문자를 포함해야 합니다.");
        }

        if (!containsLowerCase(rawPassword)) {
            throw new IllegalArgumentException("비밀번호는 영어 소문자를 포함해야 합니다.");
        }

        if (!containsDigit(rawPassword)) {
            throw new IllegalArgumentException("비밀번호는 숫자를 포함해야 합니다.");
        }

        if (!containsSpecialCharacter(rawPassword)) {
            throw new IllegalArgumentException("비밀번호는 특수문자를 포함해야 합니다.");
        }
    }

    private boolean containsUpperCase(String password) {
        return password.chars().anyMatch(Character::isUpperCase);
    }

    private boolean containsLowerCase(String password) {
        return password.chars().anyMatch(Character::isLowerCase);
    }

    private boolean containsDigit(String password) {
        return password.chars().anyMatch(Character::isDigit);
    }

    private boolean containsSpecialCharacter(String password) {
        return password.chars().anyMatch(ch -> !Character.isLetterOrDigit(ch));
    }
}

package com.study.member.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class Email {

    private final String value;

    private Email(String value) {
        this.value = value;
    }

    public static Email of(String value) {
        validate(value);
        return new Email(value);
    }

    private static void validate(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("이메일은 필수입니다.");
        }

        int atIndex = value.indexOf('@');
        if (atIndex == -1) {
            throw new IllegalArgumentException("이메일 형식이 올바르지 않습니다. '@'가 포함되어야 합니다.");
        }

        if (atIndex == 0) {
            throw new IllegalArgumentException("이메일 형식이 올바르지 않습니다. '@' 앞에 문자열이 있어야 합니다.");
        }

        String domain = value.substring(atIndex + 1);
        if (domain.isBlank()) {
            throw new IllegalArgumentException("이메일 형식이 올바르지 않습니다. '@' 뒤에 문자열이 있어야 합니다.");
        }

        int dotIndex = domain.indexOf('.');
        if (dotIndex == -1) {
            throw new IllegalArgumentException("이메일 형식이 올바르지 않습니다. 도메인에 '.'가 포함되어야 합니다.");
        }

        long dotCount = domain.chars().filter(ch -> ch == '.').count();
        if (dotCount >= 2) {
            throw new IllegalArgumentException("이메일 형식이 올바르지 않습니다. 도메인에 '.'가 2개 이상 포함될 수 없습니다.");
        }

        String topLevelDomain = domain.substring(dotIndex + 1);
        if (topLevelDomain.isBlank()) {
            throw new IllegalArgumentException("이메일 형식이 올바르지 않습니다. '.' 뒤에 문자열이 있어야 합니다.");
        }
    }

    @Override
    public String toString() {
        return value;
    }
}

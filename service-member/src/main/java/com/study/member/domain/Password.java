package com.study.member.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class Password {

    private static final int MIN_LENGTH = 4;
    private static final int MAX_LENGTH = 16;

    private final String value;

    private Password(String value) {
        this.value = value;
    }

    public static Password of(String rawPassword, PasswordEncoderAdapter passwordEncoderAdapter) {
        validateLength(rawPassword);
        String encodedPassword = passwordEncoderAdapter.encode(rawPassword);
        return new Password(encodedPassword);
    }

    private static void validateLength(String rawPassword) {
        if (rawPassword == null || rawPassword.isBlank()) {
            throw new IllegalArgumentException("비밀번호는 필수입니다.");
        }

        if (rawPassword.length() < MIN_LENGTH || rawPassword.length() > MAX_LENGTH) {
            throw new IllegalArgumentException(
                    String.format("비밀번호는 %d자리 이상 %d자리 이하여야 합니다.", MIN_LENGTH, MAX_LENGTH));
        }
    }

    public void matches(String rawPassword, PasswordEncoderAdapter passwordEncoderAdapter) {
        if (!passwordEncoderAdapter.matches(rawPassword, this.value)) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
    }

    @Override
    public String toString() {
        return "[PROTECTED]";
    }
}

package com.study.member.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class Password {

    private final String value;

    // 내부에서만 생성 가능
    private Password(String value) {
        this.value = value;
    }

    /**
     * 이미 암호화된 문자열로부터 Password 객체를 생성합니다. (주로 DB 로드용)
     */
    public static Password from(String encryptedPassword) {
        if (encryptedPassword == null || encryptedPassword.isBlank()) {
            throw new IllegalArgumentException("비밀번호 값은 필수입니다.");
        }
        return new Password(encryptedPassword);
    }

    /**
     * 입력받은 생(Raw) 비밀번호가 현재 암호화된 값과 일치하는지 확인합니다.
     */
    public boolean matches(String rawPassword, PasswordEncoderAdapter encoder) {
        return encoder.matches(rawPassword, this.value);
    }

    @Override
    public String toString() {
        return "[PROTECTED]";
    }
}
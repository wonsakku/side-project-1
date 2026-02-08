package com.study.member.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static com.study.member.fixture.TestFixture.createPasswordEncoder;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.study.member.fixture.TestFixture.FakePasswordEncoder;

class PasswordTest {

    @Test
    @DisplayName("암호화된 비밀번호로 Password 객체를 생성한다")
    void createPassword_success() {
        // given
        String encryptedPassword = "encoded_password123";

        // when
        Password password = Password.from(encryptedPassword);

        // then
        assertThat(password.getValue()).isEqualTo(encryptedPassword);
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"  "})
    @DisplayName("비밀번호가 null이거나 빈 문자열이면 예외가 발생한다")
    void createPassword_nullOrBlank_throwsException(String encryptedPassword) {
        // when & then
        assertThatThrownBy(() -> Password.from(encryptedPassword))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("비밀번호 값은 필수입니다");
    }

    @Test
    @DisplayName("비밀번호가 일치하면 true를 반환한다")
    void matches_success() {
        // given
        FakePasswordEncoder encoder = createPasswordEncoder();
        encoder.setForcedMatchResult(true);
        Password password = Password.from("encoded_password123");

        // when
        boolean result = password.matches("rawPassword", encoder);

        // then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("비밀번호가 일치하지 않으면 false를 반환한다")
    void matches_notMatched() {
        // given
        FakePasswordEncoder encoder = createPasswordEncoder();
        encoder.setForcedMatchResult(false);
        Password password = Password.from("encoded_password123");

        // when
        boolean result = password.matches("wrongPassword", encoder);

        // then
        assertThat(result).isFalse();
    }
}

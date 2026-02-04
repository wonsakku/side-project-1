package com.study.member.domain;

import com.study.member.fixture.TestFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static com.study.member.fixture.TestFixture.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PasswordTest {

    @Test
    @DisplayName("정상적인 비밀번호로 Password 객체를 생성한다")
    void createPassword_success() {
        // given
        String rawPassword = "password123";
        String expectedResult = "any_encoded_value"; // 테스트에서 원하는 아무 값이나 설정
        FakePasswordEncoder encoder = createPasswordEncoder();
        encoder.setForcedMatchResult(true);
        encoder.setForcedEncodedValue(expectedResult);

        // when
        Password password = Password.of(rawPassword, encoder);

        // then
        assertThat(password.getValue()).isEqualTo(expectedResult);
    }

    @ParameterizedTest
    @NullAndEmptySource // null 과 "" 값 둘 다 넣어줌
    @ValueSource(strings = {"  "})
    @DisplayName("비밀번호가 null이거나 빈 문자열이면 예외가 발생한다")
    void createPassword_nullOrBlank_throwsException(String rawPassword) { // rawPassword에 null, "", " " 케이스 모두 테스트
        // given
        FakePasswordEncoder encoder = createPasswordEncoder();

        // when & then
        assertThatThrownBy(() -> Password.of(rawPassword, encoder))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("비밀번호는 필수입니다");
    }

    @ParameterizedTest
    @ValueSource(strings = {"1", "12", "123", "12345678901234567", "123456789012345678"})
    @DisplayName("비밀번호가 4자리 미만이거나 16자리 초과일 경우 예외가 발생한다")
    void createPassword_invalidLength_throwsException(String rawPassword) {
        // given
        FakePasswordEncoder encoder = createPasswordEncoder();
        encoder.setForcedMatchResult(true);

        // when & then
        assertThatThrownBy(() -> Password.of(rawPassword, encoder))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("4자리 이상 16자리 이하");
    }

    @Test
    @DisplayName("비밀번호가 일치하면 예외가 발생하지 않는다")
    void matches_success() {
        // given
        String rawPassword = "password123";
        FakePasswordEncoder encoder = createPasswordEncoder();
        encoder.setForcedMatchResult(true);
        Password password = Password.of(rawPassword, encoder);

        // when & then
        assertThatCode(() -> password.matches(rawPassword, encoder))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("비밀번호가 일치하지 않으면 예외가 발생한다")
    void matches_notMatched_throwsException() {
        // given
        String rawPassword = "password123";
        FakePasswordEncoder encoder = createPasswordEncoder();
        encoder.setForcedMatchResult(false);
        Password password = Password.of(rawPassword, encoder);

        // when & then
        assertThatThrownBy(() -> password.matches("wrongPassword", encoder))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("비밀번호가 일치하지 않습니다");
    }
}

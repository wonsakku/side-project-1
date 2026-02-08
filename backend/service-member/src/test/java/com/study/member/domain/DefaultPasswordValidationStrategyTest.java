package com.study.member.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class DefaultPasswordValidationStrategyTest {

    private final DefaultPasswordValidationStrategy strategy = new DefaultPasswordValidationStrategy();

    @Test
    @DisplayName("모든 조건을 충족하는 비밀번호는 검증을 통과한다")
    void validate_success() {
        assertThatCode(() -> strategy.validate("aB1!"))
                .doesNotThrowAnyException();
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"  "})
    @DisplayName("비밀번호가 null이거나 빈 문자열이면 예외가 발생한다")
    void validate_nullOrBlank_throwsException(String rawPassword) {
        assertThatThrownBy(() -> strategy.validate(rawPassword))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("비밀번호는 필수입니다");
    }

    @ParameterizedTest
    @ValueSource(strings = {"aB!", "aB1!56789012345678"})
    @DisplayName("비밀번호가 4자리 미만이거나 16자리 초과이면 예외가 발생한다")
    void validate_invalidLength_throwsException(String rawPassword) {
        assertThatThrownBy(() -> strategy.validate(rawPassword))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("4자리 이상 16자리 이하");
    }

    @Test
    @DisplayName("영어 대문자가 없으면 예외가 발생한다")
    void validate_noUpperCase_throwsException() {
        assertThatThrownBy(() -> strategy.validate("ab1!"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("영어 대문자를 포함해야 합니다");
    }

    @Test
    @DisplayName("영어 소문자가 없으면 예외가 발생한다")
    void validate_noLowerCase_throwsException() {
        assertThatThrownBy(() -> strategy.validate("AB1!"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("영어 소문자를 포함해야 합니다");
    }

    @Test
    @DisplayName("숫자가 없으면 예외가 발생한다")
    void validate_noDigit_throwsException() {
        assertThatThrownBy(() -> strategy.validate("aBc!"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("숫자를 포함해야 합니다");
    }

    @Test
    @DisplayName("특수문자가 없으면 예외가 발생한다")
    void validate_noSpecialCharacter_throwsException() {
        assertThatThrownBy(() -> strategy.validate("aBc1"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("특수문자를 포함해야 합니다");
    }
}

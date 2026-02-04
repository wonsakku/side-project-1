package com.study.member.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class EmailTest {

    @Test
    @DisplayName("정상적인 이메일로 Email 객체를 생성한다")
    void createEmail_success() {
        // given
        String validEmail = "test@gmail.com";

        // when
        Email email = Email.of(validEmail);

        // then
        assertThat(email.getValue()).isEqualTo(validEmail);
    }

    @Test
    @DisplayName("@가 없는 경우 예외가 발생한다")
    void createEmail_withoutAt_throwsException() {
        // given
        String invalidEmail = "testgmail.com";

        // when & then
        assertThatThrownBy(() -> Email.of(invalidEmail))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("'@'가 포함되어야 합니다");
    }

    @Test
    @DisplayName("@가 제일 앞에 있는 경우 예외가 발생한다")
    void createEmail_atAtStart_throwsException() {
        // given
        String invalidEmail = "@gmail.com";

        // when & then
        assertThatThrownBy(() -> Email.of(invalidEmail))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("'@' 앞에 문자열이 있어야 합니다");
    }

    @Test
    @DisplayName("@ 뒤에 문자가 없는 경우 예외가 발생한다")
    void createEmail_nothingAfterAt_throwsException() {
        // given
        String invalidEmail = "test@";

        // when & then
        assertThatThrownBy(() -> Email.of(invalidEmail))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("'@' 뒤에 문자열이 있어야 합니다");
    }

    @Test
    @DisplayName("@ 뒤에 .이 없는 경우 예외가 발생한다")
    void createEmail_noDotInDomain_throwsException() {
        // given
        String invalidEmail = "test@gmailcom";

        // when & then
        assertThatThrownBy(() -> Email.of(invalidEmail))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("도메인에 '.'가 포함되어야 합니다");
    }

    @Test
    @DisplayName("@ 뒤에 .이 2개 이상인 경우 예외가 발생한다")
    void createEmail_multipleDots_throwsException() {
        // given
        String invalidEmail = "test@gmail.co.kr";

        // when & then
        assertThatThrownBy(() -> Email.of(invalidEmail))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("도메인에 '.'가 2개 이상 포함될 수 없습니다");
    }

    @Test
    @DisplayName(". 뒤에 문자열이 없는 경우 예외가 발생한다")
    void createEmail_nothingAfterDot_throwsException() {
        // given
        String invalidEmail = "test@gmail.";

        // when & then
        assertThatThrownBy(() -> Email.of(invalidEmail))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("'.' 뒤에 문자열이 있어야 합니다");
    }
}

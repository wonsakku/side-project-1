package com.study.member.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDateTime;
import java.util.stream.Stream;

import static com.study.member.fixture.TestFixture.createPasswordEncoder;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MemberTest {

    private static final Email VALID_EMAIL = Email.of("test@gmail.com");
    private static final String VALID_NAME = "홍길동";
    private static final UserRole VALID_ROLE = UserRole.USER;
    private static final LocalDateTime VALID_CREATED_AT = LocalDateTime.of(2024, 1, 1, 0, 0, 0);
    private static final LocalDateTime VALID_UPDATED_AT = LocalDateTime.of(2024, 1, 1, 0, 0, 0);

    @Nested
    @DisplayName("Member 생성 테스트")
    class CreateMemberTest {

        @Test
        @DisplayName("Member를 정상적으로 생성한다")
        void createMember_success() {
            // given
            Email email = Email.of("test@gmail.com");
            Password password = Password.of("password123", createPasswordEncoder());
            String name = "홍길동";
            UserRole userRole = UserRole.USER;
            LocalDateTime createdAt = LocalDateTime.of(2024, 1, 1, 0, 0, 0);
            LocalDateTime updatedAt = LocalDateTime.of(2024, 1, 1, 0, 0, 0);

            // when
            Member member = Member.create(email, password, name, userRole, createdAt, updatedAt);

            // then
            assertThat(member.getId()).isNull();
            assertThat(member.getEmail()).isEqualTo(email);
            assertThat(member.getPassword()).isEqualTo(password);
            assertThat(member.getName()).isEqualTo(name);
            assertThat(member.getRole()).isEqualTo(userRole);
            assertThat(member.getCreatedAt()).isEqualTo(createdAt);
            assertThat(member.getUpdatedAt()).isEqualTo(updatedAt);
        }

        @ParameterizedTest(name = "{0}")
        @MethodSource("com.study.member.domain.MemberTest#provideInvalidMemberArguments")
        @DisplayName("필수 값이 누락되면 예외가 발생한다")
        void createMember_invalidArgs_throwsException(String description, Email email, Password password,
                                                      String name, UserRole role, LocalDateTime createdAt,
                                                      LocalDateTime updatedAt, String expectedMessage) {
            assertThatThrownBy(() -> Member.create(email, password, name, role, createdAt, updatedAt))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining(expectedMessage);
        }
    }

    private static Stream<Arguments> provideInvalidMemberArguments() {
        Password validPassword = Password.of("password123", createPasswordEncoder());
        return Stream.of(
                Arguments.of("이메일 null", null, validPassword, VALID_NAME, VALID_ROLE, VALID_CREATED_AT, VALID_UPDATED_AT, "이메일은 필수"),
                Arguments.of("비밀번호 null", VALID_EMAIL, null, VALID_NAME, VALID_ROLE, VALID_CREATED_AT, VALID_UPDATED_AT, "비밀번호는 필수"),
                Arguments.of("이름 null", VALID_EMAIL, validPassword, null, VALID_ROLE, VALID_CREATED_AT, VALID_UPDATED_AT, "이름은 필수"),
                Arguments.of("이름 빈값", VALID_EMAIL, validPassword, "  ", VALID_ROLE, VALID_CREATED_AT, VALID_UPDATED_AT, "이름은 필수"),
                Arguments.of("권한 null", VALID_EMAIL, validPassword, VALID_NAME, null, VALID_CREATED_AT, VALID_UPDATED_AT, "권한은 필수"),
                Arguments.of("생성일시 null", VALID_EMAIL, validPassword, VALID_NAME, VALID_ROLE, null, VALID_UPDATED_AT, "생성일시는 필수"),
                Arguments.of("수정일시 null", VALID_EMAIL, validPassword, VALID_NAME, VALID_ROLE, VALID_CREATED_AT, null, "수정일시는 필수")
        );
    }

    @Nested
    @DisplayName("Member 수정 테스트")
    class UpdateMemberTest {

        @Test
        @DisplayName("이메일을 변경한다")
        void changeEmail_success() {
            // given
            Member member = createMember();
            Email changedEmail = Email.of("changed@gmail.com");
            LocalDateTime updatedDateTime = LocalDateTime.of(2024, 2, 1, 0, 0, 0);

            // when
            member.changeEmail(changedEmail, updatedDateTime);

            // then
            assertThat(member.getEmail()).isEqualTo(changedEmail);
            assertThat(member.getUpdatedAt()).isEqualTo(updatedDateTime);
        }

        @Test
        @DisplayName("비밀번호를 변경한다")
        void changePassword_success() {
            // given
            Member member = createMember();
            Password changedPassword = Password.of("newPassword1", createPasswordEncoder());
            LocalDateTime updatedDateTime = LocalDateTime.of(2024, 2, 1, 0, 0, 0);

            // when
            member.changePassword(changedPassword, updatedDateTime);

            // then
            assertThat(member.getPassword()).isEqualTo(changedPassword);
            assertThat(member.getUpdatedAt()).isEqualTo(updatedDateTime);
        }
    }

    private Member createMember() {
        Email email = Email.of("test@gmail.com");
        Password password = Password.of("password123", createPasswordEncoder());
        String name = "홍길동";
        UserRole userRole = UserRole.USER;
        LocalDateTime createdAt = LocalDateTime.of(2024, 1, 1, 0, 0, 0);
        LocalDateTime updatedAt = LocalDateTime.of(2024, 1, 1, 0, 0, 0);

        return Member.create(email, password, name, userRole, createdAt, updatedAt);
    }
}

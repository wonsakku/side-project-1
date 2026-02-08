package com.study.member.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    private MemberId id;
    private Email email;
    private Password password;
    private String name;
    private UserRole role;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private Member(MemberId id, Email email, Password password, String name, UserRole role,
                   LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.role = role;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static Member create(Email email, Password password, String name, UserRole userRole,
                                LocalDateTime createdAt, LocalDateTime updatedAt) {
        validateForCreate(email, password, name, userRole, createdAt, updatedAt);
        return new Member(null, email, password, name, userRole, createdAt, updatedAt);
    }

    private static void validateForCreate(Email email, Password password, String name, UserRole userRole,
                                          LocalDateTime createdAt, LocalDateTime updatedAt) {
        if (Objects.isNull(email)) {
            throw new IllegalArgumentException("이메일은 필수입니다.");
        }
        if (Objects.isNull(password)) {
            throw new IllegalArgumentException("비밀번호는 필수입니다.");
        }
        if (Objects.isNull(name) || name.isBlank()) {
            throw new IllegalArgumentException("이름은 필수입니다.");
        }
        if (Objects.isNull(userRole)) {
            throw new IllegalArgumentException("권한은 필수입니다.");
        }
        if (Objects.isNull(createdAt)) {
            throw new IllegalArgumentException("생성일시는 필수입니다.");
        }
        if (Objects.isNull(updatedAt)) {
            throw new IllegalArgumentException("수정일시는 필수입니다.");
        }
    }

    public static Member of(Long id, Email email, Password password, String name, UserRole userRole,
                            LocalDateTime createdAt, LocalDateTime updatedAt) {
        return new Member(MemberId.of(id), email, password, name, userRole, createdAt, updatedAt);
    }

    public void changePassword(Password changedPassword, LocalDateTime updatedDateTime) {
        this.password = changedPassword;
        this.updatedAt = updatedDateTime;
    }

    public void changeEmail(Email changedEmail, LocalDateTime updatedDateTime) {
        this.email = changedEmail;
        this.updatedAt = updatedDateTime;
    }
}

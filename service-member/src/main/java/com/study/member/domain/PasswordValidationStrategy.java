package com.study.member.domain;

public interface PasswordValidationStrategy {

    void validate(String rawPassword);
}

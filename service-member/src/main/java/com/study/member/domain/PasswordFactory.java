package com.study.member.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PasswordFactory {

    private final PasswordValidationStrategy passwordValidationStrategy;
    private final PasswordEncoderAdapter passwordEncoderAdapter;

    public Password create(String rawPassword) {
        passwordValidationStrategy.validate(rawPassword);
        String encodedPassword = passwordEncoderAdapter.encode(rawPassword);
        return Password.from(encodedPassword);
    }
}

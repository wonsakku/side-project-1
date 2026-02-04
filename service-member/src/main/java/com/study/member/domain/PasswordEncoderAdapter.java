package com.study.member.domain;

public interface PasswordEncoderAdapter {

    String encode(String rawPassword);

    boolean matches(String rawPassword, String encodedPassword);
}

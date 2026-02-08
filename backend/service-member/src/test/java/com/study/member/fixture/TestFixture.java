package com.study.member.fixture;

import com.study.member.domain.PasswordEncoderAdapter;

public class TestFixture {

    /**
     * 기본 기능을 가진 가짜 인코더 클래스
     */
    public static class FakePasswordEncoder implements PasswordEncoderAdapter {
        private final String prefix = "encoded_";
        private String forcedEncodedValue = null; // encode 결과를 강제하기 위한 필드
        private Boolean forcedMatchResult = null;

        @Override
        public String encode(String rawPassword) {
            return forcedEncodedValue != null ? forcedEncodedValue : prefix + rawPassword;
        }

        @Override
        public boolean matches(String rawPassword, String encodedPassword) {
            // 강제 결과값이 설정되어 있으면 그 값을 반환 (PasswordTest의 실패 케이스 대응)
            if (forcedMatchResult != null) {
                return forcedMatchResult;
            }
            // 기본 로직: prefix가 붙어있는지 확인 (MemberTest 대응)
            return encodedPassword.equals(prefix + rawPassword);
        }

        // 테스트에서 결과를 강제하고 싶을 때 사용
        public void setForcedMatchResult(boolean result) {
            this.forcedMatchResult = result;
        }

        // encode 결과를 강제하는 메서드
        public void setForcedEncodedValue(String value) {
            this.forcedEncodedValue = value;
        }
    }

    // 팩토리 메서드: 기본 가짜 인코더 반환
    public static FakePasswordEncoder createPasswordEncoder() {
        return new FakePasswordEncoder();
    }
}

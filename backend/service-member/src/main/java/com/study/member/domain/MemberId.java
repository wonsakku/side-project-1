package com.study.member.domain;

import com.study.common.core.domain.Identifier;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class MemberId implements Identifier<Long> {

    private final Long value;

    private MemberId(Long value) {
        this.value = value;
    }

    public static MemberId of(Long value) {
        if (value == null) {
            return null;
        }
        return new MemberId(value);
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}

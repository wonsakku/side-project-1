package com.study.content.domain.vod;

import com.study.common.core.domain.Identifier;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class VodId implements Identifier<Long> {

    private final Long value;

    private VodId(Long value) {
        this.value = value;
    }

    public static VodId of(Long value) {
        if (value == null) {
            return null;
        }
        return new VodId(value);
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}

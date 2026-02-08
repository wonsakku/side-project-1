package com.study.content.domain.vod;

import com.study.common.core.domain.Identifier;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class VodGroupId implements Identifier<Long> {

    private final Long value;

    private VodGroupId(Long value) {
        this.value = value;
    }

    public static VodGroupId of(Long value) {
        if (value == null) {
            return null;
        }
        return new VodGroupId(value);
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}

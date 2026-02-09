package com.study.content.domain.content;

import com.study.common.core.domain.Identifier;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class ContentGroupId implements Identifier<Long> {

    private final Long value;

    private ContentGroupId(Long value) {
        this.value = value;
    }

    public static ContentGroupId of(Long value) {
        if (value == null) {
            return null;
        }
        return new ContentGroupId(value);
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}

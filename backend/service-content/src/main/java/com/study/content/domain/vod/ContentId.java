package com.study.content.domain.vod;

import com.study.common.core.domain.Identifier;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class ContentId implements Identifier<Long> {

    private final Long value;

    private ContentId(Long value) {
        this.value = value;
    }

    public static ContentId of(Long value) {
        if (value == null) {
            return null;
        }
        return new ContentId(value);
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}

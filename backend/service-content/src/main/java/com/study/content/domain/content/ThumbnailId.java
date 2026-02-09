package com.study.content.domain.content;

import com.study.common.core.domain.Identifier;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class ThumbnailId implements Identifier<Long> {

    private final Long value;

    private ThumbnailId(Long value) {
        this.value = value;
    }

    public static ThumbnailId of(Long value) {
        if (value == null) {
            return null;
        }
        return new ThumbnailId(value);
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}

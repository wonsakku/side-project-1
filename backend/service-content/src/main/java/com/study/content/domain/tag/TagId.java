package com.study.content.domain.tag;

import com.study.common.core.domain.Identifier;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class TagId implements Identifier<Long> {

    private final Long value;

    private TagId(Long value) {
        this.value = value;
    }

    public static TagId of(Long value) {
        if (value == null) {
            return null;
        }
        return new TagId(value);
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}

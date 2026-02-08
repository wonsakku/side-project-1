package com.study.content.domain.vod;

import com.study.common.core.domain.Identifier;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class UserWatchHistoryId implements Identifier<Long> {

    private final Long value;

    private UserWatchHistoryId(Long value) {
        this.value = value;
    }

    public static UserWatchHistoryId of(Long value) {
        if (value == null) {
            return null;
        }
        return new UserWatchHistoryId(value);
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}

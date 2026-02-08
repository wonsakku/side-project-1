package com.study.content.domain.vod;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserWatchHistory {

    private UserWatchHistoryId id;
    private VodId vodId;
    private Long userId;
    private int endPlaySecond;

    private UserWatchHistory(UserWatchHistoryId id, VodId vodId, Long userId, int endPlaySecond) {
        this.id = id;
        this.vodId = vodId;
        this.userId = userId;
        this.endPlaySecond = endPlaySecond;
    }

    public static UserWatchHistory create(VodId vodId, Long userId, int endPlaySecond) {
        validateForCreate(vodId, userId,  endPlaySecond);
        return new UserWatchHistory(null, vodId, userId,  endPlaySecond);
    }

    private static void validateForCreate(VodId vodId, Long userId, int endPlaySecond) {
        if (Objects.isNull(vodId)) {
            throw new IllegalArgumentException("VOD ID는 필수입니다.");
        }
        if (Objects.isNull(userId)) {
            throw new IllegalArgumentException("사용자 ID는 필수입니다.");
        }
        if (endPlaySecond < 0) {
            throw new IllegalArgumentException("종료 재생 시간은 0 이상이어야 합니다.");
        }
    }

    public static UserWatchHistory of(Long id, Long vodId, Long userId, int endPlaySecond) {
        return new UserWatchHistory(UserWatchHistoryId.of(id), VodId.of(vodId), userId, endPlaySecond);
    }

    public void updatePlayPosition(int endPlaySecond) {
        this.endPlaySecond = endPlaySecond;
    }
}

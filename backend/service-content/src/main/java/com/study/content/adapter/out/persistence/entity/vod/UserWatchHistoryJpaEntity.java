package com.study.content.adapter.out.persistence.entity.vod;

import com.study.content.domain.vod.UserWatchHistory;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "user_watch_history")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserWatchHistoryJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_watch_history_id")
    private Long id;

    @Column(name = "vod_id", nullable = false)
    private Long vodId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "end_play_second", nullable = false)
    private int endPlaySecond;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "modified_at")
    private LocalDateTime modifiedAt;

    private UserWatchHistoryJpaEntity(Long id, Long vodId, Long userId, int endPlaySecond,
                                     LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.id = id;
        this.vodId = vodId;
        this.userId = userId;
        this.endPlaySecond = endPlaySecond;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    public static UserWatchHistoryJpaEntity from(UserWatchHistory domain) {
        return new UserWatchHistoryJpaEntity(
                domain.getId() != null ? domain.getId().getValue() : null,
                domain.getVodId() != null ? domain.getVodId().getValue() : null,
                domain.getUserId(),
                domain.getEndPlaySecond(),
                domain.getCreatedAt(),
                domain.getModifiedAt()
        );
    }

    public UserWatchHistory toDomain() {
        return UserWatchHistory.of(id, vodId, userId, endPlaySecond, createdAt, modifiedAt);
    }
}

package com.study.content.adapter.out.persistence.entity.vod;

import com.study.content.domain.vod.Vod;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "vod")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class VodJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vod_id")
    private Long id;

    @Column(name = "content_id", nullable = false)
    private Long contentId;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "vod_url", nullable = false)
    private String vodUrl;

    @Column(name = "running_time_second", nullable = false)
    private int runningTimeSecond;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "modified_at")
    private LocalDateTime modifiedAt;

    private VodJpaEntity(Long id, Long contentId, String title, String description, String vodUrl,
                         int runningTimeSecond, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.id = id;
        this.contentId = contentId;
        this.title = title;
        this.description = description;
        this.vodUrl = vodUrl;
        this.runningTimeSecond = runningTimeSecond;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    public static VodJpaEntity from(Vod domain) {
        return new VodJpaEntity(
                domain.getId() != null ? domain.getId().getValue() : null,
                domain.getContentId() != null ? domain.getContentId().getValue() : null,
                domain.getTitle(),
                domain.getDescription(),
                domain.getVodUrl(),
                domain.getRunningTime(),
                domain.getCreatedAt(),
                domain.getModifiedAt()
        );
    }

    public Vod toDomain() {
        return Vod.of(id, contentId, title, description, vodUrl, runningTimeSecond, createdAt, modifiedAt);
    }
}

package com.study.content.adapter.out.persistence.entity.content;

import com.study.content.domain.content.ContentGroup;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "content_group")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ContentGroupJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "content_group_id")
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "modified_at")
    private LocalDateTime modifiedAt;

    private ContentGroupJpaEntity(Long id, String title, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.id = id;
        this.title = title;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    public static ContentGroupJpaEntity from(ContentGroup domain) {
        return new ContentGroupJpaEntity(
                domain.getId() != null ? domain.getId().getValue() : null,
                domain.getTitle(),
                domain.getCreatedAt(),
                domain.getModifiedAt()
        );
    }

    public ContentGroup toDomain() {
        return ContentGroup.of(id, title, createdAt, modifiedAt);
    }
}

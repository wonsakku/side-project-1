package com.study.content.adapter.out.persistence.entity.content;

import com.study.content.domain.content.ContentTag;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "content_tag")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ContentTagJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "content_tag_id")
    private Long id;

    @Column(name = "content_id", nullable = false)
    private Long contentId; // 객체 참조 제거

    @Column(name = "tag_id", nullable = false)
    private Long tagId;     // 객체 참조 제거
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "modified_at")
    private LocalDateTime modifiedAt;

    private ContentTagJpaEntity(Long contentId, Long tagId,
                                LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.contentId = contentId;
        this.tagId = tagId;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    public static ContentTagJpaEntity from(ContentTag contentTag) {
        return new ContentTagJpaEntity(
                contentTag.getContentIdValue(),
                contentTag.getTagIdValue(),
                contentTag.getCreatedAt(),
                contentTag.getModifiedAt()
        );
    }
}

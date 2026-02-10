package com.study.content.adapter.out.persistence.entity.content;

import com.study.content.domain.content.Thumbnail;
import com.study.content.domain.content.ThumbnailType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "thumb_nail")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ThumbnailJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "thumb_nail_id")
    private Long id;

    @Column(name = "content_id", nullable = false)
    private Long contentId;

    @Column(name = "img_url", nullable = false)
    private String imgUrl;

    @Column(name = "origin_file_name", nullable = false)
    private String originFileName; // 추가

    @Column(name = "saved_file_name", nullable = false)
    private String savedFileName;  // 추가

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private ThumbnailType type;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "modified_at")
    private LocalDateTime modifiedAt;

    private ThumbnailJpaEntity(Long id, Long contentId, String imgUrl, String originFileName,
                               String savedFileName, ThumbnailType type, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.id = id;
        this.contentId = contentId;
        this.imgUrl = imgUrl;
        this.originFileName = originFileName;
        this.savedFileName = savedFileName;
        this.type = type;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    public static ThumbnailJpaEntity from(Thumbnail domain) {
        return new ThumbnailJpaEntity(
                domain.getId() != null ? domain.getId().getValue() : null,
                domain.getContentId() != null ? domain.getContentId().getValue() : null,
                domain.getImgUrl(),
                domain.getOriginFileName(),
                domain.getSavedFileName(),
                domain.getType(),
                domain.getCreatedAt(),
                domain.getModifiedAt()
        );
    }

    public Thumbnail toDomain() {
        return Thumbnail.of(
                id,
                contentId,
                imgUrl,
                originFileName,
                savedFileName,
                type,
                createdAt,
                modifiedAt
        );
    }
}
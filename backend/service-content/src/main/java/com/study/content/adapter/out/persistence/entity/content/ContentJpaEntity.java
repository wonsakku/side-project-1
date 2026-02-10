package com.study.content.adapter.out.persistence.entity.content;

import com.study.content.domain.content.Content;
import com.study.content.domain.content.Thumbnail;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Entity
@Table(name = "content")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ContentJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "content_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "content_group_id")
    private ContentGroupJpaEntity contentGroup;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "age")
    private Integer age;

    @Column(name = "completed", nullable = false)
    private boolean completed;

    /**
     * CascadeType.ALL: Content 저장/수정/삭제 시 썸네일도 함께 처리
     * orphanRemoval = true: 리스트에서 제거된 썸네일 엔티티를 DB에서도 삭제
     * @JoinColumn: 단방향 연관관계에서 Thumbnail 테이블의 외래키(content_id)를 관리
     */
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "content_id")
    private List<ThumbnailJpaEntity> thumbnails = new ArrayList<>();

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "modified_at")
    private LocalDateTime modifiedAt;

    // 내부 생성자: 필드 매핑용 (age 추가)
    private ContentJpaEntity(Long id, String title, String description, Integer age,
                             boolean completed, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.age = age;
        this.completed = completed;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    /**
     * 도메인 -> 엔티티 변환 (썸네일 포함)
     */
    public static ContentJpaEntity from(Content domain) {
        ContentJpaEntity entity = new ContentJpaEntity(
                domain.getId() != null ? domain.getId().getValue() : null,
                domain.getTitle(),
                domain.getDescription(),
                domain.getAge(),
                domain.isCompleted(),
                domain.getCreatedAt(),
                domain.getModifiedAt()
        );

        // 도메인의 썸네일 리스트를 엔티티 리스트로 변환하여 주입
        if (domain.getThumbnails() != null && !domain.getThumbnails().isEmpty()) {
            List<ThumbnailJpaEntity> thumbnailEntities = domain.getThumbnails().stream()
                    .map(ThumbnailJpaEntity::from)
                    .collect(Collectors.toList());
            entity.updateThumbnails(thumbnailEntities);
        }

        return entity;
    }

    /**
     * 엔티티 -> 도메인 변환 (썸네일 포함)
     */
    public Content toDomain() {
        Content domain = Content.of(
                this.id,
                this.title,
                this.description,
                this.age,
                this.completed,
                this.createdAt,
                this.modifiedAt
        );

        // 엔티티의 썸네일 리스트를 도메인 리스트로 변환하여 주입
        if (this.thumbnails != null && !this.thumbnails.isEmpty()) {
            List<Thumbnail> domainThumbnails = this.thumbnails.stream()
                    .map(ThumbnailJpaEntity::toDomain)
                    .collect(Collectors.toList());
            domain.addThumbnails(domainThumbnails);
        }

        return domain;
    }

    public void updateThumbnails(List<ThumbnailJpaEntity> thumbnails) {
        this.thumbnails.clear();
        if (thumbnails != null) {
            this.thumbnails.addAll(thumbnails);
        }
    }

    public void changeContentGroup(ContentGroupJpaEntity contentGroup) {
        this.contentGroup = contentGroup;
    }
}
package com.study.content.adapter.out.persistence.entity.content;

import com.study.content.domain.content.Content;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

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

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "modified_at")
    private LocalDateTime modifiedAt;

    private ContentJpaEntity(Long id, String title, String description,
                             boolean completed, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.completed = completed;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    public static ContentJpaEntity from(Content domain) {
        return new ContentJpaEntity(
                domain.getId() != null ? domain.getId().getValue() : null,
                domain.getTitle(),
                domain.getDescription(),
                domain.isCompleted(),
                domain.getCreatedAt(),
                domain.getModifiedAt()
        );
    }

    public void changeContentGroup(ContentGroupJpaEntity contentGroup) {
        this.contentGroup = contentGroup;
    }

    public Content toDomain() {
        return Content.of(
                this.id,
                this.title,
                this.description,
                this.age,
                this.completed,
                this.createdAt,
                this.modifiedAt
        );
    }
}

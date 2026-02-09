package com.study.content.adapter.out.persistence.entity.content;

import com.study.content.domain.tag.Tag;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "tag")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TagJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tag_id")
    private Long id;

    @Column(name = "tag_name", nullable = false)
    private String tagName;

    @Column(name = "activated", nullable = false)
    private boolean activated;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "modified_at")
    private LocalDateTime modifiedAt;

    private TagJpaEntity(Long id, String tagName, boolean activated,
                         LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.id = id;
        this.tagName = tagName;
        this.activated = activated;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    public static TagJpaEntity from(Tag domain) {
        return new TagJpaEntity(
                domain.getId() != null ? domain.getId().getValue() : null,
                domain.getTagName(),
                domain.isActivated(),
                domain.getCreatedAt(),
                domain.getModifiedAt()
        );
    }

    public Tag toDomain() {
        return Tag.of(id, tagName, activated, createdAt, modifiedAt);
    }
}

package com.study.content.domain.tag;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@EqualsAndHashCode(of = "id")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Tag {

    private TagId id;
    private String tagName;
    private boolean activated;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    private Tag(TagId id, String tagName, boolean activated, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.id = id;
        this.tagName = tagName;
        this.activated = activated;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    public static Tag create(String tagName) {
        if (Objects.isNull(tagName) || tagName.isBlank()) {
            throw new IllegalArgumentException("태그명은 필수입니다.");
        }
        LocalDateTime now = LocalDateTime.now();
        return new Tag(null, tagName, true, now, now);
    }

    public static Tag of(Long id, String tagName, boolean activated, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        return new Tag(TagId.of(id), tagName, activated, createdAt, modifiedAt);
    }

    public void changeTagName(String tagName) {
        if (Objects.isNull(tagName) || tagName.isBlank()) {
            throw new IllegalArgumentException("태그명은 필수입니다.");
        }
        this.tagName = tagName;
    }
}

package com.study.content.domain.content;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ContentGroup {

    private ContentGroupId id;
    private String title;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    private ContentGroup(ContentGroupId id, String title, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.id = id;
        this.title = title;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    public static ContentGroup create(String title) {
        if (Objects.isNull(title) || title.isBlank()) {
            throw new IllegalArgumentException("제목은 필수입니다.");
        }
        LocalDateTime now = LocalDateTime.now();
        return new ContentGroup(null, title, now, now);
    }

    public static ContentGroup of(Long id, String title, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        return new ContentGroup(ContentGroupId.of(id), title, createdAt, modifiedAt);
    }

    public void changeTitle(String title) {
        if (Objects.isNull(title) || title.isBlank()) {
            throw new IllegalArgumentException("제목은 필수입니다.");
        }
        this.title = title;
    }
}

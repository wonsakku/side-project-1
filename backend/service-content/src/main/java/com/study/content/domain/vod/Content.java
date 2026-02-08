package com.study.content.domain.vod;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Content {

    private ContentId id;
    private String title;
    private String description;
    private boolean completed;
    private List<Tag> tags = new ArrayList<>();

    private Content(ContentId id, String title, String description, boolean completed, List<Tag> tags) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.completed = completed;
        this.tags = tags != null ? new ArrayList<>(tags) : new ArrayList<>();
    }

    public static Content create(String title, String description) {
        validateForCreate(title, description);
        return new Content(null, title, description, false, new ArrayList<>());
    }

    private static void validateForCreate(String title, String description) {
        if (Objects.isNull(title) || title.isBlank()) {
            throw new IllegalArgumentException("제목은 필수입니다.");
        }
        if (Objects.isNull(description) || description.isBlank()) {
            throw new IllegalArgumentException("설명은 필수입니다.");
        }
    }

    public static Content of(Long id, String title, String description, boolean completed, List<Tag> tags) {
        return new Content(ContentId.of(id), title, description, completed, tags);
    }

    public void changeCompleted(boolean completed) {
        this.completed = completed;
    }

    public void changeTitle(String title) {
        if (Objects.isNull(title) || title.isBlank()) {
            throw new IllegalArgumentException("제목은 필수입니다.");
        }
        this.title = title;
    }

    public void changeDescription(String description) {
        if (Objects.isNull(description) || description.isBlank()) {
            throw new IllegalArgumentException("설명은 필수입니다.");
        }
        this.description = description;
    }

    public void addTag(Tag tag) {
        if (Objects.isNull(tag)) {
            throw new IllegalArgumentException("태그는 필수입니다.");
        }
        this.tags.add(tag);
    }

    public void removeTag(Tag tag) {
        this.tags.remove(tag);
    }

    public List<Tag> getTags() {
        return Collections.unmodifiableList(tags);
    }
}

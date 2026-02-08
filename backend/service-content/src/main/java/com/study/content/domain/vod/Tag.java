package com.study.content.domain.vod;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Getter
@EqualsAndHashCode(of = "id")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Tag {

    private TagId id;
    private String tagName;

    private Tag(TagId id, String tagName) {
        this.id = id;
        this.tagName = tagName;
    }

    public static Tag create(String tagName) {
        if (Objects.isNull(tagName) || tagName.isBlank()) {
            throw new IllegalArgumentException("태그명은 필수입니다.");
        }
        return new Tag(null, tagName);
    }

    public static Tag of(Long id, String tagName) {
        return new Tag(TagId.of(id), tagName);
    }

    public void changeTagName(String tagName) {
        if (Objects.isNull(tagName) || tagName.isBlank()) {
            throw new IllegalArgumentException("태그명은 필수입니다.");
        }
        this.tagName = tagName;
    }
}

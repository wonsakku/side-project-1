package com.study.content.domain.vod;

import com.study.content.domain.content.ContentId;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class VodGroup {

    private VodGroupId id;
    private ContentId contentId;
    private String title;

    private VodGroup(VodGroupId id, ContentId contentId, String title) {
        this.id = id;
        this.contentId = contentId;
        this.title = title;
    }

    public static VodGroup create(ContentId contentId, String title) {
        validateForCreate(contentId, title);
        return new VodGroup(null, contentId, title);
    }

    private static void validateForCreate(ContentId contentId, String title) {
        if (Objects.isNull(contentId)) {
            throw new IllegalArgumentException("콘텐츠 ID는 필수입니다.");
        }
        if (Objects.isNull(title) || title.isBlank()) {
            throw new IllegalArgumentException("제목은 필수입니다.");
        }
    }

    public static VodGroup of(Long id, Long contentId, String title) {
        return new VodGroup(VodGroupId.of(id), ContentId.of(contentId), title);
    }

    public void changeTitle(String title) {
        if (Objects.isNull(title) || title.isBlank()) {
            throw new IllegalArgumentException("제목은 필수입니다.");
        }
        this.title = title;
    }
}

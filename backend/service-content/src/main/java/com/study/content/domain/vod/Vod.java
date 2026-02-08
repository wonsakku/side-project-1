package com.study.content.domain.vod;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Vod {

    private VodId id;
    private ContentId contentId;
    private String title;
    private String description;
    private String vodUrl;
    private int runningTime;

    private Vod(VodId id, ContentId contentId, String title, String description, String vodUrl, int runningTime) {
        this.id = id;
        this.contentId = contentId;
        this.title = title;
        this.description = description;
        this.vodUrl = vodUrl;
        this.runningTime = runningTime;
    }

    public static Vod create(ContentId contentId, String title, String description, String vodUrl, int runningTime) {
        validateForCreate(contentId, title, vodUrl, runningTime);
        return new Vod(null, contentId, title, description, vodUrl, runningTime);
    }

    private static void validateForCreate(ContentId contentId, String title, String vodUrl, int runningTime) {
        if (Objects.isNull(contentId)) {
            throw new IllegalArgumentException("콘텐츠 ID는 필수입니다.");
        }
        if (Objects.isNull(title) || title.isBlank()) {
            throw new IllegalArgumentException("제목은 필수입니다.");
        }
        if (Objects.isNull(vodUrl) || vodUrl.isBlank()) {
            throw new IllegalArgumentException("VOD URL은 필수입니다.");
        }
        if (runningTime <= 0) {
            throw new IllegalArgumentException("재생 시간은 0보다 커야 합니다.");
        }
    }

    public static Vod of(Long id, Long contentId, String title, String description, String vodUrl, int runningTime) {
        return new Vod(VodId.of(id), ContentId.of(contentId), title, description, vodUrl, runningTime);
    }

    public void changeTitle(String title) {
        if (Objects.isNull(title) || title.isBlank()) {
            throw new IllegalArgumentException("제목은 필수입니다.");
        }
        this.title = title;
    }

    public void changeDescription(String description) {
        this.description = description;
    }

    public void changeVodUrl(String vodUrl) {
        if (Objects.isNull(vodUrl) || vodUrl.isBlank()) {
            throw new IllegalArgumentException("VOD URL은 필수입니다.");
        }
        this.vodUrl = vodUrl;
    }
}

package com.study.content.domain.content;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Thumbnail {

    private ThumbnailId id;
    private ContentId contentId;
    private String imgUrl;
    private ThumbnailType type;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    private Thumbnail(ThumbnailId id, ContentId contentId, String imgUrl, ThumbnailType type,
                      LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.id = id;
        this.contentId = contentId;
        this.imgUrl = imgUrl;
        this.type = type;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    public static Thumbnail create(ContentId contentId, String imgUrl, ThumbnailType type) {
        validateForCreate(contentId, imgUrl, type);
        LocalDateTime now = LocalDateTime.now();
        return new Thumbnail(null, contentId, imgUrl, type, now, now);
    }

    private static void validateForCreate(ContentId contentId, String imgUrl, ThumbnailType type) {
        if (Objects.isNull(contentId)) {
            throw new IllegalArgumentException("콘텐츠 ID는 필수입니다.");
        }
        if (Objects.isNull(imgUrl) || imgUrl.isBlank()) {
            throw new IllegalArgumentException("이미지 URL은 필수입니다.");
        }
        if (Objects.isNull(type)) {
            throw new IllegalArgumentException("썸네일 타입은 필수입니다.");
        }
    }

    public static Thumbnail of(Long id, Long contentId, String imgUrl, ThumbnailType type,
                               LocalDateTime createdAt, LocalDateTime modifiedAt) {
        return new Thumbnail(ThumbnailId.of(id), ContentId.of(contentId), imgUrl, type, createdAt, modifiedAt);
    }

    public void changeImgUrl(String imgUrl) {
        if (Objects.isNull(imgUrl) || imgUrl.isBlank()) {
            throw new IllegalArgumentException("이미지 URL은 필수입니다.");
        }
        this.imgUrl = imgUrl;
    }

    public void changeType(ThumbnailType type) {
        if (Objects.isNull(type)) {
            throw new IllegalArgumentException("썸네일 타입은 필수입니다.");
        }
        this.type = type;
    }
}

package com.study.content.domain.vod;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Thumbnail {

    private ThumbnailId id;
    private ContentId contentId;
    private String imgUrl;
    private ThumbnailType type;

    private Thumbnail(ThumbnailId id, ContentId contentId, String imgUrl, ThumbnailType type) {
        this.id = id;
        this.contentId = contentId;
        this.imgUrl = imgUrl;
        this.type = type;
    }

    public static Thumbnail create(ContentId contentId, String imgUrl, ThumbnailType type) {
        validateForCreate(contentId, imgUrl, type);
        return new Thumbnail(null, contentId, imgUrl, type);
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

    public static Thumbnail of(Long id, Long contentId, String imgUrl, ThumbnailType type) {
        return new Thumbnail(ThumbnailId.of(id), ContentId.of(contentId), imgUrl, type);
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

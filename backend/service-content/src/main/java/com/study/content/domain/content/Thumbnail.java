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
    private String originFileName; // 추가
    private String savedFileName;  // 추가
    private ThumbnailType type;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    private Thumbnail(ThumbnailId id, ContentId contentId, String imgUrl, String originFileName,
                      String savedFileName, ThumbnailType type, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.id = id;
        this.contentId = contentId;
        this.imgUrl = imgUrl;
        this.originFileName = originFileName;
        this.savedFileName = savedFileName;
        this.type = type;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    /**
     * 신규 생성을 위한 팩토리 메서드
     */
    public static Thumbnail create(ContentId contentId, String imgUrl, String originFileName,
                                   String savedFileName, ThumbnailType type, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        validateForCreate(contentId, imgUrl, originFileName, savedFileName, type);
        return new Thumbnail(null, contentId, imgUrl, originFileName, savedFileName, type, createdAt, modifiedAt);
    }

    private static void validateForCreate(ContentId contentId, String imgUrl, String originFileName,
                                          String savedFileName, ThumbnailType type) {
        if (Objects.isNull(contentId)) throw new IllegalArgumentException("콘텐츠 ID는 필수입니다.");
        if (Objects.isNull(imgUrl) || imgUrl.isBlank()) throw new IllegalArgumentException("이미지 URL은 필수입니다.");
        if (Objects.isNull(originFileName) || originFileName.isBlank()) throw new IllegalArgumentException("원본 파일명은 필수입니다.");
        if (Objects.isNull(savedFileName) || savedFileName.isBlank()) throw new IllegalArgumentException("저장용 파일명은 필수입니다.");
        if (Objects.isNull(type)) throw new IllegalArgumentException("썸네일 타입은 필수입니다.");
    }

    /**
     * DB 데이터 복원을 위한 팩토리 메서드
     */
    public static Thumbnail of(Long id, Long contentId, String imgUrl, String originFileName,
                               String savedFileName, ThumbnailType type, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        return new Thumbnail(ThumbnailId.of(id), ContentId.of(contentId), imgUrl,
                originFileName, savedFileName, type, createdAt, modifiedAt);
    }

    // ... changeImgUrl, changeType 등 기존 유지
}
package com.study.content.domain.content;

import com.study.content.domain.tag.TagId;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ContentTag {
    private Long contentTagId;
    private ContentId contentId;
    private TagId tagId;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    private ContentTag(ContentId contentId, TagId tagId, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.contentId = contentId;
        this.tagId = tagId;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    public static ContentTag of(ContentId contentId, TagId tagId,  LocalDateTime createdAt, LocalDateTime modifiedAt) {
        return new ContentTag(contentId, tagId,  createdAt, modifiedAt);
    }

    public static ContentTag of(Long contentTagId, Long contentId, Long tagId,  LocalDateTime createdAt, LocalDateTime modifiedAt) {
        return new ContentTag(contentTagId, ContentId.of(tagId),  TagId.of(tagId), createdAt, modifiedAt);
    }

    // 추가 추천 메서드
    public Long getContentIdValue() {
        return contentId != null ? contentId.getValue() : null;
    }

    public Long getTagIdValue() {
        return tagId != null ? tagId.getValue() : null;
    }
}

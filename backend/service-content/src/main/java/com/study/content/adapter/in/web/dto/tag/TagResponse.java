package com.study.content.adapter.in.web.dto.tag;

import com.study.content.domain.tag.Tag;

public record TagResponse(Long tagId, String tagName) {
    public static TagResponse from(Tag tag) {
        return new TagResponse(tag.getId().getValue(), tag.getTagName());
    }
}

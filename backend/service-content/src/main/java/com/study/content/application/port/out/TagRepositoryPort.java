package com.study.content.application.port.out;

import com.study.content.domain.tag.Tag;

import java.util.List;

public interface TagRepositoryPort {

    Tag save(Tag tag);

    boolean existsByTagName(String tagName);

    List<Tag> getTags();

    List<Tag> getTagsByIdIn(List<Long> tagIds);
}

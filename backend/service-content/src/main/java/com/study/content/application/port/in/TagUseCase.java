package com.study.content.application.port.in;

import com.study.content.domain.tag.Tag;
import com.study.content.domain.tag.TagId;

import java.util.List;

public interface TagUseCase {

    TagId createTag(CreateTagCommand command);

    List<Tag> getTags();

    record CreateTagCommand(String tagName) {
    }
}

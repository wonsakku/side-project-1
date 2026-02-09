package com.study.content.application.port.out;

import com.study.content.domain.content.ContentTag;

import java.util.List;

public interface ContentTagRepositoryPort {
    void saveAll(List<ContentTag> contentTags);
}

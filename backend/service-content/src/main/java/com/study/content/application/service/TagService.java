package com.study.content.application.service;

import com.study.content.application.port.in.TagUseCase;
import com.study.content.application.port.out.TagRepositoryPort;
import com.study.content.domain.tag.Tag;
import com.study.content.domain.tag.TagId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TagService implements TagUseCase {

    private final TagRepositoryPort tagRepositoryPort;

    @Override
    @Transactional
    public TagId createTag(CreateTagCommand command) {
        if (tagRepositoryPort.existsByTagName(command.tagName())) {
            throw new IllegalArgumentException("이미 존재하는 태그명입니다.");
        }

        Tag tag = Tag.create(command.tagName());
        Tag savedTag = tagRepositoryPort.save(tag);

        return savedTag.getId();
    }

    @Override
    public List<Tag> getTags() {
        return tagRepositoryPort.getTags();
    }
}

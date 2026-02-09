package com.study.content.application.service;

import com.study.content.application.port.in.ContentUseCase;
import com.study.content.application.port.out.ContentRepositoryPort;
import com.study.content.application.port.out.ContentTagRepositoryPort;
import com.study.content.application.port.out.TagRepositoryPort;
import com.study.content.application.service.dto.CreateContentCommand;
import com.study.content.domain.content.Content;
import com.study.content.domain.content.ContentId;
import com.study.content.domain.content.ContentTag;
import com.study.content.domain.tag.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ContentService implements ContentUseCase {

    private final TagRepositoryPort tagRepositoryPort;
    private final ContentRepositoryPort contentRepositoryPort;
    private final ContentTagRepositoryPort contentTagRepositoryPort;


    @Transactional
    @Override
    public ContentId createContent(CreateContentCommand command) {
        List<Tag> tags = tagRepositoryPort.getTagsByIdIn(command.tagIds());
        LocalDateTime now = LocalDateTime.now();
        Content content = Content.create(
                command.title(),
                command.description(),
                command.age(),
                command.completed(),
                now,
                now
        );

        Content savedContent = contentRepositoryPort.save(content);
        List<ContentTag> contentTags = tags.stream()
                .map(tag -> ContentTag.of(savedContent.getId(), tag.getId(), now, now))
                .toList();

        contentTagRepositoryPort.saveAll(contentTags);

        return savedContent.getId();
    }
}

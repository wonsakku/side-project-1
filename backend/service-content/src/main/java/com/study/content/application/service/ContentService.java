package com.study.content.application.service;

import com.study.content.application.port.in.ContentUseCase;
import com.study.content.application.port.out.ContentRepositoryPort;
import com.study.content.application.port.out.ContentTagRepositoryPort;
import com.study.content.application.port.out.TagRepositoryPort;
import com.study.content.application.service.dto.CreateContentCommand;
import com.study.content.application.service.dto.RequestThumbNail;
import com.study.content.domain.content.Content;
import com.study.content.domain.content.ContentId;
import com.study.content.domain.content.ContentTag;
import com.study.content.domain.content.Thumbnail;
import com.study.content.domain.content.file.FileSource;
import com.study.content.domain.content.file.FileStorage;
import com.study.content.domain.content.file.FileStorageInfo;
import com.study.content.domain.tag.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ContentService implements ContentUseCase {

    private final String THUMB_NAIL_PATH = "/thumbnails";

    private final TagRepositoryPort tagRepositoryPort;
    private final ContentRepositoryPort contentRepositoryPort;
    private final ContentTagRepositoryPort contentTagRepositoryPort;
    private final FileStorage fileStorage;


    @Transactional
    @Override
    public ContentId createContent(CreateContentCommand command) {

        // 컨텐츠 저장
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
        final ContentId contentId = savedContent.getId();


        List<FileStorageInfo> fileStorageInfos = fileStorage.store(toFileSource(command.thumbNails()), THUMB_NAIL_PATH + "/" + contentId.getValue());
        List<Thumbnail> thumbnails = fileStorageInfos.stream()
                .map(
        file -> Thumbnail.create(
                            contentId,
                            file.filePath(),
                            file.originFileName(),
                            file.savedFileName(),
                            file.thumbnailType(),
                            now,
                            now
                        )
                ).toList();
        savedContent.addThumbnails(thumbnails);


        // 6. 2차 저장: 변경 감지(Dirty Checking) 또는 명시적 저장
        // 1번에서 설정한 CascadeType.ALL 덕분에 썸네일들이 DB에 저장됨
        contentRepositoryPort.save(savedContent);


        List<Tag> tags = tagRepositoryPort.getTagsByIdIn(command.tagIds());
        List<ContentTag> contentTags = tags.stream()
                .map(tag -> ContentTag.of(contentId, tag.getId(), now, now))
                .toList();

        contentTagRepositoryPort.saveAll(contentTags);

        return savedContent.getId();
    }

    private List<FileSource> toFileSource(List<RequestThumbNail> requestThumbNails) {
        return requestThumbNails.stream()
                .map(t ->  FileSource.builder()
                        .size(t.getSize())
                        .inputStream(t.getInputStream())
                        .fileName(t.getFileOriginalName())
                        .thumbnailType(t.getThumbnailType())
                        .build()
                )
                .toList();
    }

}

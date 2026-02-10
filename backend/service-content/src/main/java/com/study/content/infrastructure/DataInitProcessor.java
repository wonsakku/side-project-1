package com.study.content.infrastructure;

import com.study.content.application.port.out.ContentRepositoryPort;
import com.study.content.application.port.out.ContentTagRepositoryPort;
import com.study.content.application.port.out.TagRepositoryPort;
import com.study.content.domain.content.Content;
import com.study.content.domain.content.ContentTag;
import com.study.content.domain.content.Thumbnail;
import com.study.content.domain.content.ThumbnailType;
import com.study.content.domain.tag.Tag;
import com.study.content.domain.tag.TagId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
public class DataInitProcessor {

    private final String THUMB_NAIL_PATH = "/thumbnails";


    private final TagRepositoryPort tagRepository; // Outbound Port
    private final ContentRepositoryPort contentRepository;
    private final ContentTagRepositoryPort contentTagRepository;



    @Transactional
    public void initTags(){
        if(tagRepository.getTags().isEmpty()){
            List<Tag> tags = createTags();
            for (Tag tag : tags) {
                tagRepository.save(tag);
            }
        }
    }

    private List<Tag> createTags() {
        return Stream.of(
                Tag.create("액션"),
                Tag.create("판타지"),
                Tag.create("메카닉"),
                Tag.create("순정"),
                Tag.create("학원"),
                Tag.create("이세계"),
                Tag.create("회귀물"),
                Tag.create("게임"),
                Tag.create("개그"),
                Tag.create("일상"),
                Tag.create("범죄")
        ).toList();
    }

    @Transactional
    public void initContents() {
        List<Content> contents = createContents();
        List<Tag> tags = tagRepository.getTags();
        for (Content content : contents) {
            LocalDateTime now = LocalDateTime.now();

            Content savedContent = contentRepository.save(content);

            // 원본 리스트를 보호하기 위해 복사본 생성
            List<Tag> shuffledTags = new ArrayList<>(tags);
            Collections.shuffle(shuffledTags);

            List<ContentTag> contentTags = shuffledTags.stream().map(tag -> ContentTag.of(savedContent.getId(), tag.getId(), now, now)).toList();
            contentTagRepository.saveAll(contentTags);

            Long contentIdValue = savedContent.getId().getValue();
            String mainFileNm = contentIdValue + "-main.png";
            String subFileNm = contentIdValue + "-sub.png";
            List<Thumbnail> thumbnails = List.of(
                    Thumbnail.create(savedContent.getId(), THUMB_NAIL_PATH + "/" + contentIdValue + "/" + mainFileNm, mainFileNm, mainFileNm, ThumbnailType.MAIN, now, now),
                    Thumbnail.create(savedContent.getId(), THUMB_NAIL_PATH + "/" + contentIdValue + "/" + subFileNm, subFileNm, subFileNm, ThumbnailType.SUB, now, now)
            );

            savedContent.addThumbnails(thumbnails);
            contentRepository.save(savedContent);
        }
    }


    private List<Content> createContents() {
        LocalDateTime now = LocalDateTime.now();
        return Arrays.asList(
                Content.create("블루 아카이브 1부 1장", "블루 아카이브 1부 1장", 15, true, now, now),
                Content.create("블루 아카이브 1부 2장", "블루 아카이브 1부 2장", 15, true, now, now),
                Content.create("블루 아카이브 2부 1장", "블루 아카이브 2부 1장", 15, true, now, now),
                Content.create("블루 아카이브 3부 1장", "블루 아카이브 3부 1장", 15, true, now, now),
                Content.create("블루 아카이브 3부 2장", "블루 아카이브 3부 2장", 15, true, now, now),
                Content.create("블루 아카이브 3부 3장", "블루 아카이브 3부 3장", 15, true, now, now),
                Content.create("블루 아카이브 3부 4장", "블루 아카이브 3부 4장", 15, true, now, now),
                Content.create("블루 아카이브 4부 1장", "블루 아카이브 4부 1장", 15, true, now, now),
                Content.create("블루 아카이브 2부 2장", "블루 아카이브 2부 2장", 15, true, now, now),
                Content.create("블루 아카이브 최종장", "블루 아카이브 최종장", 15, true, now, now)
        );
    }
}

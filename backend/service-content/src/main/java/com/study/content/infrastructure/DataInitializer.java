package com.study.content.infrastructure;

import com.study.content.application.port.out.TagRepositoryPort;
import com.study.content.domain.tag.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
public class DataInitializer implements ApplicationRunner {
    private final TagRepositoryPort tagRepository; // Outbound Port

    @Transactional
    @Override
    public void run(ApplicationArguments args) {
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
}

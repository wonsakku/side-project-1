package com.study.content.adapter.out.persistence;

import com.study.content.adapter.out.persistence.entity.content.TagJpaEntity;
import com.study.content.adapter.out.persistence.repository.TagJpaRepository;
import com.study.content.application.port.out.TagRepositoryPort;
import com.study.content.domain.tag.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class TagPersistenceAdapter implements TagRepositoryPort {

    private final TagJpaRepository tagJpaRepository;

    @Override
    public Tag save(Tag tag) {
        TagJpaEntity entity = TagJpaEntity.from(tag);
        TagJpaEntity savedEntity = tagJpaRepository.save(entity);
        return savedEntity.toDomain();
    }

    @Override
    public boolean existsByTagName(String tagName) {
        return tagJpaRepository.existsByTagName(tagName);
    }

    @Override
    public List<Tag> getTags() {
        return tagJpaRepository.findAllByActivatedTrue()
                .stream()
                .map(TagJpaEntity::toDomain)
                .toList();
    }

    @Override
    public List<Tag> getTagsByIdIn(List<Long> tagIds) {
        return tagJpaRepository.findAllById(tagIds)
                .stream().map(TagJpaEntity::toDomain)
                .toList();
    }
}

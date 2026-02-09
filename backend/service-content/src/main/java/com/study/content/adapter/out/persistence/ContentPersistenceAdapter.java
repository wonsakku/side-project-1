package com.study.content.adapter.out.persistence;

import com.study.content.adapter.out.persistence.entity.content.ContentJpaEntity;
import com.study.content.adapter.out.persistence.entity.content.TagJpaEntity;
import com.study.content.adapter.out.persistence.repository.ContentJpaRepository;
import com.study.content.adapter.out.persistence.repository.TagJpaRepository;
import com.study.content.application.port.out.ContentRepositoryPort;
import com.study.content.application.port.out.TagRepositoryPort;
import com.study.content.domain.content.Content;
import com.study.content.domain.tag.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ContentPersistenceAdapter implements ContentRepositoryPort {

    private final ContentJpaRepository contentJpaRepository;

    @Override
    public Content save(Content content) {
        ContentJpaEntity entity = ContentJpaEntity.from(content);
        ContentJpaEntity savedEntity = contentJpaRepository.save(entity);
        return savedEntity.toDomain();
    }

}

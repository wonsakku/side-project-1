package com.study.content.adapter.out.persistence;

import com.study.content.adapter.out.persistence.entity.content.ContentTagJpaEntity;
import com.study.content.adapter.out.persistence.repository.ContentTagJpaRepository;
import com.study.content.application.port.out.ContentTagRepositoryPort;
import com.study.content.domain.content.ContentTag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ContentTagPersistenceAdapter implements ContentTagRepositoryPort {

    private final ContentTagJpaRepository contentTagJpaRepository;

    @Override
    public void saveAll(List<ContentTag> contentTags) {
        List<ContentTagJpaEntity> list = contentTags.stream().map(ContentTagJpaEntity::from).toList();
        contentTagJpaRepository.saveAll(list);
    }
}

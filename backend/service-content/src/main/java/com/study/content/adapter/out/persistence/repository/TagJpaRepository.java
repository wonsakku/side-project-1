package com.study.content.adapter.out.persistence.repository;

import com.study.content.adapter.out.persistence.entity.content.TagJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface TagJpaRepository extends JpaRepository<TagJpaEntity, Long> {

    boolean existsByTagName(String tagName);

    List<TagJpaEntity> findAllByActivatedTrue();
}

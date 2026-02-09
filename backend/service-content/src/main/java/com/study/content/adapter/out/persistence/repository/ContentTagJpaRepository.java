package com.study.content.adapter.out.persistence.repository;

import com.study.content.adapter.out.persistence.entity.content.ContentTagJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContentTagJpaRepository extends JpaRepository<ContentTagJpaEntity, Integer> {
}

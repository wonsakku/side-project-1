package com.study.content.adapter.out.persistence.repository;

import com.study.content.adapter.out.persistence.entity.content.ContentJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContentJpaRepository extends JpaRepository<ContentJpaEntity, Long> {

}

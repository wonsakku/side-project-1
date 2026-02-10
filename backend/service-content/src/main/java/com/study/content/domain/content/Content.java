package com.study.content.domain.content;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Content {

    private ContentId id;
    private String title;
    private String description;
    private Integer age;
    private boolean completed;
    private List<Thumbnail> thumbnails = new ArrayList<>();
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    /**
     * 내부 빌더를 통해서만 생성되도록 생성자를 private으로 설정합니다.
     */
    @Builder(access = AccessLevel.PRIVATE)
    private Content(ContentId id, String title, String description, Integer age,
                    boolean completed, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.age = age;
        this.completed = completed;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    /**
     * 신규 생성을 위한 팩토리 메서드
     */
    public static Content create(String title, String description, Integer age, boolean completed, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        validateForCreate(title, description);

        return Content.builder()
                .id(null) // 신규 생성 시 ID는 null
                .title(title)
                .description(description)
                .age(age)
                .completed(completed)
                .createdAt(createdAt)
                .modifiedAt(modifiedAt)
                .build();
    }

    /**
     * DB 데이터 기반 재구성(Reconstitution)을 위한 팩토리 메서드
     */
    public static Content of(Long id, String title, String description, Integer age,
                             boolean completed, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        return Content.builder()
                .id(ContentId.of(id))
                .title(title)
                .description(description)
                .age(age)
                .completed(completed)
                .createdAt(createdAt)
                .modifiedAt(modifiedAt)
                .build();
    }

    private static void validateForCreate(String title, String description) {
        if (Objects.isNull(title) || title.isBlank()) {
            throw new IllegalArgumentException("제목은 필수입니다.");
        }
        if (Objects.isNull(description) || description.isBlank()) {
            throw new IllegalArgumentException("설명은 필수입니다.");
        }
    }

    public void changeCompleted(boolean completed) {
        this.completed = completed;
    }

    public void changeTitle(String title) {
        if (Objects.isNull(title) || title.isBlank()) {
            throw new IllegalArgumentException("제목은 필수입니다.");
        }
        this.title = title;
    }

    public void changeDescription(String description) {
        if (Objects.isNull(description) || description.isBlank()) {
            throw new IllegalArgumentException("설명은 필수입니다.");
        }
        this.description = description;
    }

    // 추가: ID를 주입받는 reconstitution 메서드 (저장 후 ID 업데이트용)
    public Content withId(ContentId id) {
        return Content.builder()
                .id(id)
                .title(this.title)
                .description(this.description)
                .age(this.age)
                .completed(this.completed)
                .createdAt(this.createdAt)
                .modifiedAt(this.modifiedAt)
                .build();
    }

    // 추가: 썸네일 주입 메서드
    public void addThumbnails(List<Thumbnail> thumbnails) {
        this.thumbnails.addAll(thumbnails);
    }
}
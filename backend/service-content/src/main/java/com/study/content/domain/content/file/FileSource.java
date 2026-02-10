package com.study.content.domain.content.file;

import com.study.content.domain.content.ThumbnailType;
import lombok.Builder;

import java.io.InputStream;

/**
 * 업로드할 파일의 원천 데이터를 담는 객체
 */
@Builder
public record FileSource(
        InputStream inputStream,
        String fileName,
        long size,
        ThumbnailType thumbnailType) {
    // 팩토리 메서드 등을 추가해 편의성을 높일 수 있습니다.
}

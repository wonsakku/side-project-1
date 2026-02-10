package com.study.content.domain.content.file;

import com.study.content.domain.content.ThumbnailType;

public record FileStorageInfo(String filePath, String originFileName, String savedFileName, ThumbnailType thumbnailType) {
}

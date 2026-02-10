package com.study.content.domain.content.file;

import java.util.List;

public interface FileStorage {


    List<FileStorageInfo> store(List<FileSource> fileSources, String folderPath);

    /**
     * 프런트엔드에서 썸네일을 보여주기 위한 URL 반환
     */
    String getPublicUrl(String fileName);
}

package com.study.content.infrastructure.file;

import com.study.content.domain.content.file.FileSource;
import com.study.content.domain.content.file.FileStorage;
import com.study.content.domain.content.file.FileStorageInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class LocalFileStorage implements FileStorage {

    @Value("${file.basePath}") // 예: C:/data/uploads
    private String basePath;

    @Override
    public List<FileStorageInfo> store(List<FileSource> fileSources, String folderPath) {
        List<FileStorageInfo> results = new ArrayList<>();

        try {
            // 1. 물리적 저장 디렉토리 확정 및 생성
            Path targetDirectory = Paths.get(basePath, folderPath);
            if (!Files.exists(targetDirectory)) {
                Files.createDirectories(targetDirectory);
            }

            // 2. 각 파일 소스를 순회하며 물리 저장 실행
            for (FileSource source : fileSources) {
                String fileName = source.fileName();

                // 1. UUID + 확장자 생성
                String savedFileName = UUID.randomUUID().toString() + extractExtension(fileName);

                Path targetPath = targetDirectory.resolve(savedFileName);

                // 3. InputStream 복사 (NIO 사용)
                Files.copy(source.inputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);

                // 4. DB에 기록할 상대 경로 생성
                // 폴더 구조와 파일명을 합쳐서 반환 (예: thumbnails/123/s_image.jpg)
                String savedRelativePath = folderPath + "/" + savedFileName;


                results.add(new FileStorageInfo(savedRelativePath, fileName, savedFileName, source.thumbnailType()));
            }

            return results;

        } catch (IOException e) {
            throw new RuntimeException("로컬 파일 저장 시스템 오류가 발생했습니다.", e);
        }
    }

    /**
     * 로컬 저장소는 URL 생성을 직접 담당하지 않으므로,
     * 저장된 상대 경로를 그대로 반환하거나 슬래시(/) 정규화만 수행합니다.
     */
    @Override
    public String getPublicUrl(String storedPath) {
        if (storedPath == null || storedPath.isBlank()) return "";

        // 어떤 환경에서도 경로가 /로 시작하도록 보장
        return storedPath.startsWith("/") ? storedPath : "/" + storedPath;
    }

    // 확장자 추출 헬퍼 메서드
    private String extractExtension(String originalFilename) {
        int pos = originalFilename.lastIndexOf(".");
        return (pos != -1) ? originalFilename.substring(pos) : "";
    }
}
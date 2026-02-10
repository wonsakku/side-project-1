package com.study.content.application.service.dto;

import com.study.content.domain.content.ThumbnailType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

@Getter
@Builder
@AllArgsConstructor
public class RequestThumbNail {
    private InputStream inputStream;
    private String fileOriginalName;
    private long size;
    private ThumbnailType thumbnailType;
}

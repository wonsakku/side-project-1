package com.study.content.adapter.in.web.dto.content;

import com.study.content.domain.content.ThumbnailType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
public class UploadFileInfo {
    private MultipartFile thumbNail;
    private ThumbnailType thumbnailType;
}

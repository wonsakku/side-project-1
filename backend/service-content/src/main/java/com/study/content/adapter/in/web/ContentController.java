package com.study.content.adapter.in.web;

import com.study.content.adapter.in.web.dto.ResponseForm;
import com.study.content.adapter.in.web.dto.content.CreateContentRequest;
import com.study.content.application.port.in.ContentUseCase;
import com.study.content.application.service.dto.CreateContentCommand;
import com.study.content.application.service.dto.RequestThumbNail;
import com.study.content.domain.content.ContentId;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ContentController {

    private final ContentUseCase contentUseCase;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ResponseForm<Long>> createContent(@ModelAttribute CreateContentRequest request) {

        // MultipartFile이 포함된 request를 Command로 변환
        ContentId content = contentUseCase.createContent(toCommand(request));

        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        new ResponseForm<>(
                                HttpStatus.OK.value(),
                                HttpStatus.OK.name(),
                                content.getValue()
                        )
                );
    }

    private CreateContentCommand toCommand(CreateContentRequest request) {
        List<RequestThumbNail> thumbNails = request.thumbNails().stream()
                .map(thumbNail -> {
                    try {
                        return RequestThumbNail.builder()
                                .inputStream(thumbNail.getThumbNail().getInputStream())
                                .size(thumbNail.getThumbNail().getSize())
                                .fileOriginalName(thumbNail.getThumbNail().getOriginalFilename())
                                .thumbnailType(thumbNail.getThumbnailType())
                                .build();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }).toList();

        return CreateContentCommand.builder()
                .age(request.age())
                .completed(request.completed())
                .thumbNails(thumbNails)
                .description(request.description())
                .title(request.title())
                .tagIds(request.tagIds())
                .thumbNails(thumbNails)
                .build();
    }
}

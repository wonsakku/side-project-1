package com.study.content.adapter.in.web;

import com.study.content.adapter.in.web.dto.ResponseForm;
import com.study.content.application.port.in.ContentUseCase;
import com.study.content.application.service.dto.CreateContentCommand;
import com.study.content.domain.content.ContentId;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ContentController {

    private final ContentUseCase contentUseCase;

    @PostMapping
    public ResponseEntity<ResponseForm<Long>> createContent(@RequestBody CreateContentCommand command) {
        ContentId content = contentUseCase.createContent(command);
        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        new ResponseForm<>(
                                HttpStatus.OK.value(),
                                HttpStatus.OK.name(),
                                content.getValue()
                        )
                );
    }
}

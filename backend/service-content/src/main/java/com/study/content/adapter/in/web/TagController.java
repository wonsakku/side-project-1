package com.study.content.adapter.in.web;

import com.study.content.adapter.in.web.dto.tag.CreateTagResponse;
import com.study.content.adapter.in.web.dto.tag.TagResponse;
import com.study.content.application.port.in.TagUseCase;
import com.study.content.application.service.dto.CreateTagRequest;
import com.study.content.domain.tag.Tag;
import com.study.content.domain.tag.TagId;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tags")
@RequiredArgsConstructor
public class TagController {

    private final TagUseCase tagUseCase;

    @PostMapping
    public ResponseEntity<CreateTagResponse> createTag(@RequestBody CreateTagRequest request) {
        TagUseCase.CreateTagCommand command = new TagUseCase.CreateTagCommand(request.tagName());
        TagId tagId = tagUseCase.createTag(command);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new CreateTagResponse(tagId.getValue()));
    }

    @GetMapping
    public ResponseEntity<List<TagResponse>> createTag() {
        List<Tag> tags = tagUseCase.getTags();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(tags.stream()
                        .map(TagResponse::from)
                        .toList()
                );
    }


}

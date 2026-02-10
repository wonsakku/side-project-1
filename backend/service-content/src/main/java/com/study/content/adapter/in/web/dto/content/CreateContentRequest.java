package com.study.content.adapter.in.web.dto.content;

import java.util.List;

public record CreateContentRequest(String title,
                                   String description,
                                   List<Long> tagIds,
                                   Integer age,
                                   boolean completed,
                                   List<UploadFileInfo> thumbNails) {
}

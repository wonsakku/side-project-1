package com.study.content.application.port.in;

import com.study.content.application.service.dto.CreateContentCommand;
import com.study.content.domain.content.ContentId;
import org.springframework.web.bind.annotation.RequestBody;

public interface ContentUseCase {

    ContentId createContent(@RequestBody CreateContentCommand command);
}

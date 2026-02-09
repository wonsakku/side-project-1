package com.study.content.application.port.out;

import com.study.content.domain.content.Content;

public interface ContentRepositoryPort {
    Content save(Content content);
}

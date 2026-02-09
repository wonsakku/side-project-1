package com.study.content.adapter.in.web.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ResponseForm<T> {
    private final Integer responseCode;
    private final String responseMessage;
    private final T data;

}

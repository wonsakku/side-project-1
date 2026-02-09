package com.study.content.application.service.dto;

import java.util.List;

public record CreateContentCommand(String title, String description, List<Long> tagIds, Integer age, boolean completed) {
}

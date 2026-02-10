package com.study.content.infrastructure;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements ApplicationRunner {

    private final DataInitProcessor dataInitProcessor;

    @Override
    public void run(ApplicationArguments args) {
        dataInitProcessor.initTags();
        dataInitProcessor.initContents();
    }



}

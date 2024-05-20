package com.pda.practice.test;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TestService {

    private final TestRepository testRepository;

    public String getDataSource() {
        return testRepository.getDataSource();
    }
}

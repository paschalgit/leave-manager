package com.peswa.leavemanager.service;

import org.springframework.stereotype.Service;

public interface SequenceGeneratorService {
    long generateSequence(String name);
}

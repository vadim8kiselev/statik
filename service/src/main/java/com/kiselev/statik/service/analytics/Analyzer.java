package com.kiselev.statik.service.analytics;

import com.kiselev.statik.Storer;
import com.kiselev.statik.model.Command;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class Analyzer {

    private final Storer storer;

    public String analyze(Command command) {
        return String.format("Report for command {id: %s, title: %s}",
                command.getId(),
                command.getMessage());
    }
}

package com.kiselev.statik.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Command {

    private Long id;

    private String message;
}

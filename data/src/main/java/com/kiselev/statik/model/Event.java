package com.kiselev.statik.model;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class Event {

    private Long id;

    private Date timestamp;

    private String message;
}

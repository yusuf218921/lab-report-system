package com.bigbox.labreports.system.core.results;

import lombok.Getter;

@Getter
public class Result {
    private final boolean success;
    private String message;

    public Result(boolean success) {
        this.success = success;
    }

    public Result(boolean success, String message) {
        this(success);
        this.message = message;
    }
}

package com.bigbox.labreports.system.core.results;

import lombok.Getter;

@Getter
public class ErrorResult extends Result{
    public ErrorResult() {
        super(false);
    }

    public ErrorResult(String message) {
        super(false, message);
    }
}

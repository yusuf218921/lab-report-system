package com.bigbox.labreports.system.core.results;

import lombok.Getter;

@Getter
public class SuccessResult extends Result{
    public SuccessResult() {
        super(true);
    }

    public SuccessResult(String message) {
        super(true, message);
    }
}

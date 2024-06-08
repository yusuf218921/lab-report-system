package com.bigbox.labreports.system.core.results;

import lombok.Getter;
import org.springframework.boot.autoconfigure.security.oauth2.resource.IssuerUriCondition;

@Getter
public class ErrorDataResult<T> extends DataResult<T> {
    public ErrorDataResult(T data, String message) {
        super(data, false, message);
    }

    public ErrorDataResult(T data) {
        super(data, false);
    }

    public ErrorDataResult(String message) {
        super(false,message);
    }

    public ErrorDataResult() {
        super(true);
    }
}

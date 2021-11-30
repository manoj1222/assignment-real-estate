package com.assignment.real.estate.exceptions;

import org.springframework.core.NestedRuntimeException;

public class BizException extends NestedRuntimeException {

    public BizException(String msg) {
        super(msg);
    }

    public BizException(String msg, Throwable cause) {
        super(msg, cause);
    }

}

package com.smartlogis.common.exception;

import org.springframework.http.HttpStatus;

public interface MessageCode {
    String getCode();
    HttpStatus getStatus();
}

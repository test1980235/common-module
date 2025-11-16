package com.smartlogis.common.presentation;

import com.smartlogis.common.infrastructure.MessageResolver;
import io.micrometer.common.util.StringUtils;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ApiResponse<T> {
    private final boolean success;
    private final String messageCode;
    private final String message;
    private final T data;

    public static <T> ApiResponse<T> success() {
        return successWithMessageOnly(null);
    }

    public static <T> ApiResponse<T> success(final String messageCode, final T data) {
        String message = getMessage(messageCode);
        return new ApiResponse<>(true, messageCode, message, data);
    }

    public static <T> ApiResponse<T> successWithDataOnly(final T data) {
        return success("", data);
    }

    public static <T> ApiResponse<T> successWithMessageOnly(final String messageCode) {
        return success(messageCode, null);
    }

    public static <T> ApiResponse<T> successWithMessageOnly(final String messageCode, final Object... messageArguments) {
        return success(messageCode, null, messageArguments);
    }

    public static <T> ApiResponse<T> success(final String messageCode, final T data, final Object... messageArguments) {
        String message = getMessage(messageCode, messageArguments);
        return new ApiResponse<>(true, messageCode, message, data);
    }

    public static <T> ApiResponse<T> failure(final String messageCode) {
        String message = getMessage(messageCode);
        return new ApiResponse<>(false, messageCode, message, null);
    }

    public static <T> ApiResponse<T> failure(final String messageCode, final Object... messageArguments) {
        String message = getMessage(messageCode, messageArguments);
        return new ApiResponse<>(false, messageCode, message, null);
    }

    @Override
    public String toString() {
        return "ApiResponse{" +
                "success=" + success +
                ", messageCode='" + messageCode + '\'' +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }

    private static String getMessage(final String messageCode) {
        String message = "";
        if (StringUtils.isNotBlank(messageCode)) {
            message = MessageResolver.getMessage(messageCode);
        }
        return message;
    }

    private static String getMessage(final String messageCode, final Object... messageArguments) {
        String message = "";
        if (StringUtils.isNotBlank(messageCode)) {
            message = MessageResolver.getMessage(messageCode, messageArguments);
        }
        return message;
    }
}

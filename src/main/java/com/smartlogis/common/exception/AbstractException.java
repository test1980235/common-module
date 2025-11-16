package com.smartlogis.common.exception;

import com.smartlogis.common.infrastructure.MessageResolver;
import lombok.Getter;

import java.io.Serial;

@Getter
public abstract class AbstractException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 4395394977789012707L;

    private final transient MessageCode messageCode;

    private final transient Object[] messageArguments;

    protected AbstractException(final MessageCode messageCode) {
        super(MessageResolver.getMessage(messageCode.getCode()));
        this.messageCode = messageCode;
        this.messageArguments = new Object[0];
    }

    protected AbstractException(final MessageCode messageCode, final Object... messageArguments){
        super(getMessage(messageCode, messageArguments));
        this.messageCode = messageCode;
        this.messageArguments = messageArguments;
    }

    protected AbstractException(final MessageCode messageCode, final Throwable cause) {
        super(MessageResolver.getMessage(messageCode.getCode()), cause);
        this.messageCode = messageCode;
        this.messageArguments = new Object[0];
    }

    protected AbstractException(final MessageCode messageCode, final Throwable cause, final Object... messageArguments){
        super(MessageResolver.getMessage(messageCode.getCode(), messageArguments), cause);
        this.messageCode = messageCode;
        this.messageArguments = messageArguments;
    }

    private static String getMessage(MessageCode code, Object... messageArguments) {
        return MessageResolver.hasFormatParameters(code.getCode()) ? MessageResolver.getMessage(code.getCode(), messageArguments) : messageArguments[0].toString();
    }
}

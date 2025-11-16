package com.smartlogis.common.infrastructure;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;

import java.text.MessageFormat;
import java.util.Locale;

public class MessageResolver {
    private final MessageSource source;

    private static final String LANG_CD = "ko";

    private static MessageSource messageSource;

    private static Locale locale;

    @Autowired
    public MessageResolver(MessageSource source) {
        this.source = source;
    }

    @PostConstruct
    public synchronized void initialize() {
        messageSource = source;
        locale = Locale.of(LANG_CD);
    }

    public static String getMessage(String messageCode) {
        return messageSource.getMessage(messageCode, null, locale);
    }

    public static String getMessage(String messageCode, Object... messageArguments) {
        return messageSource.getMessage(messageCode, messageArguments, locale);
    }

    public static boolean hasFormatParameters(String messageCode) {
        try {
            String rawMessage = messageSource.getMessage(messageCode, null, locale);
            MessageFormat format = new MessageFormat(rawMessage, locale);
            return format.getFormats().length > 0;
        } catch (NoSuchMessageException e) {
            return false;
        }
    }
}

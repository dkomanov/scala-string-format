package com.komanov.stringformat;

import org.slf4j.helpers.MessageFormatter;

import java.text.MessageFormat;

public class JavaFormats {
    private static final MessageFormat messageFormatInstance = new MessageFormat("{0}a{1}b{2}{3}");

    public static String concat(int value1, String value2, Object nullObject) {
        return value1 + "a" + value2 + "b" + value2 + nullObject;
    }

    public static String messageFormat(int value1, String value2, Object nullObject) {
        return MessageFormat.format("{0}a{1}b{2}{3}", value1, value2, value2, nullObject);
    }

    public static String messageFormatCached(int value1, String value2, Object nullObject) {
        return messageFormatInstance.format(new Object[]{value1, value2, value2, nullObject});
    }

    public static String slf4j(int value1, String value2, Object nullObject) {
        return MessageFormatter
                .arrayFormat("{}a{}b{}{}", new Object[]{value1, value2, value2, nullObject})
                .getMessage();
    }
}
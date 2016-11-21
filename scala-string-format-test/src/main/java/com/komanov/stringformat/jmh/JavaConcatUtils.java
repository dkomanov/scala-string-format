package com.komanov.stringformat.jmh;

public class JavaConcatUtils {

    public static String javaConcat(int value1, String value2, Object nullObject) {
        return value1 + "a" + value2 + "b" + value2 + nullObject;
    }
}

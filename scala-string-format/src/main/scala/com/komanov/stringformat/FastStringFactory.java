package com.komanov.stringformat;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class FastStringFactory {

    private static MethodHandle getValue = getValueHandler();
    private static MethodHandle newString = getNewStringHandler();

    public static String fastNewString(StringBuilder sb) throws Throwable {
        if (sb.capacity() != sb.length()) {
            throw new IllegalArgumentException("Expected filled StringBuilder!");
        }

        char[] chars = (char[]) getValue.invoke(sb);
        return fastNewString(chars);
    }

    public static String fastNewString(char[] chars) throws Throwable {
        return (String) newString.invokeExact(chars, true);
    }

    private static MethodHandle getValueHandler() {
        try {
            Method method = StringBuilder.class.getSuperclass().getDeclaredMethod("getValue");
            method.setAccessible(true);
            return MethodHandles.lookup().unreflect(method);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static MethodHandle getNewStringHandler() {
        try {
            Constructor<String> constructor = String.class.getDeclaredConstructor(char[].class, boolean.class);
            constructor.setAccessible(true);
            return MethodHandles.lookup().unreflectConstructor(constructor);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

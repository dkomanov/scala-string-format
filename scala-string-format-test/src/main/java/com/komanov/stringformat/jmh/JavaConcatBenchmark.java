package com.komanov.stringformat.jmh;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Param;

public class JavaConcatBenchmark extends BenchmarkBase {

    @Param({"1", "10000"})
    public int value1 = 1;
    @Param({"", "string__10", "string___________________________________________________________________________________________100"})
    public String value2 = "";
    public Object nullObject = null;

    @Benchmark
    public String javaConcat() {
        return value1 + "a" + value2 + "b" + value2 + nullObject;
    }
}

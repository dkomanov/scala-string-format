package com.komanov.stringformat.jmh

import java.util.concurrent.TimeUnit

import com.komanov.stringformat.{InputArg, JavaFormats, ScalaFormats}
import org.openjdk.jmh.annotations._

@State(Scope.Benchmark)
@BenchmarkMode(Array(Mode.AverageTime))
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Fork(value = 2, jvmArgs = Array("-Xmx2G"))
@Measurement(iterations = 7, time = 3, timeUnit = TimeUnit.SECONDS)
@Warmup(iterations = 2, time = 2, timeUnit = TimeUnit.SECONDS)
abstract class BenchmarkBase

class EmptyStringBenchmark extends BenchmarkBase {

  @Benchmark
  def baseline: String = {
    ""
  }

  @Benchmark
  def sInterpolator: String = {
    s""
  }

  @Benchmark
  def sfiInterpolator: String = {
    import com.komanov.stringformat.macros.MacroConcat._
    sfi""
  }
}

class ConstStringBenchmark extends BenchmarkBase {

  @Benchmark
  def baseline: String = {
    "abc"
  }

  @Benchmark
  def sInterpolator: String = {
    s"abc"
  }

  @Benchmark
  def sfiInterpolator: String = {
    import com.komanov.stringformat.macros.MacroConcat._
    sfi"abc"
  }
}

class ManyParamsBenchmark extends BenchmarkBase {

  @Param
  var arg: InputArg = InputArg.Tiny

  var nullObject: Object = null

  @Benchmark
  def javaConcat(): String = {
    JavaFormats.concat(arg.value1, arg.value2, nullObject)
  }

  @Benchmark
  def scalaConcat(): String = {
    ScalaFormats.concat(arg.value1, arg.value2, nullObject)
  }

  @Benchmark
  def messageFormat(): String = {
    JavaFormats.messageFormat(arg.value1, arg.value2, nullObject)
  }

  @Benchmark
  def messageFormatCached(): String = {
    JavaFormats.messageFormatCached(arg.value1, arg.value2, nullObject)
  }

  @Benchmark
  def slf4j(): String = {
    JavaFormats.slf4j(arg.value1, arg.value2, nullObject)
  }

  @Benchmark
  def concatOptimized(): String = {
    ScalaFormats.optimizedConcat(arg.value1, arg.value2, nullObject)
  }

  @Benchmark
  def sInterpolator(): String = {
    ScalaFormats.sInterpolator(arg.value1, arg.value2, nullObject)
  }

  @Benchmark
  def fInterpolator(): String = {
    ScalaFormats.fInterpolator(arg.value1, arg.value2, nullObject)
  }

  @Benchmark
  def rawInterpolator(): String = {
    ScalaFormats.rawInterpolator(arg.value1, arg.value2, nullObject)
  }

  @Benchmark
  def sfiInterpolator(): String = {
    ScalaFormats.sfiInterpolator(arg.value1, arg.value2, nullObject)
  }

}

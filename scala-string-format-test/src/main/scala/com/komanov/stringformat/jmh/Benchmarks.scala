package com.komanov.stringformat.jmh

import java.util.concurrent.TimeUnit

import com.komanov.stringformat.{JavaFormats, ScalaFormats}
import org.openjdk.jmh.annotations._

@State(Scope.Benchmark)
@BenchmarkMode(Array(Mode.AverageTime))
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Fork(value = 1, jvmArgs = Array("-Xmx2G"))
@Measurement(iterations = 2, time = 3, timeUnit = TimeUnit.SECONDS)
@Warmup(iterations = 2, time = 2, timeUnit = TimeUnit.SECONDS)
abstract class BenchmarkBase

class ManyParamsBenchmark extends BenchmarkBase {

  @Param(Array("1", "10000"))
  var value1: Int = 1
  @Param(Array("", "string__10", "string___________________________________________________________________________________________100", "string______________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________495"))
  var value2: String = ""

  var nullObject: Object = null

  @Benchmark
  def javaConcat(): String = {
    JavaFormats.concat(value1, value2, nullObject)
  }

  @Benchmark
  def scalaConcat(): String = {
    ScalaFormats.concat(value1, value2, nullObject)
  }

  @Benchmark
  def messageFormat(): String = {
    JavaFormats.messageFormat(value1, value2, nullObject)
  }

  @Benchmark
  def messageFormatCached(): String = {
    JavaFormats.messageFormatCached(value1, value2, nullObject)
  }

  @Benchmark
  def slf4j(): String = {
    JavaFormats.slf4j(value1, value2, nullObject)
  }

  @Benchmark
  def concatOptimized(): String = {
    ScalaFormats.optimizedConcat(value1, value2, nullObject)
  }

  @Benchmark
  def sInterpolator(): String = {
    ScalaFormats.sInterpolator(value1, value2, nullObject)
  }

  @Benchmark
  def fInterpolator(): String = {
    ScalaFormats.fInterpolator(value1, value2, nullObject)
  }

  @Benchmark
  def rawInterpolator(): String = {
    ScalaFormats.rawInterpolator(value1, value2, nullObject)
  }

}

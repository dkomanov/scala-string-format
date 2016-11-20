#!/bin/sh -e

ec() {
    echo $* 1>&2
    $*
}

ec mvn clean install

ec java -jar scala-string-format-test/target/benchmarks.jar -rf json -rff jmh-result.json > jmh.log

ec mv jmh-result.json docs/

echo "Don't forget to push docs!"

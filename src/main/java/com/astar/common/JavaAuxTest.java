package com.astar.common;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.util.concurrent.TimeUnit;


@BenchmarkMode(Mode.AverageTime) // Measure average time per operation
@OutputTimeUnit(TimeUnit.NANOSECONDS) // Report results in nanoseconds for fine-grained timing
@State(Scope.Benchmark)
// The benchmark instance (and its fields) will be shared across threads for all iterations
@Fork(value = 2, jvmArgs = {"-Xms2G", "-Xmx2G"}) // Run 2 JVM forks, each with 2GB min/max heap
@Warmup(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS) // 5 warmup iterations, 1 second each
@Measurement(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
public class JavaAuxTest {
    @Param({"60000", "600000"})
    public int loopIterations;

    public static void main(String[] args) {

    }

    @Benchmark
    public void testStringPlusConcat(Blackhole bh) {
        String a = "";
        for (int i = 0; i < loopIterations; i++) {
            a = a + "asdasd" + i;
        }
        bh.consume(a);
    }

    @Benchmark
    public void testStringBuilderConcat(Blackhole bh) {
        StringBuilder b = new StringBuilder();
        for (int i = 0; i < loopIterations; i++) {
            b.append("asdasd").append(i);
        }
        bh.consume(b.toString());
    }
}

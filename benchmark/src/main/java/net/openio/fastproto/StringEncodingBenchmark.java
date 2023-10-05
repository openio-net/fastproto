/**
 * Licensed to the OpenIO.Net under one or more
 * contributor license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.openio.fastproto;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.ByteBufUtil;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

//Test class from lightproto :
// https://github.com/splunk/lightproto/blob/master/benchmark/src/main/java/com/github/splunk/lightproto/benchmark/StringEncodingBenchmark.java

@State(Scope.Benchmark)
@Warmup(iterations = 3)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@Measurement(iterations = 10)
@Fork(value = 1)
public class StringEncodingBenchmark {

    private static final String TEST_STRING = "UTF16 Ελληνικά Русский 日本語";
    private static final String TEST_STRING_ASCII = "Neque porro quisquam est qui dolorem ipsum";

    @Benchmark
    public void jdkEncoding(Blackhole bh) {
        byte[] bytes = TEST_STRING.getBytes(StandardCharsets.UTF_8);
        bh.consume(bytes);
    }

    ByteBuf buffer = ByteBufAllocator.DEFAULT.buffer(1<<10);

    @Benchmark
    public void nettyEncoding(Blackhole bh) {
        buffer.clear();
        ByteBufUtil.writeUtf8(buffer, TEST_STRING);
        bh.consume(buffer);
    }

    @Benchmark
    public void jdkEncodingAscii(Blackhole bh) {
        byte[] bytes = TEST_STRING_ASCII.getBytes(StandardCharsets.UTF_8);
        bh.consume(bytes);
    }

    @Benchmark
    public void nettyEncodingAscii(Blackhole bh) {
        buffer.clear();
        ByteBufUtil.writeUtf8(buffer, TEST_STRING_ASCII);
        bh.consume(buffer);
    }
}

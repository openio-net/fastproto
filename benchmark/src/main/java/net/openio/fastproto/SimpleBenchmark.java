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

import com.Frame;
import com.Point;
import com.google.protobuf.CodedOutputStream;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.buffer.Unpooled;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
@Warmup(iterations = 3)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@Measurement(iterations = 3)
@Fork(value = 1)
public class SimpleBenchmark {

    final static byte[] serialized;

    static {
        Point.PointBuild b = Point.newBuilder();
        b.setX(1);
        b.setY(2);
        b.setZ(3);

        Frame.FrameBuild frameBuilder = Frame.newBuilder();
        frameBuilder.setName("xyz");
        frameBuilder.setPoint(b.build());

        Frame frame = frameBuilder.build();
        int size = frame.getByteSize();

        serialized = new byte[size];
        CodedOutputStream s = CodedOutputStream.newInstance(serialized);
        ByteBuf buf=Unpooled.wrappedBuffer(serialized);
        frame.encode(buf);
    }

    byte[] data = new byte[1024];
    Frame frame = new Frame();
    ByteBuf buffer = PooledByteBufAllocator.DEFAULT.buffer(1024);
    private final ByteBuf serializeByteBuf = Unpooled.wrappedBuffer(serialized);

    @Benchmark
    public void protobufSerialize(Blackhole bh) throws Exception {
        Point.PointBuild b = Point.newBuilder();
        b.setX(1);
        b.setY(2);
        b.setZ(3);

        Frame.FrameBuild frameBuilder = Frame.newBuilder();
        frameBuilder.setName("xyz");
        frameBuilder.setPoint(b.build());

        Frame frame = frameBuilder.build();

        CodedOutputStream s = CodedOutputStream.newInstance(data);
        ByteBuf buf=Unpooled.wrappedBuffer(data);
        frame.encode(buf);
        bh.consume(b);
        bh.consume(s);
        bh.consume(frame);
    }

    @Benchmark
    public void fastProtoSerialize(Blackhole bh) {
        Frame.FrameBuild f = Frame.newBuilder();
        f.clear();
        Point.PointBuild p = Point.newBuilder();
        p.setX(1);
        p.setY(2);
        p.setZ(3);
        f.setName("xyz");

        p.build().encode(buffer);
        buffer.clear();

        bh.consume(p);
    }

    @Benchmark
    public void protobufDeserialize(Blackhole bh) throws Exception {
        Frame b = Frame.decode(serializeByteBuf, serializeByteBuf.readableBytes());
        Frame f = b;
        f.getName();
        bh.consume(f);
    }

    @Benchmark
    public void fastProtoDeserialize(Blackhole bh) {
        frame.encode(serializeByteBuf);
        serializeByteBuf.resetReaderIndex();
        bh.consume(frame);
    }

    @Benchmark
    public void lightProtoDeserializeReadString(Blackhole bh) {
        frame.encode(serializeByteBuf);
        bh.consume(frame.getName());
        serializeByteBuf.resetReaderIndex();
    }

}

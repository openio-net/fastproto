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
package Test;

import com.A;
import com.I;
import com.X;
import com.google.protobuf.InvalidProtocolBufferException;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ITest {

    @org.junit.Test
    public void testI() throws InvalidProtocolBufferException {
        A a = A.newBuilder().addB(-123)
                .addB(-1234)
                .addB(12345)
                .addB(123456)
                .setA(1234567).build();

        A a1 = A.newBuilder().addB(12312)
                .addB(123412)
                .addB(-1234512)
                .addB(0)
                .setA(12345612).build();

        I i = I.newBuilder().putB(1, a)
                .putB(2, a1).build();

        ByteBuf byteBuf = Unpooled.buffer(i.getByteSize());
        i.encode(byteBuf);
        X.I i1 = X.I.parseFrom(byteBuf.array());
        assertEquals(i.getB(1).getB(0), i1.getBOrThrow(1).getB(0));
        assertEquals(i.getB(1).getB(1), i1.getBOrThrow(1).getB(1));
        assertEquals(i.getB(1).getB(2), i1.getBOrThrow(1).getB(2));
        assertEquals(i.getB(1).getB(3), i1.getBOrThrow(1).getB(3));
        assertEquals(i.getB(1).getA(), i1.getBOrThrow(1).getA());


        assertEquals(i.getB(2).getB(0), i1.getBOrThrow(2).getB(0));
        assertEquals(i.getB(2).getB(1), i1.getBOrThrow(2).getB(1));
        assertEquals(i.getB(2).getB(2), i1.getBOrThrow(2).getB(2));
        assertEquals(i.getB(2).getB(3), i1.getBOrThrow(2).getB(3));
        assertEquals(i.getB(2).getA(), i1.getBOrThrow(2).getA());


    }
}

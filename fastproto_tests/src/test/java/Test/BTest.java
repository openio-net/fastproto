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
import com.B;
import com.X;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BTest {


    @org.junit.Test
    public void testProtoB() {
        A a = A.newBuilder().addB(123456)
                .addB(0)
                .addB(-2)
                .addB(-1)
                .addB(Integer.MAX_VALUE)
                .addB(Integer.MIN_VALUE)
                .setA(Integer.MIN_VALUE).build();

        A a1 = A.newBuilder().addB(1)
                .addB(0)
                .addB(-2)
                .addB(-1)
                .addB(Integer.MAX_VALUE)
                .addB(Integer.MIN_VALUE)
                .setA(Integer.MAX_VALUE).build();

        B b = B.newBuilder().addB(a)
                .addB(a1)
                .build();


        X.B b1 = X.B.newBuilder().addB(
                X.A.newBuilder()
                        .addB(123456)
                        .addB(0)
                        .addB(-1)
                        .addB(-2)
                        .addB(Integer.MAX_VALUE)
                        .addB(Integer.MIN_VALUE)
                        .setA(Integer.MIN_VALUE).build()
        ).addB(
                X.A.newBuilder()
                        .addB(1)
                        .addB(0)
                        .addB(-1)
                        .addB(-2)
                        .addB(Integer.MAX_VALUE)
                        .addB(Integer.MIN_VALUE)
                        .setA(Integer.MAX_VALUE)
                        .build()
        ).build();
        ByteBuf byteBuf = Unpooled.buffer(b.getByteSize());
        byteBuf.writeBytes(b1.toByteArray());
        B b2 = B.decode(byteBuf);
        assertEquals(b1.getSerializedSize(), b.getByteSize());
        assertEquals(b1.getB(0).getA(), b2.getB(0).getA());
        assertEquals(b1.getB(0).getB(0), b2.getB(0).getB(0));
        assertEquals(b1.getB(0).getB(1), b2.getB(0).getB(1));
        assertEquals(b1.getB(0).getB(2), b2.getB(0).getB(2));
        assertEquals(b1.getB(0).getB(3), b2.getB(0).getB(3));
        assertEquals(b1.getB(0).getB(4), b2.getB(0).getB(4));
        assertEquals(b1.getB(0).getB(5), b2.getB(0).getB(5));

        assertEquals(b1.getB(1).getA(), b2.getB(1).getA());
        assertEquals(b1.getB(1).getB(0), b2.getB(1).getB(0));
        assertEquals(b1.getB(1).getB(1), b2.getB(1).getB(1));
        assertEquals(b1.getB(1).getB(2), b2.getB(1).getB(2));
        assertEquals(b1.getB(1).getB(3), b2.getB(1).getB(3));
        assertEquals(b1.getB(1).getB(4), b2.getB(1).getB(4));
        assertEquals(b1.getB(1).getB(5), b2.getB(1).getB(5));
    }
}

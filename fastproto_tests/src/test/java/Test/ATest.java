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
import com.X;
import com.google.protobuf.InvalidProtocolBufferException;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ATest {

    @org.junit.Test
    public void testA() throws InvalidProtocolBufferException {

        X.A a3 = X.A.newBuilder()
                .setA(123456)
                .addB(-123)
                .addB(1234)
                .addB(-12345)
                .addB(Integer.MAX_VALUE)
                .build();
        A a = A.newBuilder().addB(-123)
                .addB(1234)
                .addB(-12345)
                .addB(Integer.MAX_VALUE)
                .setA(123456).build();
        int size = a.getByteSize();
        ByteBuf byteBuf = Unpooled.buffer(a.getByteSize());
        a.encode(byteBuf);

        X.A a1 = X.A.parseFrom(byteBuf.array());
        int size1 = a3.getSerializedSize();
        byteBuf.clear();
        A a2 = A.decode(byteBuf.writeBytes(a1.toByteArray()));
        assertEquals(size1, size);
        assertEquals(a1.getB(0), a2.getB(0));
        assertEquals(a1.getB(1), a2.getB(1));
        assertEquals(a1.getB(2), a2.getB(2));
        assertEquals(a1.getB(3), a2.getB(3));
        assertEquals(a1.getA(), a2.getA());

    }
}

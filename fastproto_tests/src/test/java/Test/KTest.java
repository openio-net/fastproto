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

import com.K;
import com.X;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class KTest {


    @org.junit.Test
    public void testI() {
        X.K j1 = X.K.newBuilder().setH(Long.MIN_VALUE)
                .setI(Integer.MAX_VALUE)
                .setJ(Integer.MIN_VALUE)
                .setK(Long.MIN_VALUE)
                .setL(true).build();

        K j = K.newBuilder()
                .setH(Long.MIN_VALUE)
                .setI(Integer.MAX_VALUE)
                .setJ(Integer.MIN_VALUE)
                .setK(Long.MIN_VALUE)
                .setL(true).build();

        ByteBuf buf = Unpooled.buffer(j1.getSerializedSize());
        buf.writeBytes(j1.toByteArray());
        K j2 = K.decode(buf);
        buf.clear();
        j.encode(buf);
        K j3 = K.decode(buf);
        assertEquals(j1.getSerializedSize(), j.getByteSize());
        assertEquals(j2.getH(), j3.getH());
        assertEquals(j2.getI(), j3.getI());
        assertEquals(j2.getJ(), j3.getJ());
        assertEquals(j2.getK(), j3.getK());
        assertEquals(j2.getL(), j3.getL());
    }

}

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

import com.J;
import com.X;
import com.google.protobuf.ByteString;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JTest {


    @org.junit.Test
    public void testI() {
        X.J j1 = X.J.newBuilder().setH(Long.MIN_VALUE)
                .setI(Integer.MAX_VALUE)
                .setJ(Long.MIN_VALUE)
                .setK("hello")
                .setL(ByteString.copyFromUtf8("hello")).build();

        J j = J.newBuilder()
                .setH(Long.MIN_VALUE)
                .setI(Integer.MAX_VALUE)
                .setJ(Long.MIN_VALUE)
                .setK("hello")
                .setL(ByteString.copyFromUtf8("hello").toByteArray()).build();

        ByteBuf buf = Unpooled.buffer(j1.getSerializedSize());
        buf.writeBytes(j1.toByteArray());
        J j2 = J.decode(buf);
        buf.clear();
        j.encode(buf);
        J j3 = J.decode(buf);
        assertEquals(j2.getH(), j3.getH());
        assertEquals(j2.getI(), j3.getI());
        assertEquals(j2.getJ(), j3.getJ());
        assertEquals(j2.getK(), j3.getK());
        assertEquals(new String(j2.getL(), StandardCharsets.UTF_8), new String(j3.getL(), StandardCharsets.UTF_8));
    }

}

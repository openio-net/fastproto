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

import com.N;
import com.X;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.junit.jupiter.api.Assertions;

public class OTest {

    @org.junit.Test
    public void testN() {
        X.N j1 = X.N.newBuilder().addH(Long.MIN_VALUE)
                .addH(Long.MAX_VALUE)
                .addH(0)
                .addI(Integer.MAX_VALUE)
                .addI(Integer.MIN_VALUE)
                .addI(0)
                .addJ(Integer.MIN_VALUE)
                .addJ(Integer.MAX_VALUE)
                .addJ(0)
                .addK(Long.MIN_VALUE)
                .addK(Long.MAX_VALUE)
                .addK(0)
                .addL(true)
                .addL(false).build();


        N j = N.newBuilder()
                .addH(Long.MIN_VALUE)
                .addH(Long.MAX_VALUE)
                .addH(0L)
                .addI(Integer.MAX_VALUE)
                .addI(Integer.MIN_VALUE)
                .addI(0)
                .addJ(Integer.MIN_VALUE)
                .addJ(Integer.MAX_VALUE)
                .addJ(0)
                .addK(Long.MIN_VALUE)
                .addK(Long.MAX_VALUE)
                .addK(0L)
                .addL(true)
                .addL(false)
                .build();

        ByteBuf buf = Unpooled.buffer(j1.getSerializedSize());
        buf.writeBytes(j1.toByteArray());
        N j2 = N.decode(buf);
        buf.clear();
        j.encode(buf);
        N j3 = N.decode(buf);
        Assertions.assertEquals(j1.getSerializedSize(), j.getByteSize());
        Assertions.assertEquals(j2.getH(0), j3.getH(0));
        Assertions.assertEquals(j2.getH(1), j3.getH(1));
        Assertions.assertEquals(j2.getH(2), j3.getH(2));
        Assertions.assertEquals(j2.getI(0), j3.getI(0));
        Assertions.assertEquals(j2.getI(1), j3.getI(1));
        Assertions.assertEquals(j2.getI(2), j3.getI(2));
        Assertions.assertEquals(j2.getJ(0), j3.getJ(0));
        Assertions.assertEquals(j2.getJ(1), j3.getJ(1));
        Assertions.assertEquals(j2.getJ(2), j3.getJ(2));
        Assertions.assertEquals(j2.getK(0), j3.getK(0));
        Assertions.assertEquals(j2.getK(1), j3.getK(1));
        Assertions.assertEquals(j2.getK(2), j3.getK(2));
        Assertions.assertEquals(j2.getL(0), j3.getL(0));
        Assertions.assertEquals(j2.getL(1), j3.getL(1));
    }
}

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
import com.P;
import com.X;
import com.google.protobuf.InvalidProtocolBufferException;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.junit.jupiter.api.Assertions;

public class PTest {

  @org.junit.Test
  public void testP() throws InvalidProtocolBufferException {
    A a = A.newBuilder().addB(-123)
      .addB(1234)
      .addB(-12345)
      .addB(Integer.MAX_VALUE)
      .setA(123456).build();

    A a1 = A.newBuilder().addB(-13)
      .addB(134)
      .addB(-1235)
      .addB(Integer.MAX_VALUE)
      .setA(12345).build();

    X.A a2 = X.A.newBuilder()
      .setA(123456)
      .addB(-123)
      .addB(1234)
      .addB(-12345)
      .addB(Integer.MAX_VALUE)
      .build();

    X.A a3 = X.A.newBuilder()
      .setA(12345)
      .addB(-13)
      .addB(134)
      .addB(-1235)
      .addB(Integer.MAX_VALUE)
      .build();

    P p=P.newBuilder().setA("name1")
      .addB("name2")
      .addB("name3")
      .setC(a)
      .addD(a1).build();

    X.P p1=X.P.newBuilder().setA("name1")
      .addB("name2")
      .addB("name3")
      .setC(a2)
      .addD(a3).build();

    ByteBuf buf = Unpooled.buffer(p1.getSerializedSize());
    buf.writeBytes(p1.toByteArray());
    P p3= P.decode(buf);
    buf.clear();
    p.encode(buf);
    X.P p4 =X.P.parseFrom(buf.array());
    Assertions.assertEquals(p1.getSerializedSize(), p.getByteSize());
    Assertions.assertEquals(p3.getA(),p4.getA());
    Assertions.assertEquals(p3.getC().getA(),p4.getC().getA());
    Assertions.assertEquals(p3.getB(0),p4.getB(0));
    Assertions.assertEquals(p3.getB(1),p4.getB(1));
  }
}

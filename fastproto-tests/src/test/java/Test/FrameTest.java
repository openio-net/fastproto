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

import com.Frame;
import com.Point;
import com.X;
import com.google.protobuf.CodedOutputStream;
import com.google.protobuf.InvalidProtocolBufferException;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.junit.jupiter.api.Assertions;

public class FrameTest {

  @org.junit.Test
  public void frameTest() throws InvalidProtocolBufferException {


    Point.PointBuild b = Point.newBuilder();
    b.setX(1);
    b.setY(2);
    b.setZ(3);

    Frame.FrameBuild frameBuilder = Frame.newBuilder();
    frameBuilder.setName("xyz");
    frameBuilder.setPoint(b.build());

    Frame frame = frameBuilder.build();

    ByteBuf buf= Unpooled.buffer(frame.getByteSize());
    buf.resetWriterIndex();
    frame.encode(buf);

    X.Frame frame1=X.Frame.parseFrom(buf.array());
    Assertions.assertEquals(frame1.getSerializedSize(),frame.getByteSize());
    Assertions.assertEquals(frame1.getName(),frame.getName());

  }
}

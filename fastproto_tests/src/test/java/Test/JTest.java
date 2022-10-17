package java.Test;

import com.J;
import com.X;
import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JTest {


    @org.junit.Test
    public void testI() throws InvalidProtocolBufferException {//序列化反序列化测试
        X.J j1=X.J.newBuilder().setH(Long.MIN_VALUE)
                .setI(Integer.MAX_VALUE)
                .setJ(Long.MIN_VALUE)
                .setK("hello")
                .setL(ByteString.copyFromUtf8("hello")).build();

        J j =J.newBuilder()
                .setH(Long.MIN_VALUE)
                .setI(Integer.MAX_VALUE)
                .setJ(Long.MIN_VALUE)
                .setK("hello")
                .setL(ByteString.copyFromUtf8("hello").toByteArray()).build();

        ByteBuf buf= Unpooled.buffer(j1.getSerializedSize());
        buf.writeBytes(j1.toByteArray());
        J j2=J.decode(buf);
        buf.clear();
        j.encode(buf);
        J j3=J.decode(buf);
        assertEquals(j2.getH(),j3.getH());
        assertEquals(j2.getI(),j3.getI());
        assertEquals(j2.getJ(),j3.getJ());
        assertEquals(j2.getK(),j3.getK());
        assertEquals(new String(j2.getL(), StandardCharsets.UTF_8),new String(j3.getL(), StandardCharsets.UTF_8));
    }

}

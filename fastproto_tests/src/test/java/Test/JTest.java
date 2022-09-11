package Test;

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
                .set_h(Long.MIN_VALUE)
                .set_i(Integer.MAX_VALUE)
                .set_j(Long.MIN_VALUE)
                .set_k("hello")
                .set_l(ByteString.copyFromUtf8("hello").toByteArray()).build();

        ByteBuf buf= Unpooled.buffer(j1.getSerializedSize());
        buf.writeBytes(j1.toByteArray());
        J j2=J.decode(buf);
        buf.clear();
        j.encode(buf);
        J j3=J.decode(buf);
        assertEquals(j2.get_h(),j3.get_h());
        assertEquals(j2.get_i(),j3.get_i());
        assertEquals(j2.get_j(),j3.get_j());
        assertEquals(j2.get_k(),j3.get_k());
        assertEquals(new String(j2.get_l(), StandardCharsets.UTF_8),new String(j3.get_l(), StandardCharsets.UTF_8));
    }

}

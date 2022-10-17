package java.Test;

import com.L;
import com.X;
import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LTest {

    @org.junit.Test
    public void testL() throws InvalidProtocolBufferException {//序列化反序列化测试
        L l= L.newBuilder()
                .addH(Long.MAX_VALUE)
                .addH(Long.MIN_VALUE)
                .addI(Integer.MAX_VALUE)
                .addI(Integer.MIN_VALUE)
                .addJ(Long.MAX_VALUE)
                .addJ(Long.MIN_VALUE)
                .addK("hello1")
                .addK("hello2")
                .addL(ByteString.copyFromUtf8("hello1").toByteArray())
                .addL(ByteString.copyFromUtf8("hello2").toByteArray())
                .build();




        X.L l1=X.L.newBuilder()
                .addH(Long.MAX_VALUE)
                .addH(Long.MIN_VALUE)
                .addI(Integer.MAX_VALUE)
                .addI(Integer.MIN_VALUE)
                .addJ(Long.MAX_VALUE)
                .addJ(Long.MIN_VALUE)
                .addK("hello1")
                .addK("hello2")
                .addL(ByteString.copyFromUtf8("hello1"))
                .addL(ByteString.copyFromUtf8("hello2"))
                .build();


        ByteBuf buf=Unpooled.buffer(l1.getSerializedSize());

        buf.writeBytes(l1.toByteArray());

        L l2=L.decode(buf);

        buf.clear();

        l.encode(buf);

        L l3=L.decode(buf);

        assertEquals(l2.getH(0),l3.getH(0));
        assertEquals(l2.getH(1),l3.getH(1));
        assertEquals(l2.getI(0),l3.getI(0));
        assertEquals(l2.getI(1),l3.getI(1));
        assertEquals(l2.getJ(0),l3.getJ(0));
        assertEquals(l2.getJ(1),l3.getJ(1));
        assertEquals(l2.getK(0),l3.getK(0));
        assertEquals(l2.getK(1),l3.getK(1));
        assertEquals(new String(l2.getL(0),StandardCharsets.UTF_8),new String(l3.getL(0),StandardCharsets.UTF_8));
        assertEquals(new String(l2.getL(1),StandardCharsets.UTF_8),new String(l3.getL(1),StandardCharsets.UTF_8));
    }


}

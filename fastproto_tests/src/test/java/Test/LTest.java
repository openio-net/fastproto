package Test;

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
                .add_h_value(Long.MAX_VALUE)
                .add_h_value(Long.MIN_VALUE)
                .add_i_value(Integer.MAX_VALUE)
                .add_i_value(Integer.MIN_VALUE)
                .add_j_value(Long.MAX_VALUE)
                .add_j_value(Long.MIN_VALUE)
                .add_k_value("hello1")
                .add_k_value("hello2")
                .add_l_value(ByteString.copyFromUtf8("hello1").toByteArray())
                .add_l_value(ByteString.copyFromUtf8("hello2").toByteArray())
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

        assertEquals(l2.get_h_value(0),l3.get_h_value(0));
        assertEquals(l2.get_h_value(1),l3.get_h_value(1));
        assertEquals(l2.get_i_value(0),l3.get_i_value(0));
        assertEquals(l2.get_i_value(1),l3.get_i_value(1));
        assertEquals(l2.get_j_value(0),l3.get_j_value(0));
        assertEquals(l2.get_j_value(1),l3.get_j_value(1));
        assertEquals(l2.get_k_value(0),l3.get_k_value(0));
        assertEquals(l2.get_k_value(1),l3.get_k_value(1));
        assertEquals(new String(l2.get_l_value(0),StandardCharsets.UTF_8),new String(l3.get_l_value(0),StandardCharsets.UTF_8));
        assertEquals(new String(l2.get_l_value(1),StandardCharsets.UTF_8),new String(l3.get_l_value(1),StandardCharsets.UTF_8));
    }


}

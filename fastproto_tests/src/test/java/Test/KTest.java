package Test;

import com.K;
import com.X;
import com.google.protobuf.InvalidProtocolBufferException;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class KTest {


    @org.junit.Test
    public void testI() throws InvalidProtocolBufferException {//序列化反序列化测试
        X.K j1=X.K.newBuilder().setH(Long.MIN_VALUE)
                .setI(Integer.MAX_VALUE)
                .setJ(Integer.MIN_VALUE)
                .setK(Long.MIN_VALUE)
                .setL(true).build();

        K j =K.newBuilder()
                .set_h(Long.MIN_VALUE)
                .set_i(Integer.MAX_VALUE)
                .set_j(Integer.MIN_VALUE)
                .set_k(Long.MIN_VALUE)
                .set_l(true).build();

        ByteBuf buf= Unpooled.buffer(j1.getSerializedSize());
        buf.writeBytes(j1.toByteArray());
        K j2=K.decode(buf);
        buf.clear();
        j.encode(buf);
        K j3=K.decode(buf);
        assertEquals(j1.getSerializedSize(),j.getByteSize());
        assertEquals(j2.get_h(),j3.get_h());
        assertEquals(j2.get_i(),j3.get_i());
        assertEquals(j2.get_j(),j3.get_j());
        assertEquals(j2.get_k(),j3.get_k());
        assertEquals(j2.get_l(),j3.get_l());
    }

}

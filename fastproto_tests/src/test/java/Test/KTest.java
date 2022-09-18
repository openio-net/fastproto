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
                .setH(Long.MIN_VALUE)
                .setI(Integer.MAX_VALUE)
                .setJ(Integer.MIN_VALUE)
                .setK(Long.MIN_VALUE)
                .setL(true).build();

        ByteBuf buf= Unpooled.buffer(j1.getSerializedSize());
        buf.writeBytes(j1.toByteArray());
        K j2=K.decode(buf);
        buf.clear();
        j.encode(buf);
        K j3=K.decode(buf);
        assertEquals(j1.getSerializedSize(),j.getByteSize());
        assertEquals(j2.getH(),j3.getH());
        assertEquals(j2.getI(),j3.getI());
        assertEquals(j2.getJ(),j3.getJ());
        assertEquals(j2.getK(),j3.getK());
        assertEquals(j2.getL(),j3.getL());
    }

}

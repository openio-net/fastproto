package Test;

import com.N;
import com.X;
import com.google.protobuf.InvalidProtocolBufferException;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NTest {


    @org.junit.Test
    public void testN() throws InvalidProtocolBufferException {//序列化反序列化测试
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
        assertEquals(j1.getSerializedSize(), j.getByteSize());
        assertEquals(j2.getH(0), j3.getH(0));
        assertEquals(j2.getH(1), j3.getH(1));
        assertEquals(j2.getH(2), j3.getH(2));
        assertEquals(j2.getI(0), j3.getI(0));
        assertEquals(j2.getI(1),j3.getI(1));
        assertEquals(j2.getI(2),j3.getI(2));
        assertEquals(j2.getJ(0),j3.getJ(0));
        assertEquals(j2.getJ(1),j3.getJ(1));
        assertEquals(j2.getJ(2),j3.getJ(2));
        assertEquals(j2.getK(0),j3.getK(0));
        assertEquals(j2.getK(1),j3.getK(1));
        assertEquals(j2.getK(2),j3.getK(2));
        assertEquals(j2.getL(0),j3.getL(0));
        assertEquals(j2.getL(1),j3.getL(1));
    }
}

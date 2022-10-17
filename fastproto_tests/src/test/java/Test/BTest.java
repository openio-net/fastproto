package java.Test;

import com.A;
import com.B;
import com.X;
import com.google.protobuf.InvalidProtocolBufferException;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BTest {




    @org.junit.Test
    public void testProtoB() throws InvalidProtocolBufferException {//序列化反序列化测试
        A a=A.newBuilder().addB(123456)
                .addB(0)
                .addB(-2)
                .addB(-1)
                .addB(Integer.MAX_VALUE)
                .addB(Integer.MIN_VALUE)
                .setA(Integer.MIN_VALUE).build();

        A a1=A.newBuilder().addB(1)
                .addB(0)
                .addB(-2)
                .addB(-1)
                .addB(Integer.MAX_VALUE)
                .addB(Integer.MIN_VALUE)
                .setA(Integer.MAX_VALUE).build();

        B b= B.newBuilder().addB(a)
                .addB(a1)
                .build();




        X.B b1= X.B.newBuilder().addB(
                X.A.newBuilder()
                        .addB(123456)
                        .addB(0)
                        .addB(-1)
                        .addB(-2)
                        .addB(Integer.MAX_VALUE)
                        .addB(Integer.MIN_VALUE)
                        .setA(Integer.MIN_VALUE).build()
        ).addB(
                X.A.newBuilder()
                        .addB(1)
                        .addB(0)
                        .addB(-1)
                        .addB(-2)
                        .addB(Integer.MAX_VALUE)
                        .addB(Integer.MIN_VALUE)
                        .setA(Integer.MAX_VALUE)
                        .build()
        ).build();
        ByteBuf byteBuf= Unpooled.buffer(b.getByteSize());
        byteBuf.writeBytes(b1.toByteArray());
        B b2=B.decode(byteBuf);
        assertEquals(b1.getSerializedSize(),b.getByteSize());
        assertEquals(b1.getB(0).getA(),b2.getB(0).getA());
        assertEquals(b1.getB(0).getB(0),b2.getB(0).getB(0));
        assertEquals(b1.getB(0).getB(1),b2.getB(0).getB(1));
        assertEquals(b1.getB(0).getB(2),b2.getB(0).getB(2));
        assertEquals(b1.getB(0).getB(3),b2.getB(0).getB(3));
        assertEquals(b1.getB(0).getB(4),b2.getB(0).getB(4));
        assertEquals(b1.getB(0).getB(5),b2.getB(0).getB(5));

        assertEquals(b1.getB(1).getA(),b2.getB(1).getA());
        assertEquals(b1.getB(1).getB(0),b2.getB(1).getB(0));
        assertEquals(b1.getB(1).getB(1),b2.getB(1).getB(1));
        assertEquals(b1.getB(1).getB(2),b2.getB(1).getB(2));
        assertEquals(b1.getB(1).getB(3),b2.getB(1).getB(3));
        assertEquals(b1.getB(1).getB(4),b2.getB(1).getB(4));
        assertEquals(b1.getB(1).getB(5),b2.getB(1).getB(5));
    }
}

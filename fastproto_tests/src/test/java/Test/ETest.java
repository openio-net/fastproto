package Test;

import com.*;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ETest {


    @org.junit.Test
    public void testProtoE() {//序列化反序列化测试
        A a = A.newBuilder().addB(123456)
                .addB(0)
                .addB(-2)
                .addB(-1)
                .addB(Integer.MAX_VALUE)
                .addB(Integer.MIN_VALUE)
                .setA(Integer.MIN_VALUE).build();

        A a1 = A.newBuilder().addB(1)
                .addB(0)
                .addB(-2)
                .addB(-1)
                .addB(Integer.MAX_VALUE)
                .addB(Integer.MIN_VALUE)
                .setA(Integer.MAX_VALUE).build();

        B b = B.newBuilder().addB(a)
                .addB(a1)
                .build();


        X.B b1 = X.B.newBuilder().addB(
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

        D d = D.newBuilder()
                .putB(123, 123L)
                .putB(Integer.MIN_VALUE, Long.MAX_VALUE)
                .putB(Integer.MAX_VALUE, Long.MIN_VALUE)
                .build();

        D d1 = D.newBuilder()
                .putB(1234, 1234L)
                .putB(Integer.MIN_VALUE, Long.MAX_VALUE)
                .putB(Integer.MAX_VALUE, Long.MIN_VALUE)
                .build();

        X.D d2 = X.D.newBuilder().putB(123, 123)
                .putB(Integer.MIN_VALUE, Long.MAX_VALUE)
                .putB(Integer.MAX_VALUE, Long.MIN_VALUE).build();


        X.D d3 = X.D.newBuilder().putB(1234, 1234)
                .putB(Integer.MIN_VALUE, Long.MAX_VALUE)
                .putB(Integer.MAX_VALUE, Long.MIN_VALUE).build();

        E e = E.newBuilder()
                .addB(b)
                .putA(12, d)
                .putA(13, d1)
                .setOneOf0FValue(1L)
                .setOneOf0CValue(2)
                .build();


        X.E e1 = X.E.newBuilder()
                .addB(b1)
                .putA(12, d2)
                .putA(13, d3)
                .setF(1L)
                .setC(2)
                .build();
        ByteBuf buf = Unpooled.buffer(e1.getSerializedSize());
        buf.writeBytes(e1.toByteArray());
        E e2 = E.decode(buf);
        assertEquals(e1.getSerializedSize(), e.getByteSize());
        assertEquals(e1.getC(), e.getOneOf0Value());
        assertEquals(e1.getAOrThrow(12).getBOrThrow(Integer.MAX_VALUE), e.getA(12).getB(Integer.MAX_VALUE));
        assertEquals(e1.getAOrThrow(12).getBOrThrow(Integer.MAX_VALUE), e.getA(12).getB(Integer.MAX_VALUE));
        assertEquals(e1.getAOrThrow(13).getBOrThrow(Integer.MAX_VALUE), e.getA(13).getB(Integer.MAX_VALUE));

        assertEquals(e1.getB(0).getB(0).getB(5), e.getB(0).getB(0).getB(5));
    }


}

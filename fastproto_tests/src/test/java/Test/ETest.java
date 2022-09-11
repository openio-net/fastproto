package Test;

import com.*;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ETest {

    @org.junit.Test
    public void testE(){//序列化反序列化测试
        A a=A.newBuilder().add_b_value(123)
                .add_b_value(1234)
                .add_b_value(12345)
                .add_b_value(123456)
                .set_a(123456).build();
        A a1=A.newBuilder().add_b_value(123)
                .add_b_value(1234)
                .add_b_value(12345)
                .add_b_value(123456)
                .set_a(123456).build();
        D d=D.newBuilder().put_b(13,12L)
                .put_b(14,13L)
                .build();

        D d1=D.newBuilder().put_b(13,12L)
                .put_b(14,13L)
                .build();

        E e=E.newBuilder().put_a(12,d)
                .put_a(13,d1)
                .add_b_value(B.newBuilder().add_b_value(a).add_b_value(a1).build())
                .setOneOf0Valuec(1)
                .setOneOf0Valuef(12L)
                .setOneOf0Valuef(13L)
                .build();

        ByteBuf buf= Unpooled.buffer(e.getByteSize());
        e.encode(buf);
        System.out.println(e.getByteSize());
        System.out.println(buf.writerIndex());
        E e1=E.decode(buf);

        System.out.println(e1.getOneOf0Value());
        System.out.println(e1.get_b_value(0).get_b_value(1).get_b_value(3));
        System.out.println(e1.get_a_value(13).get_b_value(14));

    }


    @org.junit.Test
    public void testProtoE(){//序列化反序列化测试
        A a=A.newBuilder().add_b_value(123456)
                .add_b_value(0)
                .add_b_value(-2)
                .add_b_value(-1)
                .add_b_value(Integer.MAX_VALUE)
                .add_b_value(Integer.MIN_VALUE)
                .set_a(Integer.MIN_VALUE).build();

        A a1=A.newBuilder().add_b_value(1)
                .add_b_value(0)
                .add_b_value(-2)
                .add_b_value(-1)
                .add_b_value(Integer.MAX_VALUE)
                .add_b_value(Integer.MIN_VALUE)
                .set_a(Integer.MAX_VALUE).build();

        com.B b= B.newBuilder().add_b_value(a)
                .add_b_value(a1)
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

        D d=D.newBuilder()
                .put_b(123,123L)
                .put_b(Integer.MIN_VALUE,Long.MAX_VALUE)
                .put_b(Integer.MAX_VALUE,Long.MIN_VALUE)
                .build();

        D d1=D.newBuilder()
                .put_b(1234,1234L)
                .put_b(Integer.MIN_VALUE,Long.MAX_VALUE)
                .put_b(Integer.MAX_VALUE,Long.MIN_VALUE)
                .build();

        X.D d2=X.D.newBuilder().putB(123,123)
                .putB(Integer.MIN_VALUE,Long.MAX_VALUE)
                .putB(Integer.MAX_VALUE,Long.MIN_VALUE).build();


        X.D d3=X.D.newBuilder().putB(1234,1234)
                .putB(Integer.MIN_VALUE,Long.MAX_VALUE)
                .putB(Integer.MAX_VALUE,Long.MIN_VALUE).build();

        E e=E.newBuilder()
                .add_b_value(b)
                .put_a(12,d)
                .put_a(13,d1)
                .setOneOf0Valuef(1L)
                .setOneOf0Valuec(2)
                .build();


        X.E e1=X.E.newBuilder()
                .addB(b1)
                .putA(12,d2)
                .putA(13,d3)
                .setF(1L)
                .setC(2)
                .build();
        ByteBuf buf= Unpooled.buffer(e1.getSerializedSize());
        buf.writeBytes(e1.toByteArray());
        E e2=E.decode(buf);
        assertEquals(e1.getSerializedSize(),e.getByteSize());
        assertEquals(e1.getC(),e.getOneOf0Value());
        assertEquals(e1.getAOrThrow(12).getBOrThrow(Integer.MAX_VALUE),e.get_a_value(12).get_b_value(Integer.MAX_VALUE));
        assertEquals(e1.getAOrThrow(12).getBOrThrow(Integer.MAX_VALUE),e.get_a_value(12).get_b_value(Integer.MAX_VALUE));
        assertEquals(e1.getAOrThrow(13).getBOrThrow(Integer.MAX_VALUE),e.get_a_value(13).get_b_value(Integer.MAX_VALUE));

        assertEquals(e1.getB(0).getB(0).getB(5),e.get_b_value(0).get_b_value(0).get_b_value(5));
    }


}

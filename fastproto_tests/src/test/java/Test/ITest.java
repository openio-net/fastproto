package Test;

import com.A;
import com.I;
import com.X;
import com.google.protobuf.InvalidProtocolBufferException;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ITest {

    @org.junit.Test
    public void testI() throws InvalidProtocolBufferException {//序列化反序列化测试
        A a=A.newBuilder().add_b_value(-123)
                .add_b_value(-1234)
                .add_b_value(12345)
                .add_b_value(123456)
                .set_a(1234567).build();

        A a1=A.newBuilder().add_b_value(12312)
                .add_b_value(123412)
                .add_b_value(-1234512)
                .add_b_value(0)
                .set_a(12345612).build();

        I i= I.newBuilder().put_b(1,a)
                .put_b(2,a1).build();

        ByteBuf byteBuf= Unpooled.buffer(i.getByteSize());
        i.encode(byteBuf);
        X.I i1=X.I.parseFrom(byteBuf.array());
        assertEquals(i.get_b_value(1).get_b_value(0),i1.getBOrThrow(1).getB(0) );
        assertEquals(i.get_b_value(1).get_b_value(1),i1.getBOrThrow(1).getB(1) );
        assertEquals(i.get_b_value(1).get_b_value(2),i1.getBOrThrow(1).getB(2) );
        assertEquals(i.get_b_value(1).get_b_value(3),i1.getBOrThrow(1).getB(3) );
        assertEquals(i.get_b_value(1).get_a(),i1.getBOrThrow(1).getA() );


        assertEquals(i.get_b_value(2).get_b_value(0),i1.getBOrThrow(2).getB(0) );
        assertEquals(i.get_b_value(2).get_b_value(1),i1.getBOrThrow(2).getB(1) );
        assertEquals(i.get_b_value(2).get_b_value(2),i1.getBOrThrow(2).getB(2) );
        assertEquals(i.get_b_value(2).get_b_value(3),i1.getBOrThrow(2).getB(3) );
        assertEquals(i.get_b_value(2).get_a(),i1.getBOrThrow(2).getA() );


    }
}

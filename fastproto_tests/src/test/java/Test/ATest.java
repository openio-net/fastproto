package Test;



import com.google.protobuf.InvalidProtocolBufferException;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;


import static org.junit.jupiter.api.Assertions.assertEquals;

public class ATest {

    @org.junit.Test
    public void testA() throws InvalidProtocolBufferException {//序列化反序列化测试

        com.X.A a3= X.A.newBuilder()
                .setA(123456)
                .addB(-123)
                .addB(1234)
                .addB(-12345)
                .addB(Integer.MAX_VALUE)
                .build();
        A a= A.newBuilder().add_b_value(-123)
                .add_b_value(1234)
                .add_b_value(-12345)
                .add_b_value(Integer.MAX_VALUE)
                .set_a(123456).build();
        int size=a.getByteSize();
        ByteBuf byteBuf= Unpooled.buffer(a.getByteSize());
        a.encode(byteBuf);

        com.X.A a1= X.A.parseFrom(byteBuf.array());
        int size1=a3.getSerializedSize();
        byteBuf.clear();
        A a2=A.decode(byteBuf.writeBytes(a1.toByteArray()));
        assertEquals(size1,size );
        assertEquals(a1.getB(0),a2.get_b_value(0) );
        assertEquals(a1.getB(1),a2.get_b_value(1) );
        assertEquals(a1.getB(2),a2.get_b_value(2) );
        assertEquals(a1.getB(3),a2.get_b_value(3) );
        assertEquals(a1.getA(),a2.get_a() );

    }
}

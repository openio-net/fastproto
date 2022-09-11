package Test;

import com.O;
import com.X;
import com.google.protobuf.InvalidProtocolBufferException;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import static org.junit.Assert.assertEquals;

public class OTest {

    //
    @org.junit.Test
    public void testN() throws InvalidProtocolBufferException {//序列化反序列化测试
        X.O j1 = X.O.newBuilder().addH(Long.MIN_VALUE)
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


        O j = O.newBuilder()
                .add_h_value(Long.MIN_VALUE)
                .add_h_value(Long.MAX_VALUE)
                .add_h_value(0L)
                .add_i_value(Integer.MAX_VALUE)
                .add_i_value(Integer.MIN_VALUE)
                .add_i_value(0)
                .add_j_value(Integer.MIN_VALUE)
                .add_j_value(Integer.MAX_VALUE)
                .add_j_value(0)
                .add_k_value(Long.MIN_VALUE)
                .add_k_value(Long.MAX_VALUE)
                .add_k_value(0L)
                .add_l_value(true)
                .add_l_value(false)
                .build();

        ByteBuf buf = Unpooled.buffer(j1.getSerializedSize());
        buf.writeBytes(j1.toByteArray());
        O j2 = O.decode(buf);
        buf.clear();
        j.encode(buf);
        O j3 = O.decode(buf);
        assertEquals(j1.getSerializedSize(), j.getByteSize());
        assertEquals(j2.get_h_value(0), j3.get_h_value(0));
        assertEquals(j2.get_h_value(1), j3.get_h_value(1));
        assertEquals(j2.get_h_value(2), j3.get_h_value(2));
        assertEquals(j2.get_i_value(0), j3.get_i_value(0));
        assertEquals(j2.get_i_value(1),j3.get_i_value(1));
        assertEquals(j2.get_i_value(2),j3.get_i_value(2));
        assertEquals(j2.get_j_value(0),j3.get_j_value(0));
        assertEquals(j2.get_j_value(1),j3.get_j_value(1));
        assertEquals(j2.get_j_value(2),j3.get_j_value(2));
        assertEquals(j2.get_k_value(0),j3.get_k_value(0));
        assertEquals(j2.get_k_value(1),j3.get_k_value(1));
        assertEquals(j2.get_k_value(2),j3.get_k_value(2));
        assertEquals(j2.get_l_value(0),j3.get_l_value(0));
        assertEquals(j2.get_l_value(1),j3.get_l_value(1));
    }
}

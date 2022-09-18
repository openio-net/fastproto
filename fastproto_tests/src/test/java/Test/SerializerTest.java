package Test;

import com.Serializer;
import com.google.protobuf.CodedInputStream;
import com.google.protobuf.CodedOutputStream;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SerializerTest {


        private byte[] b = new byte[4096];
        private ByteBuf bb = Unpooled.directBuffer(4096);

        @BeforeEach
        public void setup() {
            bb.clear();
            Arrays.fill(b, (byte) 0);
        }

        @ParameterizedTest
        @ValueSource(ints = {Integer.MIN_VALUE, -1000, -100, -2, -1, 0, 1, 10, 100, 1000, (int) 1e4, (int) 1e5, (int) 1e7, Integer.MAX_VALUE})
        public void testVarInt(int i) throws Exception {
            Serializer.encodeVarInt32(bb,i);
            bb.readBytes(b,0,bb.writerIndex());
            Serializer.encodeVarInt32(bb,i);
            CodedInputStream is = CodedInputStream.newInstance(b);
            int res = is.readRawVarint32();
            assertEquals(i, res);

            res = Serializer.decodeVarInt32(bb);
            assertEquals(i, res);
            assertEquals(CodedOutputStream.computeInt32SizeNoTag(i), Serializer.computeVarInt32Size(i));
        }

        @ParameterizedTest
        @ValueSource(longs = {Long.MIN_VALUE, -10000000, -100, -2, -1, 0, 1, 10, 100, 10000000, (long) 2e18, (long) 2e32, (long) 2e43, (long) 2e57, Long.MAX_VALUE})
        public void testVarInt64(long i) throws Exception {
            Serializer.encodeVarInt64(bb,i);
            bb.readBytes(b,0,bb.writerIndex());
            Serializer.encodeVarInt64(bb,i);
            CodedInputStream is = CodedInputStream.newInstance(b);
            long res = is.readRawVarint64();
            assertEquals(i, res);

            res = Serializer.decodeVarInt64(bb);
            assertEquals(i, res);

            assertEquals(CodedOutputStream.computeInt64SizeNoTag(i), Serializer.computeVarInt64Size(i));
        }

        @ParameterizedTest
        @ValueSource(ints = {Integer.MIN_VALUE, -1000, -100, -2, -1, 0, 1, 10, 100, 1000, Integer.MAX_VALUE})
        public void testSignedVarInt(int i) throws Exception {
            Serializer.encodeVarInt32(bb, Serializer.encodeZigzag32(i));
            bb.readBytes(b,0,bb.writerIndex());
            Serializer.encodeVarInt32(bb, Serializer.encodeZigzag32(i));
            CodedInputStream is = CodedInputStream.newInstance(b);
            int res = is.readSInt32();
            assertEquals(i, res);

            res = Serializer.decodeZigzag32(Serializer.decodeVarInt32(bb));
            assertEquals(i, res);

            assertEquals(CodedOutputStream.computeSInt32SizeNoTag(i),Serializer.computeVarUInt32Size(Serializer.encodeZigzag32(i)));
        }

        @ParameterizedTest
        @ValueSource(longs = {Long.MIN_VALUE, -10000000, -100, -2, -1, 0, 1, 10, 100, 10000000, Long.MAX_VALUE})
        public void testSignedVarInt64(long i) throws Exception {
            Serializer.encodeVarInt64(bb, Serializer.encodeZigzag64(i));
            bb.readBytes(b,0,bb.writerIndex());
            Serializer.encodeVarInt64(bb, Serializer.encodeZigzag64(i));
            CodedInputStream is = CodedInputStream.newInstance(b);
            long res = is.readSInt64();
            assertEquals(i, res);

            res = Serializer.decodeZigzag64(Serializer.decodeVarInt64(bb));
            assertEquals(i, res);

            assertEquals(CodedOutputStream.computeSInt64SizeNoTag(i),Serializer.computeVarInt64Size(Serializer.encodeZigzag64(i)));
        }

        @ParameterizedTest
        @ValueSource(ints = {Integer.MIN_VALUE, -1000, -100, -2, -1, 0, 1, 10, 100, 1000, Integer.MAX_VALUE})
        public void testFixedInt32(int i) throws Exception {
            Serializer.encode32(bb, i);
            bb.readBytes(b,0,bb.writerIndex());
            Serializer.encode32(bb, i);
            CodedInputStream is = CodedInputStream.newInstance(b);
            int res = is.readFixed32();
            assertEquals(i, res);

            res = Serializer.decode32(bb);
            assertEquals(i, res);
        }

        @ParameterizedTest
        @ValueSource(longs = {Long.MIN_VALUE, -10000000, -100, -2, -1, 0, 1, 10, 100, 10000000, Long.MAX_VALUE})
        public void testFixedInt64(long i) throws Exception {
            Serializer.encode64(bb, i);
            bb.readBytes(b,0,bb.writerIndex());
            Serializer.encode64(bb, i);
            CodedInputStream is = CodedInputStream.newInstance(b);
            long res = is.readFixed64();
            assertEquals(i, res);

            res = Serializer.decode64(bb);
            assertEquals(i, res);
        }

        @ParameterizedTest
        @ValueSource(floats = {Float.MIN_VALUE, -1000.0f, -100.0f, -2.f, -1.f, 0f, 1f, 10f, 100f, 1000f, Float.MAX_VALUE})
        public void testFloat(float i) throws Exception {
            Serializer.encodeFloat(bb, i);
            bb.readBytes(b,0,bb.writerIndex());
            Serializer.encodeFloat(bb, i);
            CodedInputStream is = CodedInputStream.newInstance(b);
            float res = is.readFloat();
            assertEquals(i, res);

            res = Serializer.decodeFloat(bb);
            assertEquals(i, res);
        }

        @ParameterizedTest
        @ValueSource(doubles = {Double.MIN_VALUE, -10000000.0, -100.0, -2.0, -1.0, 0.0, 1.0, 10.0, 100.0, 10000000.0, Double.MAX_VALUE})
        public void testDouble(double i) throws Exception {
            Serializer.encodeDouble(bb, i);
            bb.readBytes(b,0,bb.writerIndex());
            Serializer.encodeDouble(bb, i);
            CodedInputStream is = CodedInputStream.newInstance(b);
            double res = is.readDouble();
            assertEquals(i, res);

            res = Serializer.decodeDouble(bb);
            assertEquals(i, res);
        }

        @ParameterizedTest
        @ValueSource(strings = {"hello", "UTF16 Ελληνικά Русский 日本語", "Neque porro quisquam est qui dolorem ipsum"})
        public void testString(String s) throws Exception {
            byte[] sb = s.getBytes(StandardCharsets.UTF_8);
            assertEquals(sb.length,s.getBytes(StandardCharsets.UTF_8).length);


            Serializer.encodeString(bb, s);
            bb.readBytes(b,0,bb.writerIndex());
            Serializer.encodeString(bb, s);
            CodedInputStream is = CodedInputStream.newInstance(b);
            assertEquals(s, is.readString());

            assertEquals(sb.length, ByteBufUtil.utf8Bytes(s));
            assertEquals(s, Serializer.decodeString(bb, Serializer.decodeVarInt32(bb)));

            assertEquals(CodedOutputStream.computeStringSizeNoTag(s), Serializer.computeVarInt32Size(sb.length) + ByteBufUtil.utf8Bytes(s));
        }


}

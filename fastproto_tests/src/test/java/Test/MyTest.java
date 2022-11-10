package Test;


import com.github.os72.protocjar.Protoc;

import java.io.IOException;

public class MyTest {


    //    @org.junit.Test//生成java代码
//    public void bootTest() throws Exception {
//        Config config=new Config();
//        config.setFileDir("src/test/proto");
//        config.addProtoFiles("x.proto");
//        config.addProtoFiles("addressbook.proto");
//        config.addProtoFiles("bytes.proto");
//        config.addProtoFiles("enums.proto");
//        config.addProtoFiles("messages.proto");
//        config.addProtoFiles("numbers.proto");
//        config.addProtoFiles("PulsarApi.proto");
//        config.addProtoFiles("PulsarMarkers.proto");
//        config.addProtoFiles("repeated_numbers.proto");
//        config.addProtoFiles("required.proto");
//        config.addProtoFiles("strings.proto");
//        config.addProtoFiles("Test.proto");
////        config.addProtoFiles("b.proto");
//        Boot.boot(config);
//    }
//
//    @org.junit.Test//生成java代码
//    public void bootTestProto() throws Exception {
//        Config config=new Config();
//        config.setFileDir("src/test/proto");
//        File file=new File("src/test/proto");
//        for(File file1: file.listFiles()){
//            if(file1.isFile()) {
//                if(file1.getName().matches(".*.proto")) {
//                    config.addProtoFiles(file1.getName());
//                }
//
//            }
//        }
//
//
////        config.addProtoFiles("b.proto");
//        Boot.boot(config);
//    }
//
//
//
//
//
//
//
//
//
//
//
//
//
//    @org.junit.Test
//    public void testC(){//序列化反序列化测试
//        A a=A.newBuilder().add_b_value(123)
//                .add_b_value(1234)
//                .add_b_value(12345)
//                .add_b_value(123456)
//                .set_a(123456).build();
//
//        A a1=A.newBuilder().add_b_value(123)
//                .add_b_value(1234)
//                .add_b_value(12345)
//                .add_b_value(123456)
//                .set_a(123456).build();
//        B b= B.newBuilder().add_b_value(a)
//                .add_b_value(a1)
//                .build();
//
//
//        C c=C.newBuilder()
//                .add_b_value(b)
//                .build();
//        ByteBuf byteBuf= Unpooled.buffer(c.getByteSize());
//        c.encode(byteBuf);
//        System.out.println(c.getByteSize());
//        System.out.println(byteBuf.writerIndex());
//        C c1=C.decode(byteBuf);
//        System.out.println();
//        System.out.println(c1.get_b_value(0).get_b_value(0).get_b_value(0));
//        System.out.println(c1.get_b_value(0).get_b_value(0).get_a());
//
//        System.out.println(c1.get_b_value(0).get_b_value(1).get_b_value(3));
//        System.out.println(c1.get_b_value(0).get_b_value(1).get_a());
//    }
//
//    @org.junit.Test
//    public void testD(){//序列化反序列化测试
//        D d=D.newBuilder().put_b(13,12l)
//                .put_b(14,13l)
//                .build();
//
//        ByteBuf byteBuf= Unpooled.buffer(d.getByteSize());
//        d.encode(byteBuf);
//        System.out.println(d.getByteSize());
//        System.out.println(byteBuf.writerIndex()-byteBuf.readerIndex());
//        D d1=D.decode(byteBuf);
//        for(int key:d1.get_b_KeySet()){
//            System.out.println(key);
//            System.out.println(d1.get_b_value(key));
//        }
//    }
//
//
//
//
//    @org.junit.Test
//    public void testF(){//序列化反序列化测试
//        A a=A.newBuilder().add_b_value(123)
//                .add_b_value(1234)
//                .add_b_value(12345)
//                .add_b_value(123456)
//                .set_a(123456).build();
//
//        A a1=A.newBuilder().add_b_value(123)
//                .add_b_value(1234)
//                .add_b_value(12345)
//                .add_b_value(123456)
//                .set_a(123456).build();
//
//        I i= I.newBuilder().put_b(1,a)
//                .put_b(2,a1).build();
//        ByteBuf byteBuf= Unpooled.buffer(i.getByteSize());
//        i.encode(byteBuf);
//        System.out.println(byteBuf.writerIndex());
//        System.out.println(i.getByteSize());
//        for(int key:i.get_b_KeySet()){
//            System.out.println(i.get_b_value(key).get_b_value(3));
//        }
//
//    }
//
//
//
    @org.junit.Test
    public void testProtoc() throws IOException, InterruptedException {//序列化反序列化测试

        String[] args = {"-v3.11.1", "-I", "src/test/proto", "--java_out", "src/test/java", "src/test/proto/x.proto"};
        int result = Protoc.runProtoc(args);//生成desc文件，调用protoc
        System.out.println(result);
    }


//
//    @org.junit.Test
//    public void testProto() throws IOException, InterruptedException {//序列化反序列化测试
//
//        A a=A.newBuilder().add_b_value(123)
//                .add_b_value(1234)
//                .add_b_value(12345)
//                .add_b_value(123456)
//                .set_a(123456).build();
//
//        A a1=A.newBuilder().add_b_value(123)
//                .add_b_value(1234)
//                .add_b_value(12345)
//                .add_b_value(123456)
//                .set_a(123456).build();
//        D d=D.newBuilder().put_b(13,12l)
//                .put_b(14,13l)
//                .build();
//
//        D d1=D.newBuilder().put_b(13,12l)
//                .put_b(14,13l)
//                .build();
//
//        E e=E.newBuilder().put_a(12,d)
//                .put_a(13,d1)
//                .add_b_value(B.newBuilder().add_b_value(a).add_b_value(a1).build())
//                .setOneOf0Valuec(1)
//                .setOneOf0Valuef(12l)
//                .setOneOf0Valuef(13l)
//                .build();
//
//        ByteBuf buf=Unpooled.buffer(e.getByteSize());
//        e.encode(buf);
//
//        System.out.println(com.X.E.parseFrom(buf.array()));
//
//    }
//
//
//    @org.junit.Test
//    public void testEncodeProto() throws IOException, InterruptedException {//序列化反序列化测试
//
//        ByteBuf buf=Unpooled.buffer();
//        buf.writeBytes(com.X.A.newBuilder().setA(12)
//                .addB(1).addB(2).build().toByteArray());
//
//        A a=A.decode(buf);
//        System.out.println(a.get_b_value(1));
//        System.out.println(a.get_a());
//    }
//
//
//    @org.junit.Test
//    public void testEncodeProtoD() throws IOException, InterruptedException {//序列化反序列化测试
//
//        ByteBuf buf=Unpooled.buffer();
//        buf.writeBytes(com.X.D.newBuilder().putB(-122,122).putB(133,133).putB(144,144).build().toByteArray());
//
//        D a=D.decode(buf);
//       System.out.println(a.get_b_value(-122));
//        System.out.println(a.get_b_value(133));
//        System.out.println(a.get_b_value(144));
//    }

}

package com.fastproto.test;

import com.fastproto.Boot;

import com.fastproto.config.Config;


import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.junit.Test;

public class MyTest {




    @org.junit.Test//生成java代码
    public void bootTest() throws Exception {
        Config config=new Config();
        config.setFileDir("src/main/java/generate/");
        config.addProtoFiles("x.proto");
        config.addProtoFiles("b.proto");
        Boot.boot(config);
    }





//    @org.junit.Test
//    public void test(){//序列化反序列化测试
//        ByteBuf b=Unpooled.buffer(10);
//        A a=new A.ABuild().seta(1)
//                .addb(1)
//                .addb(2)
//                .addb(4)
//                .addb(2)
//                .addb(23)
//                .IsInitialized();
//
//
//        a.encode(b);
//        A a1=google.protobuf.A.ABuild.decode(b);
//
//
//        google.protobuf.B bBuild=new B.BBuild()
//                .addb(a1)
//                .addb(a)
//                .IsInitialized();
//
//        ByteBuf b1=Unpooled.buffer(10);
//
//        bBuild.encode(b1);
//
//        google.protobuf.B bx=google.protobuf.B.BBuild.decode(b1);
//
//
//        bBuild.encode(b1);
//
//        google.protobuf.B bx2=google.protobuf.B.BBuild.decode(b1);
//
//
//
//        new C.CBuild()
//                .addb(bx)
//                .addb(bx2)
//                .IsInitialized().encode(b1);
//        C c=C.CBuild.decode(b1);
//
//
//
//        D d=new D.DBuild().putb(1234567890,1234567890l)
//                .putb(123456789,1234567890l).IsInitialized();
//        E e=new E.EBuild()
//                .addb(bx)
//                .addb(bx2)
//                .sete(1234567890l)
//                .addm(1234567890)
//                .addm(1234567890)
//                .putn1(1234567890,a)
//                .putn1(123456789,a1)
//                .addo(1234567890l)
//                .addo(1234567890l)
//                .setc(1234567890)
//                .setf(1234567890l)
//                .seth(1234567890l)
//                .seti(1234567890)
//                .setj(1234567890l)
//                .setk("1234567890")
//                .setl(new byte[]{123,123,123,123})
//                .setq(F.a)
//                .puta(1234567890,d)
//                .puta(1234567890,d)
//                .IsInitialized();
//        ByteBuf buf=Unpooled.buffer(1024);
//        e.encode(buf);
//
//        E e1=E.EBuild.decode(buf);
//
//        System.out.println();
//        for(B b2:e1.getb()){
//            for(A a2: b2.getb()){
//                System.out.println(a2.geta());
//                for(int s:a2.getb()){
//                    System.out.println(s);
//                }
//            }
//        }
//
//        System.out.println(e1.gete());
//        System.out.println(e1.getc());
//        System.out.println(e1.getf());
//        System.out.println(e1.geth());
//        System.out.println(e1.geti());
//        System.out.println(e1.getj());
//        System.out.println(e1.getk());
//        for(byte b3:e1.getl()) {
//            System.out.print(b3+" ");
//        }
//
//        System.out.println(e1.getq());
//
//        for(int key:e1.geta().keySet()){
//            D d1=e1.geta().get(key);
//            System.out.println("e1key:"+key);
//            for(int k:d1.getb().keySet()){
//                System.out.println("d1key:"+k);
//                System.out.println("MapData:"+d1.getb().get(k));
//            }
//        }
//        for(long w:e1.getm()){
//            System.out.println(w);
//        }
//    }
}

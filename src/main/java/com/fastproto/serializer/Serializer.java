package com.fastproto.serializer;


import io.netty.buffer.ByteBuf;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public  class Serializer {


    public static int decodeVarInt32(ByteBuf byteBuf){
        int value;//解析第一个数
        int a=byteBuf.readByte();
        if(a>=0){
            return a;
        }
        value=a^0b11111111111111111111111110000000;


        a=byteBuf.readByte();//解析第二数
        if(a>=0){
            return value|(a<<7);
        }

        value=value|((a^0b11111111111111111111111110000000)<<7);

        a=byteBuf.readByte();//解析第三数
        if(a>=0){
            return value|(a<<14);
        }

        value=value|((a^(0b11111111111111111111111110000000))<<14);

        a=byteBuf.readByte();//解析第4数
        if(a>=0){
            return value|(a<<21);
        }

        value=value|((a^(0b11111111111111111111111110000000))<<21);


        a=byteBuf.readByte();//解析第5数
        if(a>=0){
            return     value|(a<<28);
        }

        throw new  RuntimeException("this code is wrong");
    }



    public static long decodeVarInt64(ByteBuf byteBuf){
        long value;//解析第一个数
        long a=byteBuf.readByte();
        if(a>=0L){
            return a;
        }
        value=a^0b1111111111111111111111111111111111111111111111111111111110000000L;


        a=byteBuf.readByte();//解析第二数
        if(a>=0L){
            return value|(a<<7L);
        }

        value=value|((a^0b1111111111111111111111111111111111111111111111111111111110000000L)<<7L);

        a=byteBuf.readByte();//解析第三数
        if(a>=0){
            return value|(a<<14L);
        }

        value=value|((a^0b1111111111111111111111111111111111111111111111111111111110000000L)<<14L);

        a=byteBuf.readByte();//解析第4数
        if(a>=0L){
            return value|(a<<21L);
        }

        value=value|((a^0b1111111111111111111111111111111111111111111111111111111110000000L)<<21L);


        a=byteBuf.readByte();//解析第5数
        if(a>=0L){
            return value|(a<<28L);
        }
        value=value|((a^0b1111111111111111111111111111111111111111111111111111111110000000L)<<28L);


        a=byteBuf.readByte();//解析第6数
        if(a>=0L){
            return value|(a<<35L);
        }
        value=value|((a^0b1111111111111111111111111111111111111111111111111111111110000000L)<<35L);

        a=byteBuf.readByte();//解析第7数
        if(a>=0L){
            return value|(a<<42L);
        }
        value=value|((a^0b1111111111111111111111111111111111111111111111111111111110000000L)<<42L);

        a=byteBuf.readByte();//解析第8数
        if(a>=0L){
            return value|(a<<49L);
        }
        value=value|((a^0b1111111111111111111111111111111111111111111111111111111110000000L)<<49L);

        a=byteBuf.readByte();//解析第9数
        if(a>=0L){
            return value|(a<<56L);
        }
        value=value|((a^0b1111111111111111111111111111111111111111111111111111111110000000L)<<56L);

        a=byteBuf.readByte();//解析第10数
        if(a>=0L){
            return value|(a<<63L);
        }
        throw new  RuntimeException("this code is wrong");



    }



    public static void encodeVarInt32(ByteBuf byteBuf,int value){
        while (true) {
            int value2 = value >>> 7;
            if (value2 == 0) {
                byteBuf.writeByte(value);
                return;
            }
            byteBuf.writeByte( (value | 128));

            value = value2;
        }

    }


    public static void encodeVarInt64(ByteBuf byteBuf,long value){
        while (true) {
            long value2 = value >>> 7L;
            if (value2 == 0L) {
                byteBuf.writeByte((int) value);
                return;
            }
            byteBuf.writeByte((int) (value | 128L));

            value = value2;
        }


    }


    public static int encodeZigzag32(int value){
        return (value<<1)^(value>>31);
    }

    public static long encodeZigzag64(long value){
        return (value<<1L)^(value>>63L);
    }


    public static int decodeZigzag32(int value){
        return (value>>>1) ^ -(value&1);
    }

    public static long decodeZigzag64(long value){
       return (value>>>1L) ^ -(value&1L);
    }

    public static void encodeString(ByteBuf byteBuf,String s){
        byte[] a=s.getBytes(StandardCharsets.UTF_8);
        Serializer.encodeVarInt32(byteBuf,a.length);
        byteBuf.writeBytes(a);
    }

    public static void encodeByteString(ByteBuf byteBuf,byte[] s){
        byteBuf.writeBytes(s);

    }

    public static String decodeString(ByteBuf byteBuf,int length){
        if(!byteBuf.hasArray()){
            throw new RuntimeException("byteBuf not has array");
        }
        int read=byteBuf.readerIndex();
        byteBuf.readerIndex(read+length);
        return new String(byteBuf.array(),read,length);
    }

    public static byte[] decodeByteString(ByteBuf byteBuf,int length){
        if(!byteBuf.hasArray()){
            throw new RuntimeException("byteBuf not has array");
        }
        int read=byteBuf.readerIndex();
        byteBuf.readerIndex(read+length);
        return Arrays.copyOfRange(byteBuf.array(),read,read+length);

    }

    public static boolean decodeBoolean(ByteBuf byteBuf){
        return decodeVarInt64(byteBuf)!=0;
    }


    public static void encodeDouble(ByteBuf byteBuf,double b){
        byteBuf.writeDouble(b);

    }

    public static void encodeFloat(ByteBuf byteBuf,float b){
        byteBuf.writeFloat(b);
    }

    public static double decodeDouble(ByteBuf byteBuf){
        return byteBuf.readDouble();
    }

    public static float decodeFloat(ByteBuf byteBuf){
        return byteBuf.readFloat();

    }


    public static void encode32(ByteBuf byteBuf,int b){
        byteBuf.writeInt(b);
    }

    public static void encode64(ByteBuf byteBuf,long b){

        byteBuf.writeLong(b);


    }

    public static int decode32(ByteBuf byteBuf){

        return byteBuf.readInt();
    }

    public static long decode64(ByteBuf byteBuf){
        return byteBuf.readLong();
    }















}

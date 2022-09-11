package com.github.myincubator.fastproto.wrapper;

public enum FiledType {

    Int32("int32",Integer.class),
    Int64("int64",Long.class),
    Double("double", java.lang.Double.class),
    Float("float",java.lang.Float.class),
    uInt32("uint32",Integer.class),
    uInt64("uInt64",Long.class),
    sInt32("sInt32",Integer.class),
    sInt64("sInt64",Long.class),
    Fixed32("fixed32",Integer.class),
    Fixed64("fixed64",Long.class),
    sFixed32("sfixed32",Integer.class),
    sFixed64("sfixed64",Long.class),
    Bool("bool",Boolean.class),
    String("String",String.class),
    Bytes("bytes",byte[].class),
    Message("message", java.lang.Object.class),
    Enum("enum",Enum.class);

    public final  static String eInt32="int32";
    public final  static String eInt64="int64";
    public final  static String eDouble="double";
    public final  static String eFloat="float";
    public final  static String euInt32="uint32";
    public final  static String euInt64="uInt64";
    public final  static String esInt32="sInt32";
    public final  static String esInt64="sInt64";
    public final  static String eFixed32="fixed32";
    public final  static String eFixed64="fixed64";
    public final  static String esFixed32="sfixed32";
    public final  static String esFixed64="sfixed64";
    public final  static String eBool="bool";
    public final  static String eString="String";
    public final  static String eBytes="bytes";
    public final  static String eMessage="message";
    public final  static String eEnum="enum";

    String type;

    Class javaClass;

    public Class getJavaClass(){
        return javaClass;
    }
    FiledType(String type, Class javaClass){
        this.type=type;
        this.javaClass=javaClass;
    }

    public java.lang.String getType() {
        return type;
    }


}

package com.fastproto.wrapper;

public enum FiledType {

    Int32("int32",Integer.class),
    Int64("int64",Long.class),
    Double("double", java.lang.Double.class),
    Float("float",java.lang.Float.class),
    uInt32("uint32",Integer.class),
    uInt64("uInt64",Long.class),
    sInt32("sInt32",Integer.class),
    sInt64("sInt32",Long.class),
    Fixed32("fixed32",Integer.class),
    Fixed64("fixed64",Long.class),
    sFixed32("sfixed32",Integer.class),
    sFixed64("sfixed64",Long.class),
    Bool("bool",Boolean.class),
    String("String",String.class),
    Bytes("bytes",byte[].class),
    Map("map", java.util.Map.class),
    Message("message", java.lang.Object.class),
    Enum("enum",Enum.class);


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

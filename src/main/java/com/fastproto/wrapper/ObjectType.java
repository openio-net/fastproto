package com.fastproto.wrapper;

public enum ObjectType {

    Message("message"),//proto2、3,
    Enum("enum"),//proto2、3,
    Service("service"),//proto2、3
    Extend("extend"),
    OneOf("oneof"),
    Map("map"),
    group("group");//proto2

    String type;



    ObjectType(String type){
        this.type=type;
    }
}

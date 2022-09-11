package com.github.myincubator.fastproto.wrapper;

public class ProtoOption implements Option{

    private  String key;

    private java.lang.Object value;

    private OptionType type;

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public java.lang.Object getValue() {
        return value;
    }

    @Override
    public OptionType getObjectType() {
        return type;
    }

    public ProtoOption(String key,java.lang.Object value,OptionType optionType){
        this.key=key;
        this.value=value;
        this.type=optionType;
    }
}

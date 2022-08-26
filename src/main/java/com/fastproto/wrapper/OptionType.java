package com.fastproto.wrapper;

public enum OptionType {

    PackageOption("FileOption"),
    ObjectOption("ObjectOption"),
    FiledOption("FiledOption");

    String type;

    OptionType(String type){
        this.type=type;
    }
}

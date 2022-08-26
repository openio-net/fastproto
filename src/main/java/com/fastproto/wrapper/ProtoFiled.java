package com.fastproto.wrapper;

import java.util.ArrayList;
import java.util.List;

public class ProtoFiled implements Filed{

    private int tag;

    private FiledLabel filedLabel;

    private FiledType filedType;

    private String fileName;

    private java.lang.Object filed;

    private boolean oneOf;

    private int oneIndex;

    private List<Option> options=new ArrayList<>();

    private String fileTypeName;

    @Override
    public int getTag() {
        return tag;
    }

    @Override
    public Filed setTag(int tag) {
        this.tag=tag;
        return this;
    }

    @Override
    public FiledLabel getFiledLabel() {
        return this.filedLabel;
    }

    @Override
    public Filed setFiledLabel(FiledLabel filedLabel) {
        this.filedLabel=filedLabel;
        return this;
    }

    @Override
    public FiledType getFileType() {
        return filedType;
    }

    @Override
    public Filed setFileType(FiledType filedType) {
        this.filedType=filedType;
        return this;
    }

    @Override
    public String getFiledName() {
        return fileName;
    }

    @Override
    public Filed setFileTypeName(String filedName) {
        this.fileTypeName=filedName;

        return this;
    }

    @Override
    public String getFileTypeName() {
        return this.fileTypeName;
    }

    @Override
    public Filed setFiledName(String filedName) {
        this.fileName=filedName;
        return this;
    }



    @Override
    public List<Option> getAllOption() {
        return options;
    }

    @Override
    public Option getOption(String key) {
        for(Option option : options) {
            if(option.getKey().equals(key)) {
                return option;
            }
        }
        return null;
    }

    @Override
    public Filed addOption(Option option) {
        if(option==null){
            throw new RuntimeException("option is null");
        }
        options.add(option);
        return this;
    }

    @Override
    public Filed setHasOneOf(boolean b) {
         oneOf=b;
         return this;
    }

    @Override
    public boolean getHasOneOf() {
        return oneOf;
    }

    @Override
    public int getOneIndex() {
        return 0;
    }

    @Override
    public Filed setOneIndex(int oneIndex) {
        this.oneIndex=oneIndex;
        return this;
    }
}

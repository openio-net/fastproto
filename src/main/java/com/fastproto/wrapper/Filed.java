package com.fastproto.wrapper;


import java.util.List;

public interface Filed {


    int getTag();

    Filed setTag(int tag);

    FiledLabel getFiledLabel();

    Filed setFiledLabel(FiledLabel filedLabel);//re

    FiledType getFileType();//

    Filed setFileType(FiledType filedType);

    String getFiledName();//获取属性名字


     Filed setFileTypeName(String filedName);


     String getFileTypeName();

    Filed setFiledName(String filedName);//


    List<Option> getAllOption();//获取所有的选项

    Option getOption(String key);//更具名字获取选项

    Filed addOption(Option option);//添加选项


    Filed setHasOneOf(boolean b);

    boolean getHasOneOf();

    int getOneIndex();


    public Filed setOneIndex(int oneIndex);
}

package com.github.myincubator.fastproto.wrapper;

import java.util.List;

public interface Object {

    String getName();//获取名字

    ObjectType getObjectType();

    Object setObjectType(ObjectType objectType);

    Object setName(String name);//设置名字

    List<Filed> getAllFiled();//获取所有的属性

    Filed getFile(String filedName);//根据名字获取字段

    Object addFiled(Filed filed);//添加字段信息

    List<Option> getAllOption();//获取所有的选项

    Option getOption(String key);//更具名字获取选项

    Object addOption(Option option);//添加选项

    Object addObject(Object object);

    List<Object> getObject();

}

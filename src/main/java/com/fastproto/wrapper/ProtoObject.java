package com.fastproto.wrapper;

import java.util.ArrayList;
import java.util.List;

public class ProtoObject implements Object {

    private String name;

    private ObjectType objectType;

    private List<Filed> filed=new ArrayList<>();

    private List<Option> options=new ArrayList<>();

    private List<Object> objects=new ArrayList<>();

    @Override
    public String getName() {
        return name;
    }

    @Override
    public ObjectType getObjectType() {
        return objectType;
    }

    @Override
    public Object setObjectType(ObjectType objectType) {
        this.objectType=objectType;
        return this;
    }

    @Override
    public Object setName(String name) {
        this.name=name;
        return this;
    }

    @Override
    public List<Filed> getAllFiled() {

        return filed;
    }

    @Override
    public Filed getFile(String filedName) {
        for(Filed filed: this.filed){
            if(filed.getFiledName().equals(filedName)){
                return filed;
            }
        }
        return null;
    }

    @Override
    public Object addFiled(Filed filed) {
        this.filed.add(filed);
        return this;
    }

    @Override
    public List<Option> getAllOption() {
        return options;
    }

    @Override
    public Option getOption(String key) {
        for(Option option: options){
            if(option.getKey().equals(key)){
                return option;
            }
        }
        return null;
    }

    @Override
    public Object addOption(Option option) {
        if(option==null){
            throw  new RuntimeException("option is null");
        }
        options.add(option);
        return this;
    }

    @Override
    public Object addObject(Object object) {
       this.objects.add(object);
        return this;
    }

    @Override
    public List<Object> getObject() {
        return objects;
    }

    @Override
    public String toString() {
        return "ProtoObject{" +
                "name='" + name + '\'' +
                ", objectType=" + objectType +
                ", filed=" + filed +
                ", options=" + options +
                ", objects=" + objects +
                '}';
    }
}

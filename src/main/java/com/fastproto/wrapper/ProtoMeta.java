package com.fastproto.wrapper;

public class ProtoMeta implements Meta{

    private String fileName;

    private String packageName;

    private String filed;

    private String objectName;

    private Object object;

    @Override
    public String getFileName() {
        return fileName;
    }

    @Override
    public String getPackageName() {
        return null;
    }

    @Override
    public String getField() {
        return filed;
    }

    @Override
    public String getObjectName() {
        return objectName;
    }

    public Object getObject(){
        return object;
    }

    public ProtoMeta(String fileName,String packageName,String filed,String objectName,Object object){
        this.fileName=fileName;
        this.filed=filed;
        this.objectName=objectName;
        this.object=object;
        this.packageName=packageName;
    }
}

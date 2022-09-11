package com.github.myincubator.fastproto.wrapper;

public class ProtoMeta implements Meta{

    private String className;//对像的名字


    private String javaPackageName;//所在的java包名

    private String protoPackName;//proto的包名

    private String filed;//域

    private String objectName;//proto中的全类名


    @Override
    public String getClassName() {
        return className;
    }

    @Override
    public String getProtoPackName() {
        return protoPackName;
    }

    @Override
    public String getJavaPackageName() {
        return javaPackageName;
    }

    @Override
    public String getField() {
        return filed;
    }

    @Override
    public String getObjectName() {
        return objectName;
    }

    @Override
    public String getJavaObjectName() {
        if(filed!=null&&!filed.equals("")) {
            if(javaPackageName!=null&&!javaPackageName.equals("")) {
                return javaPackageName + "." + filed + "." + className;
            }else {
                return filed+"."+className;
            }
        }


        if(javaPackageName!=null&&!javaPackageName.equals("")) {
            return javaPackageName + "."  + className;
        }else {
            return className;
        }
    }


    public ProtoMeta(String className, String javaPackageName, String protoPackName, String filed, String objectName) {
        this.className = className;
        this.javaPackageName = javaPackageName;
        this.protoPackName = protoPackName;
        this.filed = filed;
        this.objectName = objectName;
    }


}

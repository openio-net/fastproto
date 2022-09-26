package com.github.myincubator.fastproto.wrapper;

public class Meta {

    private final String className;//对像的名字


    private final String javaPackageName;//所在的java包名

    private final String protoPackName;//proto的包名

    private final String filed;//域

    private final String objectName;//proto中的全类名


    public Meta(String className, String javaPackageName, String protoPackName, String filed, String objectName) {
        this.className = className;
        this.javaPackageName = javaPackageName;
        this.protoPackName = protoPackName;
        this.filed = filed;
        this.objectName = objectName;
    }

    public String getClassName() {
        return className;
    }

    public String getProtoPackName() {
        return protoPackName;
    }

    public String getJavaPackageName() {
        return javaPackageName;
    }

    public String getField() {
        return filed;
    }

    public String getObjectName() {
        return objectName;
    }

    public String getJavaObjectName() {
        if (filed != null && !filed.equals("")) {
            if (javaPackageName != null && !javaPackageName.equals("")) {
                return javaPackageName + "." + filed + "." + className;
            } else {
                return filed + "." + className;
            }
        }


        if (javaPackageName != null && !javaPackageName.equals("")) {
            return javaPackageName + "." + className;
        } else {
            return className;
        }
    }
}

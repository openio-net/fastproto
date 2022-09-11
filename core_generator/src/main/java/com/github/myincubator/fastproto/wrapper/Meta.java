package com.github.myincubator.fastproto.wrapper;

public interface Meta {

    /**
     *
     * @return 对象所在的文件名字;
     */
    String getClassName();

    /**
     *
     * @return 所在的proto名字
     */
    String getProtoPackName();


    /**
     *
     * @return 所在的包名
     */
    String getJavaPackageName();


    /**
     *
     * @return 所在的作用域，
     */
    String getField();


    /**
     *
     * @return proto中的全类名类名
     */
    String getObjectName();//



    /**
     *
     * @return
     */
    String getJavaObjectName();//获取java全类名
}

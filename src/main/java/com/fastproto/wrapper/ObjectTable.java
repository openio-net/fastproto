package com.fastproto.wrapper;

import java.util.List;

public interface ObjectTable {//存储定义的message、enum等信息，

    List<Meta> getMeta(String name);

    void addMeta(Meta meta);

    List<Meta> getMetaByPackage(String packageName);

    List<Meta> getMetaByFile(String fileName);


}

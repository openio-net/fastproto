package com.github.myincubator.fastproto.wrapper;

import java.util.List;

public class ProtoObjectTable implements ObjectTable{
    @Override
    public List<Meta> getMeta(String name) {
        return null;
    }

    @Override
    public void addMeta(Meta meta) {

    }

    @Override
    public List<Meta> getMetaByPackage(String packageName) {
        return null;
    }

    @Override
    public List<Meta> getMetaByFile(String fileName) {
        return null;
    }
}

package com.github.myincubator.fastproto.wrapper;

import java.util.List;

public interface Package {


    List<Option> getOption();

    Package addOption(Option option);

    String getFileName();

    Package setFileName(String fileName);

    List<String> getImportFile();

    Package addImportFile(String path);

    List<String> getImportPublicFile();

    Package addPublicPackage(String path);

    String getPackageName();

    List<Object> getAllObject();

    Object getObjectByName(String Name);


    Package addObject(Object object);

    Package setPackageName(String name);


    Package setJavaPackName(String name);

    String getJavaPackName();
}

package com.fastproto.wrapper;

import java.util.ArrayList;
import java.util.List;

public class ProtoPackage implements Package{

    private String fileName;

    private String packageName;

    private List<Option> options=new ArrayList<>();

    private List<String> ImportFile=new ArrayList<>();

    private List<String> ImportPublicFile=new ArrayList<>();

    private List<Object> objects=new ArrayList<>();



    @Override
    public List<Option> getOption() {
        return options;
    }

    @Override
    public Package addOption(Option option) {
        if(option==null){
            throw new RuntimeException("option is null");
        }
        options.add(option);
        return this;
    }

    @Override
    public String getFileName() {
        return fileName;
    }

    @Override
    public Package setFileName(String fileName) {
         this.fileName=fileName;
         return this;
    }


    @Override
    public List<String> getImportFile() {
        return ImportFile;
    }

    @Override
    public Package addImportFile(String path) {
        if(path==null){
            throw new RuntimeException("path is null");
        }
        ImportFile.add(path);
        return this;
    }

    @Override
    public List<String> getImportPublicFile() {
        return ImportPublicFile;
    }

    @Override
    public Package addPublicPackage(String path) {
        if(path==null){
            throw new RuntimeException("path is null");
        }
        ImportPublicFile.add(path);
        return this;
    }

    @Override
    public String getPackageName() {
        return packageName;
    }

    @Override
    public List<Object> getAllObject() {
        return objects;
    }

    @Override
    public Object getObjectByName(String Name) {
        if(Name==null){
            throw new RuntimeException("Name is null");
        }
        for (Object object : objects){
            if(object.getName().equals(Name))
                return object;
        }
        return null;
    }

    @Override
    public Package addObject(Object object) {
        if(object==null){
            throw new RuntimeException("object is null");
        }
        objects.add(object);
        return this;
    }

    @Override
    public Package setPackageName(String name) {
        if(name==null){
            throw new RuntimeException("PackageName is null");
        }
        this.packageName=name;
        return this;
    }

    @Override
    public String toString() {
        return "ProtoPackage{" +
                "fileName='" + fileName + '\'' +
                ", packageName='" + packageName + '\'' +
                ", options=" + options +
                ", ImportFile=" + ImportFile +
                ", ImportPublicFile=" + ImportPublicFile +
                ", objects=" + objects +
                '}';
    }
}

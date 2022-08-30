package com.fastproto.config;

import java.util.ArrayList;
import java.util.List;

public class Config {

    private String fileDir="";

    private  String java_out="src/main/java/";

    private final List<String> ProtoFiles=new ArrayList<String>();//文件名字

    public String getFileDir(){
        return fileDir;
    }

    public  void setFileDir(String fileDir1){
        this.fileDir=fileDir1;
    }

    public String getJavaOut(){
        return java_out;
    }

    public  void setJavaOut(String fileDir1){
        this.java_out=fileDir1;
    }

    public void addProtoFiles(String file){
        ProtoFiles.add(file);
    }

    public List<String> getProtoFiles(){
        return this.ProtoFiles;
    }
}

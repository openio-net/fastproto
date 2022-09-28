package com.github.myincubator.fastproto.config;

import java.util.ArrayList;
import java.util.List;


public class Config {

    private String fileDir="";//File scan root path

    private  String java_out="";//Java file output path

    private final List<String> ProtoFiles=new ArrayList<String>();//File name

    public String getFileDir(){
        return fileDir;
    }

    public  void setFileDir(String fileDir1){
        char a=fileDir1.charAt(fileDir1.length()-1);
        if(a=='/'&&a=='\\'){
            this.fileDir=fileDir1;
        }else {
            this.fileDir=fileDir1+'/';
        }

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

    public void addProtoFiles(List files){
        ProtoFiles.addAll(files);
    }

    public List<String> getProtoFiles(){
        return this.ProtoFiles;
    }
}

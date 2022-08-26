package com.fastproto.compile;

import com.fastproto.config.Config;
import com.fastproto.wrapper.*;
import com.fastproto.wrapper.Object;

import java.io.*;
import java.util.*;

public class Generate {

    private Map<String,Object> map=new HashMap();

    public void Generate(List<com.fastproto.wrapper.Package> list, Config config){
        File file=new File(config.getFileDir());
        if(!file.isDirectory()){
            throw new RuntimeException(" config is wrong ");
        }
        for(com.fastproto.wrapper.Package pack:list) {
            String[] pN =pack.getPackageName().split(".");
            String path=config.getFileDir();
            for(int i=0;i<pN.length;i++){
                path=path+pN[i]+"\\";
            }
             File file1 = new File(path);
            try {
                file1.createNewFile();
            }catch (Exception e){
                throw new RuntimeException("create javaFile is Wrong");
            }



            String Package="package "+pack.getPackageName()+"\n";

            for(Object o : pack.getAllObject()){
                File file2=new File(path+o.getName()+".java");
                try {
                    file2.createNewFile();
                } catch (IOException e) {
                    throw new RuntimeException("create javaFile is wrong");
                }
                StringWriter stringWriter=new StringWriter();
                PrintWriter pw=new PrintWriter(stringWriter);
                pw.print(Package);

                for(String s: pack.getImportFile()){
                    for(com.fastproto.wrapper.Package p :list){
                        if(p.getFileName().equals(s)){
                            pw.println("import "+p.getPackageName()+".*;");
                        }
                    }
                }
                pw.println("import "+"java.unit.List;");
                pw.println("import "+"java.unit.Map;");
                pw.println("import "+"java.unit.ArrayList;");
                pw.println("import "+"java.unit.HashMap;");
                pw.println("import io.netty.buffer.ByteBuf;");

                if(o.getObjectType().name().equals(ObjectType.Enum.name())){
                    Enum(o,pw);
                }else if(o.getObjectType().name().equals(ObjectType.Message.name())){
                    message(pack.getPackageName(),o,pw);
                }


            }

        }
    }

    private void message(String path,Object object,PrintWriter pw){
        if(object.getObjectType().name().equals(ObjectType.Map.name())){
            map.put(path+"."+object.getName(),object);
            return;
        }
        pw.println("public class "+object.getName()+"{");

        for(Object object1: object.getObject()){
            message(path+"."+object.getName(),object1,pw);
        }

        Filed(pw,object.getAllFiled());

        build(object.getName(),object.getAllFiled(),pw,object);

        pw.println("    private "+object.getName()+"( ){");

        pw.println("    }");




        pw.println("}");
    }

    private void build(String ClassName,List<Filed> fileds,PrintWriter pw,Object object){
        pw.println("public class "+ClassName+"Build {");
        BuildFiled(pw,fileds,object);

        pw.println("    private "+ClassName+"Build( ) {");
        pw.println("    }");
        BuildMethod(fileds,pw,ClassName);

        pw.println("}");
    }

    public void Enum( Object object,PrintWriter pw){
        pw.println("public enum "+object.getName()+"{");
        pw.println();
        EnumFiled(pw,object.getAllFiled());

        pw.println("    int num;");
        pw.println("    "+ object.getName()+"( int num ){");
        pw.println("        this.num = num;");
        pw.println("    }");
        pw.println();
        pw.println("    int getNum"+"( int num ){");
        pw.println("        return this.num;");
        pw.println("    }");
        pw.println("}");
    }


    private void BuildFiled(PrintWriter pw, List<Filed> list,Object object){

        Set<Integer> set=new HashSet<>();

        pw.println();
        pw.println();
        pw.println();
        for(Filed filed:list){
            if(filed.getHasOneOf()){
                set.add(filed.getOneIndex());

                pw.println("    "+"private "+"final int "+filed.getOneIndex()+"="+filed.getOneIndex()+";");
            }
            Object object1=map.get(filed.getFiledName());
            if(object1!=null){
                String key=null;
                String value=null;
                for(Filed filed1: object1.getAllFiled()){
                    if(filed1.getFiledName().equals("key")){
                        key=getTypeValue(filed1);
                    }else {
                        value=getTypeValue(filed1);
                    }
                }
                pw.println("    private "+"Map<"+key+","+value+"> "+filed.getFiledName()+" ;");
            }else {
                pw.println("    "+"private "+getTypeValue(filed)+"="+filed.getFiledName()+";");
            }
        }

        pw.println();
        pw.println();

    }

    private void Filed(PrintWriter pw, List<Filed> list){
        pw.println();
        pw.println();
        pw.println();
        for(Filed filed:list){
            Object object1=map.get(filed.getFiledName());
            if(object1!=null){
                String key=null;
                String value=null;
                for(Filed filed1: object1.getAllFiled()){
                    if(filed1.getFiledName().equals("key")){
                        key=getTypeValue(filed1);
                    }else {
                        value=getTypeValue(filed1);
                    }
                }
                pw.println("    private "+"Map<"+key+","+value+"> "+filed.getFiledName()+" ;");
                pw.println();


            }else {
                pw.println("    "+"private "+getTypeValue(filed)+"  "+filed.getFiledName()+";");
                pw.println();
            }

        }

        pw.println();
        pw.println();
    }

    private void EnumFiled(PrintWriter pw, List<Filed> list){
        for(Filed filed: list){
            pw.println("    "+filed.getFiledName()+" ( "+filed.getFiledName()+" ),");
            pw.println();
        }
        pw.println(";");
    }











    public void decode(List<Filed> filed, PrintWriter pw, Object object){
        pw.println("    public "+object.getName()+" decode( ByteBuf byteBuf) {");


        pw.println("    }");
    }


    public void encode(List<Filed> filed,PrintWriter pw, Object object){
        pw.println("    public "+" void "+" encode( ByteBuf byteBuf) {");


        pw.println("    }");
    }

    public void MessageMethod(List<Filed> fileds,PrintWriter pw){

        pw.println();
        pw.println();
        pw.println();
        for(Filed filed:fileds){
            Object object1=map.get(filed.getFiledName());
            if(object1!=null){
                String key=null;
                String value=null;
                for(Filed filed1: object1.getAllFiled()){
                    if(filed1.getFiledName().equals("key")){
                        key=getTypeValue(filed1);
                    }else {
                        value=getTypeValue(filed1);
                    }
                }
                pw.println("    public "+"Map<"+key+","+value+"> "+"get"+filed.getFiledName()+"(){");


                pw.println("        "+"return this."+filed.getFiledName()+";");
                pw.println("    }");
                pw.println();


            }else {
                pw.println("    "+"public "+getTypeValue(filed)+" "+filed.getFiledName()+"(){");
                pw.println("        "+"return this."+filed.getFiledName()+";");
                pw.println("    }");
                pw.println();
            }

        }

        pw.println();
        pw.println();

    }



    public void BuildMethod(List<Filed> filed,PrintWriter pw,String className){
        clear(filed,pw);
        init(filed,pw,className+".Build");
        set(filed,pw,className+"Build");
        newInit(filed,pw,className);
    }

    public void clear(List<Filed> filed,PrintWriter pw){//清除
        StringBuilder a=new StringBuilder();
        for(Filed f: filed){
            pw.println("    public void clear"+f.getFiledName()+"(  ){");
            pw.println("        this."+f.getFiledName()+"=null;");
            pw.println("    }");
            a.append("      clear").append(f.getFiledName()).append(";\n\n");

        }

        pw.println("    public void clear(){");
        pw.println(a);
        pw.println("    }");

    }

    public void init(List<Filed> filed,PrintWriter pw,String className){//初始化
        pw.println("    public start "+className+" init(){");
        pw.println("        "+className+" a = new "+className+"();");

        for(int i=0;i<filed.size(); i++){
            Filed f=filed.get(i);
            Object o=map.get(f.getFileTypeName());
            if(o!=null){
                pw.println("        a."+f.getFiledName()+"= new HashMap<>();\n");
            }else if(f.getFileType().getType().equals(FiledType.Message.getType())){
                if(f.getFiledLabel().getLabel().equals(FiledLabel.Repeated.getLabel())){
                    pw.println("        a."+f.getFiledName()+"= new ArrayList<>();\n");
                }
            }else if(f.getFileType().getType().equals(FiledType.Enum.getType())){
                if(f.getFiledLabel().getLabel().equals(FiledLabel.Repeated.getLabel())){
                    pw.println("        a."+f.getFiledName()+"= new ArrayList<>();\n");
                }
            }else {
                if(f.getFiledLabel().getLabel().equals(FiledLabel.Repeated.getLabel())){
                    pw.println("        a."+f.getFiledName()+"= new ArrayList<>();\n");
                }
            }
            pw.println("        return a;");
            pw.println("    }");


        }

    }


    public void set(List<Filed> filed,PrintWriter pw,String ClassName){//设置值
        for(int i=0;i<filed.size(); i++){
            Filed f=filed.get(i);
            Object o=map.get(f.getFileTypeName());
            if(o!=null){
                String key=null;
                String value=null;
                for(Filed filed1: o.getAllFiled()){
                    if(filed1.getFiledName().equals("key")){
                        key=getTypeValue(filed1);
                    }else {
                        value=getTypeValue(filed1);
                    }
                }
                pw.println("    public "+ClassName+" put("+key+ " key,"+value+" value){");
                pw.println("        "+f.getFiledName()+".put(key,value);");
                pw.println("        return this;");
                pw.println("    }");

                pw.println("    public "+ClassName+" remove("+key+ " key){");
                pw.println("        "+f.getFiledName()+".remove(key);");
                pw.println("        return this;");
                pw.println("    }");

            }else {
                if(f.getFiledLabel().getLabel().equals(FiledLabel.Repeated.getLabel())){
                    pw.println("    public "+ClassName+" add("+getTypeValue(f)+ " value){");
                    pw.println("        "+f.getFiledName()+".add(value);");
                    pw.println("        return this;");
                    pw.println("    }");

                    pw.println("    public "+ClassName+" add(int index,"+getTypeValue(f)+ " value){");
                    pw.println("        "+f.getFiledName()+".add(index,value);");
                    pw.println("        return this;");
                    pw.println("    }");

                    pw.println("    public "+ClassName+" remove(int index ){");
                    pw.println("        "+f.getFiledName()+".remove(index);");
                    pw.println("        return this;");
                    pw.println("    }");

                    pw.println("    public "+ClassName+" get(int index ){");
                    pw.println("        "+f.getFiledName()+".get(index);");
                    pw.println("        return this;");
                    pw.println("    }");

                    pw.println("    public "+ClassName+" get(int index ){");
                    pw.println("        "+f.getFiledName()+".get(index);");
                    pw.println("        return this;");
                    pw.println("    }");
                }else {
                    pw.println("    public "+ClassName+" set"+f.getFiledName()+"("+getTypeValue(f)+" value ){");
                    pw.println("        this."+f.getFiledName()+" = value ;");
                    pw.println("        return this;");
                    pw.println("    }");
                }
            }

        }
    }

    public void newInit(List<Filed> filed,PrintWriter pw,String ClassName){//创建message类

        pw.println("    public "+ClassName+" newInit(){");
        pw.println(""+ClassName+" a=new "+ClassName+"();");
        for(Filed f:filed){

            Object o=map.get(f.getFileTypeName());
            if(o!=null) {//根据属性标签进行生成代码

            }else if(f.getFiledLabel().getLabel().equals(FiledLabel.Repeated.getLabel())){

            }else if(f.getFiledLabel().getLabel().equals(FiledLabel.Optional.getLabel())){

            }else if(f.getFiledLabel().getLabel().equals(FiledLabel.Required.getLabel())){

            }

        }
        pw.println("    }");
    }



    private String getTypeValue(Filed filed){
        String Label=filed.getFiledLabel().getLabel();
        String type=filed.getFileType().getType();
        if(type.equals(FiledType.Bytes.getType())){
            if(Label.equals(FiledLabel.Repeated.getLabel())){
                return " "+"List<byte[]>"+" ";
            }else {
                return " "+"byte[]"+" ";
            }
        }else if(type.equals(FiledType.Message.getType())||type.equals(FiledType.Enum.getType())){
            if(Label.equals(FiledLabel.Repeated.getLabel())){
                return " "+"List<"+filed.getFileTypeName()+">"+" ";
            }else {
                return " "+filed.getFileTypeName()+" ";
            }
        }else {
            if(Label.equals(FiledLabel.Repeated.getLabel())){
                return " "+"List<"+filed.getFileType().getJavaClass().getName()+">"+" ";
            }else {
                return " "+filed.getFileType().getJavaClass().getName()+" ";
            }
        }
    }


//    private int getTag(int num,FiledType filedType){
//
//        return 0;
//    }


}

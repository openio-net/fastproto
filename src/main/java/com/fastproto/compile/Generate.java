package com.fastproto.compile;

import com.fastproto.config.Config;
import com.fastproto.wrapper.*;
import com.fastproto.wrapper.Object;

import java.io.*;
import java.util.*;

public class Generate {

    private final Map<String,Object> map=new HashMap();

    public void Generate(List<com.fastproto.wrapper.Package> list, Config config){
        File file=new File(config.getFileDir());
        if(!file.isDirectory()){
            throw new RuntimeException(" config is wrong ");
        }
        for(com.fastproto.wrapper.Package pack:list) {
            String[] pN =pack.getPackageName().split("\\.");
            StringBuilder path= new StringBuilder(config.getFileDir());
            for (String value : pN) {
                path.append(value).append("\\");
            }
//             File file1 = new File(path.toString());
//            try {
//                if(!file1.createNewFile()){
//                    throw new RuntimeException("create javaFile is Wrong");
//                }
//            }catch (Exception e){
//                throw new RuntimeException("create javaFile is Wrong");
//            }



            String Package="package "+pack.getPackageName()+"\n";

            for(Object o : pack.getAllObject()){
                File file2=new File(path+o.getName()+".java");
//                try {
//                    file2.createNewFile();
//                } catch (IOException e) {
//                    throw new RuntimeException("create javaFile is wrong");
//                }
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
                pw.println("import "+"java.unit.Set;");
                pw.println("import "+"java.unit.HashSet;");
                pw.println("import "+"java.unit.List;");
                pw.println("import "+"java.unit.Map;");
                pw.println("import "+"java.unit.ArrayList;");
                pw.println("import "+"java.unit.HashMap;");
                pw.println("import io.netty.buffer.ByteBuf;");

                if(o.getObjectType().getType().equals(ObjectType.Enum.getType())){
                    Enum(o,pw);
                }else if(o.getObjectType().getType().equals(ObjectType.Message.getType())){
                    message(pack.getPackageName(),o,pw);
                }
                System.out.println(stringWriter);
                pw.flush();

            }

        }
    }

    private void message(String path,Object object,PrintWriter pw){
        if(object.getObjectType().getType().equals(ObjectType.Map.getType())){
//            System.out.println(path+"."+object.getName());
            map.put(path+"."+object.getName(),object);
            return;
        }
        if(object.getObjectType().getType().equals(ObjectType.Enum.getType())){
            Enum(object,pw);
            return;
        }
        pw.println("public class "+object.getName()+"{");

        for(Object object1: object.getObject()){
            message(path+"."+object.getName(),object1,pw);
        }

        Filed(pw,object.getAllFiled());

        MessageMethod(object.getAllFiled(),pw);

        build(object.getName(),object.getAllFiled(),pw);

        pw.println("    private "+object.getName()+"( ){");

        pw.println("    }");


        pw.println("}");
    }

    private void build(String ClassName,List<Filed> filed,PrintWriter pw){
        pw.println("public class "+ClassName+"Build {");
        BuildFiled(pw,filed);

        pw.println("    private "+ClassName+"Build( ) {");
        pw.println("    }");
        BuildMethod(filed,pw,ClassName);

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


    private void BuildFiled(PrintWriter pw, List<Filed> list){


        Set<Integer> oneOfSet=new HashSet<>();
        pw.println();
        pw.println();
        pw.println();
        for(Filed filed:list){
            if(filed.getHasOneOf()){
                oneOfSet.add(filed.getOneIndex());
            }
            Object object1=map.get(filed.getFileTypeName());
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
                pw.println("        private "+"Map<"+key+","+value+">  "+filed.getFiledName()+" ;");
            }else {
                pw.println("        "+"private "+getTypeValue(filed)+" "+filed.getFiledName()+";");
            }
            pw.println();
            pw.println("        private final static int "+ filed.getFiledName()+"Tag = "+getTag(filed)+";");
            pw.println();
        }
        for(int i:oneOfSet){
            pw.println("        private int  endSet"+i+"= -1 ;");
            pw.println();
        }
        pw.println();
        pw.println();

    }

    private void Filed(PrintWriter pw, List<Filed> list){
        pw.println();
        pw.println();
        pw.println();
        for(Filed filed:list){
            Object object1=map.get(filed.getFileTypeName());
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
            pw.println("    "+filed.getFiledName()+" ( "+filed.getTag()+" ),");
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
        clear(filed,pw,className+"Build");
        init(filed,pw,className+"Build");
        set(filed,pw,className+"Build");
        newInit(filed,pw,className);
    }

    public void clear(List<Filed> filed,PrintWriter pw,String ClassName) {//清除
        Set<Integer> OneOfSet = new HashSet<>();
        Map<Integer, List<Filed>> OneOfList = new HashMap<>();
        StringBuffer clear = new StringBuffer();
        for (int i = 0; i < filed.size(); i++) {
            Filed f = filed.get(i);
            if (f.getHasOneOf()) {
                if (OneOfSet.contains(f.getOneIndex())) {
                    OneOfList.get(f.getOneIndex()).add(f);
                    continue;
                }
                OneOfSet.add(f.getOneIndex());
                List<Filed> fileds = new ArrayList<>();
                fileds.add(f);
                OneOfList.put(f.getOneIndex(), fileds);
                continue;

            }
            Object o = map.get(f.getFileTypeName());


            if (o != null) {

                clear.append("      ").append(f.getFiledName()).append("clear( );\n");
                pw.println("    public " + ClassName + " " + f.getFiledName() + "clear( ){");
                pw.println("        this." + f.getFiledName() + "=new HashMap<>();");
                pw.println("        return this;");
                pw.println("    }");
            } else {
                clear.append("      ").append(f.getFiledName()).append("clear( );\n");
                if (f.getFiledLabel().getLabel().equals(FiledLabel.Repeated.getLabel())) {
                    pw.println("    public " + ClassName + " " + f.getFiledName() + "clear(){");
                    pw.println("        this." + f.getFiledName() + "=new ArrayList();");
                    pw.println("        return this;");
                    pw.println("    }");


                } else {
                    clear.append("      ").append(f.getFiledName()).append("clear( );\n");
                    pw.println("    public " + ClassName + " " + f.getFiledName() + "clear( ){");
                    pw.println("        this." + f.getFiledName() + " = null ;");
                    pw.println("        return this;");
                    pw.println("    }");
                }
            }

        }

        for (int i : OneOfSet) {
            clear.append("          oneOf").append(i).append("clear").append("();\n");
            pw.println("    public " + ClassName + " oneOf" + i + "clear" + "  (){");
            pw.println("        endSet" + i + "=-1");
            for (Filed filed1 : OneOfList.get(i)) {
                Object o = map.get(filed1.getFileTypeName());
                if (o != null) {

                    pw.println("        this." + filed1.getFiledName() + "=new HashMap<>();");

                } else {
                    if (filed1.getFiledLabel().getLabel().equals(FiledLabel.Repeated.getLabel())) {

                        pw.println("        this." + filed1.getFiledName() + "=new ArrayList();");


                    } else {

                        pw.println("        this." + filed1.getFiledName() + " = null ;");

                    }


                }
            }
            pw.println("    }");
        }

        pw.println("    public " + ClassName + " clear(){");
        pw.println(clear);
        pw.println("    }");

    }



    public void init(List<Filed> filed,PrintWriter pw,String className){//初始化

        pw.println("    public static "+className+" init(){");
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

        }
        pw.println("        return a;");
        pw.println("    }");

    }


    public void set(List<Filed> filed,PrintWriter pw,String ClassName){//设置值
        Set<Integer> OneOfSet=new HashSet<>();
        Map<Integer,List<Filed>> OneOfList=new HashMap<>();
        for(int i=0;i<filed.size(); i++){
            Filed f=filed.get(i);
            if(f.getHasOneOf()){
                if(OneOfSet.contains(f.getOneIndex())){
                    OneOfList.get(f.getOneIndex()).add(f);
                    continue;
                }
                OneOfSet.add(f.getOneIndex());
                List<Filed> fileds=new ArrayList<>();
                fileds.add(f);
                OneOfList.put(f.getOneIndex(),fileds);
                continue;

            }
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
        setOneOfFile(pw,OneOfSet,OneOfList,ClassName);
    }


    public void setOneOfFile(PrintWriter pw,Set<Integer> os,Map<Integer,List<Filed>> om,String className){
        for(int i:os){

            for(Filed filed:om.get(i)){
                pw.println("    public "+className+" setOneOf"+i+filed.getFiledName()+"  ("+getTypeValue(filed)+" value ){");
                pw.println("        if(endSet"+i+"==-1){");
                pw.println("            this."+filed.getFiledName()+" = value;");
                pw.println("        }else{");
                pw.println("            endSet"+i+"="+filed.getTag()+";");
                pw.println("            this."+filed.getFiledName()+" = value;");
                pw.println("        }");
                pw.println("    }");
            }
        }
    }



    public void newInit(List<Filed> filed,PrintWriter pw,String ClassName){//创建message类

        pw.println("    public "+ClassName+" newInit(){");
        pw.println("        "+ClassName+" a=new "+ClassName+"();");
        for(Filed f:filed){
            Object o=map.get(f.getFileTypeName());
            if(o!=null) {//根据属性标签进行生成代码
                pw.println("        a."+f.getFiledName()+" = this."+f.getFiledName()+" ;");
            }else if(f.getFiledLabel().getLabel().equals(FiledLabel.Repeated.getLabel())){
                pw.println("        a."+f.getFiledName()+" = this."+f.getFiledName()+" ;");
            }else if(f.getFiledLabel().getLabel().equals(FiledLabel.Optional.getLabel())){
                pw.println("        a."+f.getFiledName()+" = this."+f.getFiledName()+" ;");
            }else if(f.getFiledLabel().getLabel().equals(FiledLabel.Required.getLabel())){
                pw.println("        if("+f.getFiledName()+"==null){");
                pw.println("            throw new RuntimeException(\"this filed is require\");\n\n");
                pw.println("        }");
                pw.println("        a."+f.getFiledName()+" = this."+f.getFiledName()+" ;");
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

    private int getTag(Filed filed){
        return (filed.getTag()<<3)|getWireType(filed);

    }

    private int getWireType(Filed filed){
        String type=filed.getFileType().getType();
        String label=filed.getFiledLabel().getLabel();
        if(label.equals(FiledLabel.Repeated.getLabel())){
            return 2;
        }else if(type.equals(FiledType.String.getType())
                ||type.equals(FiledType.Message.getType())
                ||type.equals(FiledType.Bytes.getType())){
            return 2;
        }else if(type.equals(FiledType.sFixed64.getType())
                ||type.equals(FiledType.Fixed64.getType())
                ||type.equals(FiledType.Double.getType())){
            return 1;
        }else if(type.equals(FiledType.sFixed32.getType())
                ||type.equals(FiledType.Fixed32.getType())
                ||type.equals(FiledType.Float.getType())) {
            return 5;
        }
     return 0;
    }


}

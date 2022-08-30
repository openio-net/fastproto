package com.fastproto.compile;

import com.fastproto.config.Config;
import com.fastproto.wrapper.*;
import com.fastproto.wrapper.Object;


import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.*;
import java.nio.file.Files;
import java.util.*;

public class Generate {

    private final Map<String,Object> map=new HashMap();



    public void Generate(List<com.fastproto.wrapper.Package> list, Config config) throws IOException {
        File file=new File(config.getJavaOut());
        if(!file.isDirectory()){
            throw new RuntimeException(" config is wrong ");
        }
        for(com.fastproto.wrapper.Package pack:list) {
            String[] pN =pack.getPackageName().split("\\.");
            StringBuilder path= new StringBuilder(config.getJavaOut());
            for (String value : pN) {
                path.append(value).append("\\");
            }
            System.out.println(path.substring(0,path.length()-1));
             File file1 = new File(path.substring(0,path.length()-1));
            try {

                if(!file1.exists()&&!file1.mkdirs()){
                    throw new RuntimeException("create javaFile is Wrong");
                }
            }catch (Exception e){
                e.printStackTrace();
                throw new RuntimeException("create javaFile is Wrong");
            }



            String Package="package "+pack.getPackageName()+";\n";

            for(Object o : pack.getAllObject()){
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
                pw.println("import "+"java.util.Set;");
                pw.println("import "+"java.util.HashSet;");
                pw.println("import "+"java.util.List;");
                pw.println("import "+"java.util.Map;");
                pw.println("import "+"java.util.ArrayList;");
                pw.println("import "+"java.util.HashMap;");
                pw.println("import io.netty.buffer.ByteBuf;");
                pw.println("import com.fastproto.serializer.Serializer;");
                pw.println("import com.fastproto.compile.DecodeMethod;");
                pw.println("import io.netty.buffer.Unpooled;");
                pw.println();

                if(o.getObjectType().getType().equals(ObjectType.Enum.getType())){
                    Enum(o,pw);
                }else if(o.getObjectType().getType().equals(ObjectType.Message.getType())){
                    message(pack.getPackageName(),o,pw,"");
                }
                pw.flush();

                File file2=new File(path+o.getName()+".java");//创建文件

                Writer w=null;
                try {
                    file2.createNewFile();
//                    String formattedCode = Roaster.format(stringWriter.toString());
                    w=Files.newBufferedWriter(file2.toPath());
                    w.write(stringWriter.toString());

                } catch (Exception e) {
                    e.printStackTrace();
                    throw new RuntimeException("create javaFile is wrong");
                }finally {
                    if(w!=null)
                    w.close();
                }

            }

        }
    }

    private void message(String path,Object object,PrintWriter pw,String a){
        if(object.getObjectType().getType().equals(ObjectType.Map.getType())){
            map.put(path+"."+object.getName(),object);
            return;
        }
        if(object.getObjectType().getType().equals(ObjectType.Enum.getType())){
            Enum(object,pw);
            return;
        }
        pw.println("public "+a+" class "+object.getName()+"{");

        for(Object object1: object.getObject()){
            message(path+"."+object.getName(),object1,pw,"static");
        }

        Filed(pw,object.getAllFiled());

        encode(object.getAllFiled(),pw);

        MessageMethod(object.getAllFiled(),pw);

        build(object.getName(),object.getAllFiled(),pw);

        pw.println("    private "+object.getName()+"( ){");

        pw.println("    }");


        pw.println("}");
        pw.println();

    }

    private void build(String ClassName,List<Filed> filed,PrintWriter pw){
        pw.println("public  static class "+ClassName+"Build {");
        BuildFiled(pw,filed);
        decodeMethodTable(pw, filed,ClassName);
        decode(pw,ClassName);

        BuildMethod(filed,pw,ClassName);

        pw.println("}");


    }

    private void decodeMethodTable(PrintWriter pw,List<Filed> filed,String ClassName){
        Set<Integer> oneOfSet=new HashSet<>();
        pw.println("    private final static Map<Integer,DecodeMethod> decode =new HashMap();");
        pw.println();
        pw.println();
        pw.println("    static {");
        for(Filed f: filed){
            pw.println("        decode.put("+getTag(f)+",(ByteBuf byteBuf,int tag1,Object object)->{");
            String type=f.getFileType().getType();
            String label=f.getFiledLabel().getLabel();

                Object object1=map.get(f.getFileTypeName());
                if(object1!=null){//判断是否是Map
                    Filed key=null;
                    Filed value=null;
                    for(Filed filed1: object1.getAllFiled()){
                        if(filed1.getFiledName().equals("key")){
                            key=filed1;
                        }else {
                            value=filed1;
                        }
                    }
                    pw.println("        "+ClassName+"Build "+"o = ("+ClassName+"Build)object;");
                    pw.println("        int length=byteBuf.readerIndex()+Serializer.decodeVarInt32(byteBuf);");
                    pw.println("//        ByteBuf buf=byteBuf.readBytes(length);");
                    pw.println("        "+getTypeValue(key)+" key=null;");
                    pw.println("        "+getTypeValue(value)+" value=null;");
                    pw.println("        int tag=Serializer.decodeVarInt32(byteBuf);");
                    pw.println("        if(tag=="+getTag(key)+"){");
                    pw.print("            key= "); filedDecode(key,pw,"byteBuf") ;pw.println(" ;");
                    pw.println("        }else if(tag=="+getTag(value)+"){");
                    pw.print("            value=");filedDecode(value,pw,"byteBuf");pw.println(";");
                    pw.println("        }");
                    pw.println("         tag=Serializer.decodeVarInt32(byteBuf);");
                    pw.println("        if(tag=="+getTag(key)+"){");
                    pw.print("            key= "); filedDecode(key,pw,"byteBuf") ;pw.println(" ;");
                    pw.println("        }else if(tag=="+getTag(value)+"){");
                    pw.print("            value=");filedDecode(value,pw,"byteBuf");pw.println(";");
                    pw.println("        }");
                    pw.println("        if(key==null||value==null){");
                    pw.println("            throw new RuntimeException(\" "+f.getFiledName()+" decode is wrong\");");
                    pw.println("        }");
                    pw.println("        o.put"+f.getFiledName()+"(key,value);");
                }else if(label.equals(FiledLabel.Repeated.getLabel())){
                    boolean ob=false;//判断是否未packed
                    for(Option os :f.getAllOption()){
                        if(os.getKey().equals("packed")){
                            ob= (boolean) os.getValue();
                        }
                    }
                    if(ob){
                        pw.println("        "+ClassName+"Build "+"o = ("+ClassName+"Build)object;");
                        pw.println("        int fLength=Serializer.decodeVarInt32(byteBuf);");
                        pw.println("        ByteBuf buf=byteBuf.readBytes(fLength);");
                        pw.println("        while(buf.readerIndex()!=fLength){");
                        pw.print("              o.add"+f.getFiledName()+"(");
                        filedDecode(f,pw,"buf");
                        pw.print(");\n");
                        pw.println("        }");
                    }else {
                        pw.println("        "+ClassName+"Build "+"o = ("+ClassName+"Build)object;");
                        pw.print("      o.add"+f.getFiledName()+"(");
                        filedDecode(f,pw,"byteBuf");
                        pw.print(");");
                    }
                }else {
                    pw.println("        "+ClassName+"Build "+"o = ("+ClassName+"Build)object;");
                    if(f.getHasOneOf()) {
                        pw.print("        o.setOneOf"+f.getOneIndex() + f.getFiledName() + "(\n          ");
                    }else {
                        pw.print("        o.set"+ f.getFiledName() + "(\n          ");
                    }
                    filedDecode(f,pw,"byteBuf");
                    pw.print(");");
                }

            pw.println("        });");
            pw.println();
            pw.println();

            }




        pw.println("    }");
        pw.println();
        pw.println();
        pw.println();
    }

    private void filedDecode(Filed filed,PrintWriter pw,String bufName){
        String type=filed.getFileType().getType();
        if(type.equals(FiledType.Message.getType())){
            String[] name=filed.getFileTypeName().split("\\.");
            if(name.length>0) {
                pw.print(filed.getFileTypeName() + "." + name[name.length - 1] + "Build.decode(" + bufName + ",Serializer.decodeVarInt32(" + bufName + "))");
            }else {
                pw.print(filed.getFileTypeName() + "." + filed.getFiledName() + "Build.decode(" + bufName + ",Serializer.decodeVarInt32(" + bufName + "))");

            }
        }else if(type.equals(FiledType.Enum.getType())){
            pw.print(filed.getFileTypeName()+".get(Serializer.decodeVarInt32("+bufName+"))");
        }else if(type.equals(FiledType.Float.getType())){
            pw.print("Serializer.decodeFloat("+bufName+")");
        }else if(type.equals(FiledType.String.getType())){
            pw.print("Serializer.decodeString("+bufName+",Serializer.decodeVarInt32("+bufName+"))");
        }else if(type.equals(FiledType.Bytes.getType())){
            pw.print("Serializer.decodeByteString("+bufName+",Serializer.decodeVarInt32("+bufName+"))");
        }else if(type.equals(FiledType.Double.getType())){
            pw.print("Serializer.decodeDouble("+bufName+")");
        }else if(type.equals(FiledType.Fixed32.getType())){
            pw.print("Serializer.decode32("+bufName+")");
        }else if(type.equals(FiledType.Fixed64.getType())){
            pw.print("Serializer.decode64("+bufName+")");
        }else if(type.equals(FiledType.sFixed32.getType())){
            pw.print("Serializer.decode32("+bufName+")");
        }else if(type.equals(FiledType.sFixed64.getType())){
            pw.print("Serializer.decode64("+bufName+")");
        }else if(type.equals(FiledType.uInt64.getType())){
            pw.print("Serializer.decodeVarInt64("+bufName+")");
        }else if(type.equals(FiledType.uInt32.getType())){
            pw.print("Serializer.decodeVarInt32("+bufName+")");
        }else if(type.equals(FiledType.sInt64.getType())){
            pw.print("Serializer.decodeZigzag64(Serializer.decodeVarInt64("+bufName+"))");
        }else if(type.equals(FiledType.sInt32.getType())){
            pw.print("Serializer.decodeZigzag32(Serializer.decodeVarInt32("+bufName+"))");
        }else if(type.equals(FiledType.Int32.getType())){
            pw.print("Serializer.decodeVarInt32("+bufName+")");
        }else if(type.equals(FiledType.Int64.getType())){
            pw.print("Serializer.decodeVarInt64("+bufName+")");
        }else if(type.equals(FiledType.Bool.getType())){
            pw.print("Serializer.decodeBoolean("+bufName+")");
        }

    }

    private void Enum( Object object,PrintWriter pw){
        pw.println("public enum "+object.getName()+"{");
        pw.println();
        EnumFiled(pw,object.getAllFiled(),object.getName());

        pw.println("    int num;");
        pw.println();
        pw.println("    "+ object.getName()+"( int num ){");
        pw.println("        this.num = num;");
        pw.println("    }");
        pw.println();
        pw.println("    int getNum"+"( ){");
        pw.println("        return this.num;");
        pw.println("    }");
        pw.println("}");
        pw.println();
        pw.println();
        pw.println();
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

    private void EnumFiled(PrintWriter pw, List<Filed> list,String className){
        StringBuilder s=new StringBuilder(list.size()*30);
        s.append("  public static ").append(className).append(" get(int tag){");
        for(Filed filed: list){
            pw.println("    "+filed.getFiledName()+" ( "+filed.getTag()+" ),");
            pw.println();
            s.append("      if(tag==").append(filed.getTag()).append("){\n");
            s.append("          return ").append(className).append(".").append(filed.getFiledName()).append(";\n");
            s.append("  }\n");
        }
        pw.println(";");
        s.append("      return null;\n    }");
        pw.println(s);

        pw.println();
        pw.println();
        pw.println();
    }











    private void decode( PrintWriter pw, String  className){

        pw.println("    public static "+className+" decode( ByteBuf byteBuf) {");
        pw.println("            "+className+"Build"+" o=new "+className+"Build();");
        pw.println("             while (byteBuf.readerIndex()<byteBuf.writerIndex()){");
        pw.println("                int tag=Serializer.decodeVarInt32(byteBuf);");

        pw.println("                ((DecodeMethod)decode.get(tag)).decode(byteBuf,tag,o);");
        pw.println("            }");
        pw.println("            return o.IsInitialized();");

        pw.println("    }");
        pw.println();
        pw.println();

        pw.println("    public static "+className+" decode( ByteBuf byteBuf,int length) {");
        pw.println("            "+className+"Build"+" o=new "+className+"Build();");
        pw.println("            int len=byteBuf.readerIndex()+length;");
        pw.println("            while(byteBuf.readerIndex()<len){");
        pw.println("                int tag=Serializer.decodeVarInt32(byteBuf);");

        pw.println("                ((DecodeMethod)decode.get(tag)).decode(byteBuf,tag,o);");
        pw.println("            }");
        pw.println("            return o.IsInitialized();");

        pw.println("    }");

    }


    private void encode(List<Filed> filed,PrintWriter pw){
        pw.println("    public "+" void "+" encode( ByteBuf byteBuf) {");
        for(Filed f: filed){
            String type=f.getFileType().getType();
            String label=f.getFiledLabel().getLabel();
            Object object1=map.get(f.getFileTypeName());
            if(object1!=null) {//判断是否是Map
                Filed key = null;
                Filed value = null;
                for (Filed filed1 : object1.getAllFiled()) {
                    if (filed1.getFiledName().equals("key")) {
                        key = filed1;
                    } else {
                        value = filed1;
                    }
                }
                pw.println("            int k"+ f.getFiledName()+"Tag="+getTag(key)+";");
                pw.println("            int v"+f.getFiledName()+"Tag="+getTag(value)+";");


                pw.println("        for("+getTypeValue(key) +" key : this."+f.getFiledName()+".keySet()){");
                pw.println("                Serializer.encodeVarInt32(byteBuf,"+getTag(f)+");");
                pw.println("            "+getTypeValue(value)+" value="+f.getFiledName()+".get(key);");
                pw.println("            ByteBuf buf = Unpooled.buffer(1024);");
                pw.println("            Serializer.encodeVarInt32(buf,k"+ f.getFiledName()+"Tag);");
                encodeFiled(pw,key,"buf","key");
                pw.println("            Serializer.encodeVarInt32(buf,v"+f.getFiledName()+"Tag);");
                encodeFiled(pw,value,"buf","value");
                pw.println("            Serializer.encodeVarInt32(byteBuf,buf.writerIndex());");
                pw.println("            byteBuf.writeBytes(buf);");
                pw.println("");
                pw.println("        }");
                pw.println();
                pw.println();
            }else if(label.equals(FiledLabel.Repeated.getLabel())){
                boolean b=false;
                for(Option o : f.getAllOption()){
                    if(o.getKey().equals("packed")){
                        b= (boolean) o.getValue();
                        break;
                    }
                }
                if(b){
                    pw.println("            ByteBuf buf"+f.getFiledName()+";");
                    pw.println("            if("+f.getFiledName()+".size()!=0){");
                    pw.println("                Serializer.encodeVarInt32(byteBuf,"+getTag(f)+");");
                    pw.println("                buf"+f.getFiledName()+" = Unpooled.buffer(1024);");
                    pw.println("            }");
                    pw.println("           for(int i=0;i<"+f.getFiledName()+".size();i++){");
                    encodeFiled(pw,f,"buf"+f.getFiledName(),f.getFiledName()+".get(i)");
                    pw.println("           }");
                    pw.println("            Serializer.encodeVarInt32(byteBuf,buf"+f.getFiledName()+".writerIndex());");
                    pw.println("            byteBuf.writeBytes(buf"+f.getFiledName()+");");
                    pw.println();
                    pw.println();
                }else {
                    pw.println("           for(int i=0;i<"+f.getFiledName()+".size();i++){");
                    pw.println("              Serializer.encodeVarInt32(byteBuf,"+getTag(f)+");");
                    encodeFiled(pw,f,"byteBuf",f.getFiledName()+".get(i)");
                    pw.println("           }");
                }
            }else {
                pw.println("        if("+f.getFiledName()+"!=null){");
                pw.println("              Serializer.encodeVarInt32(byteBuf,"+getTag(f)+");");
                    encodeFiled(pw,f,"byteBuf",f.getFiledName());
                pw.println("        }");
                pw.println();
                pw.println();
            }
        }

        pw.println("    }");
        pw.println();
        pw.println();
    }


    private void encodeFiled(PrintWriter pw,Filed f,String bufName,String filedName){
        String type=f.getFileType().getType();
        if(type.equals(FiledType.Message.getType())){
            pw.println("                ByteBuf b"+f.getFiledName()+" = Unpooled.buffer(1024);");
            pw.println("            "+filedName+".encode(b"+f.getFiledName()+");");
            pw.println("             Serializer.encodeVarInt32("+bufName+",b"+f.getFiledName()+".writerIndex());");
            pw.println("            "+bufName+".writeBytes(b"+f.getFiledName()+");");
        }else if(type.equals(FiledType.Enum.getType())){
            pw.println("            Serializer.encodeVarInt32("+bufName+","+filedName+".getNum());");
        }else if(type.equals(FiledType.Float.getType())){
            pw.println("            Serializer.encodeFloat("+bufName+","+filedName+");");
        }else if(type.equals(FiledType.String.getType())){
            pw.println("            Serializer.encodeString("+bufName+","+filedName+");");
        }else if(type.equals(FiledType.Bytes.getType())){
            pw.println("            Serializer.encodeVarInt32("+bufName+","+filedName+".length);");
            pw.println("            Serializer.encodeByteString("+bufName+","+filedName+");");
        }else if(type.equals(FiledType.Double.getType())){
            pw.println("            Serializer.encodeDouble("+bufName+","+filedName+");");
        }else if(type.equals(FiledType.Fixed32.getType())){
            pw.println("            Serializer.encode32("+bufName+","+filedName+");");
        }else if(type.equals(FiledType.Fixed64.getType())){
            pw.println("            Serializer.encode64("+bufName+","+filedName+");");
        }else if(type.equals(FiledType.sFixed32.getType())){
            pw.println("            Serializer.encode32("+bufName+","+filedName+");");
        }else if(type.equals(FiledType.sFixed64.getType())){
            pw.println("             Serializer.encode64("+bufName+","+filedName+");");
        }else if(type.equals(FiledType.uInt64.getType())){
            pw.println("            Serializer.encodeVarInt64("+bufName+","+filedName+");");
        }else if(type.equals(FiledType.uInt32.getType())){
            pw.println("            Serializer.encodeVarInt32("+bufName+","+filedName+");");
        }else if(type.equals(FiledType.sInt64.getType())){
            pw.println("            Serializer.encodeVarInt64("+bufName+",Serializer.encodeZigzag64("+filedName+"));");
        }else if(type.equals(FiledType.sInt32.getType())){
            pw.println("            Serializer.encodeVarInt32("+bufName+",Serializer.encodeZigzag32("+filedName+"));");
        }else if(type.equals(FiledType.Int32.getType())){
            pw.println("            Serializer.encodeVarInt32("+bufName+","+filedName+");");
        }else if(type.equals(FiledType.Int64.getType())){
            pw.println("            Serializer.encodeVarInt64("+bufName+","+filedName+");");
        }else if(type.equals(FiledType.Bool.getType())){
            pw.println("            Serializer.encodeBoolean("+bufName+","+filedName+");");
        }
    }



    private void MessageMethod(List<Filed> fileds,PrintWriter pw){
        pw.println();
        pw.println();
        pw.println();
        for(Filed filed:fileds){
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
                pw.println("    public "+"Map<"+key+","+value+"> "+"get"+filed.getFiledName()+"(){");

                pw.println("        "+"return this."+filed.getFiledName()+";");
                pw.println("    }");
                pw.println();

            }else {
                pw.println("    "+"public "+getTypeValue(filed)+" get"+filed.getFiledName()+"(){");
                pw.println("        "+"return this."+filed.getFiledName()+";");
                pw.println("    }");
                pw.println();
            }

        }

        pw.println();
        pw.println();
        pw.println();
        pw.println();
        pw.println();

    }



    private void BuildMethod(List<Filed> filed,PrintWriter pw,String className){
        clear(filed,pw,className+"Build");
        init(filed,pw,className+"Build");
        set(filed,pw,className+"Build");
        IsInitialized(filed,pw,className);

    }

    private void clear(List<Filed> filed,PrintWriter pw,String ClassName) {//清除
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
            pw.println();
            pw.println();

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
            pw.println();
            pw.println();
            pw.println();
        }

        pw.println("    public " + ClassName + " clear(){");
        pw.println(clear);
        pw.println("        return this;");
        pw.println("    }");
        pw.println();
        pw.println();
        pw.println();

    }



    private void init(List<Filed> filed,PrintWriter pw,String className){//初始化
        pw.println("    public "+className+"( ) {");

        for(int i=0;i<filed.size(); i++){
            Filed f=filed.get(i);
            Object o=map.get(f.getFileTypeName());
            if(o!=null){
                pw.println("        this."+f.getFiledName()+"= new HashMap<>();\n");
            }else if(f.getFileType().getType().equals(FiledType.Message.getType())){
                if(f.getFiledLabel().getLabel().equals(FiledLabel.Repeated.getLabel())){
                    pw.println("        this."+f.getFiledName()+"= new ArrayList<>();\n");
                }
            }else if(f.getFileType().getType().equals(FiledType.Enum.getType())){
                if(f.getFiledLabel().getLabel().equals(FiledLabel.Repeated.getLabel())){
                    pw.println("        this."+f.getFiledName()+"= new ArrayList<>();\n");
                }
            }else {
                if(f.getFiledLabel().getLabel().equals(FiledLabel.Repeated.getLabel())){
                    pw.println("        this."+f.getFiledName()+"= new ArrayList<>();\n");
                }
            }

        }
        pw.println("    }");
        pw.println();
        pw.println();
        pw.println();

    }


    private void set(List<Filed> filed,PrintWriter pw,String ClassName){//设置值
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
                pw.println("    public "+ClassName+" put"+f.getFiledName()+"("+key+ " key,"+value+" value){");
                pw.println("        "+f.getFiledName()+".put(key,value);");
                pw.println("        return this;");
                pw.println("    }");
                pw.println();

                pw.println("    public "+ClassName+" remove"+f.getFiledName()+"("+key+ " key){");
                pw.println("        "+f.getFiledName()+".remove(key);");
                pw.println("        return this;");
                pw.println("    }");
                pw.println();
                pw.println();


            }else {
                if(f.getFiledLabel().getLabel().equals(FiledLabel.Repeated.getLabel())){
                    pw.println("    public "+ClassName+" add"+f.getFiledName()+"("+getRepeatedType(f)+ " value){");
                    pw.println("        "+f.getFiledName()+".add(value);");
                    pw.println("        return this;");
                    pw.println("    }");
                    pw.println();
                    pw.println();
                    pw.println();

                    pw.println("    public "+ClassName+" add"+f.getFiledName()+"(int index,"+getRepeatedType(f)+ " value){");
                    pw.println("        "+f.getFiledName()+".add(index,value);");
                    pw.println("        return this;");
                    pw.println("    }");
                    pw.println();
                    pw.println();
                    pw.println();

                    pw.println("    public "+ClassName+" remove"+f.getFiledName()+"(int index ){");
                    pw.println("        "+f.getFiledName()+".remove(index);");
                    pw.println("        return this;");
                    pw.println("    }");
                    pw.println();
                    pw.println();
                    pw.println();

                    pw.println("    public "+ClassName+" get"+f.getFiledName()+"(int index ){");
                    pw.println("        "+f.getFiledName()+".get(index);");
                    pw.println("        return this;");
                    pw.println("    }");
                    pw.println();
                    pw.println();
                    pw.println();

                }else {
                    pw.println("    public "+ClassName+" set"+f.getFiledName()+"("+getTypeValue(f)+" value ){");
                    pw.println("        this."+f.getFiledName()+" = value ;");
                    pw.println("        return this;");
                    pw.println("    }");
                    pw.println();
                    pw.println();
                    pw.println();
                }
            }

        }
        setOneOfFile(pw,OneOfSet,OneOfList,ClassName);
    }


    private void setOneOfFile(PrintWriter pw,Set<Integer> os,Map<Integer,List<Filed>> om,String className){
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
                pw.println();

            }
            pw.println();
            pw.println();
            pw.println();
        }
    }



    private void IsInitialized(List<Filed> filed, PrintWriter pw, String ClassName){//创建message类

        pw.println("    public "+ClassName+" IsInitialized(){");
        pw.println("        "+ClassName+" a=new "+ClassName+"();");
        for(Filed f:filed){
            Object o=map.get(f.getFileTypeName());
            if(o!=null) {//根据属性标签进行生成代码
                pw.println("        a."+f.getFiledName()+" = this."+f.getFiledName()+" ;");
            }else if(f.getFiledLabel().getLabel().equals(FiledLabel.Repeated.getLabel())){
                pw.println("        a."+f.getFiledName()+" = this."+f.getFiledName()+" ;");
            }else if(f.getFiledLabel().getLabel().equals(FiledLabel.Optional.getLabel())){
                if(f.getHasOneOf()) {
                    pw.println("        if(endSet"+f.getOneIndex()+"=="+f.getTag()+"){");
                    pw.println("            a." + f.getFiledName() + " = this." + f.getFiledName() + " ;");
                    pw.println("        }");
                }else {
                    pw.println("        a." + f.getFiledName() + " = this." + f.getFiledName() + " ;");
                }
            }else if(f.getFiledLabel().getLabel().equals(FiledLabel.Required.getLabel())){
                pw.println("        if("+f.getFiledName()+"==null){");
                pw.println("            throw new RuntimeException(\"this filed is require\");\n\n");
                pw.println("        }");
                pw.println("        a."+f.getFiledName()+" = this."+f.getFiledName()+" ;");
            }

        }
        pw.println("        return a;");
        pw.println("    }");
        pw.println();
        pw.println();
        pw.println();
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

    private String getRepeatedType(Filed filed){
        String type=filed.getFileType().getType();
        if(type.equals(FiledType.Bytes.getType())){

                return " "+"byte[]"+" ";

        }else if(type.equals(FiledType.Message.getType())||type.equals(FiledType.Enum.getType())){

                return " "+filed.getFileTypeName()+" ";

        }else {

                return " "+filed.getFileType().getJavaClass().getName()+" ";

        }
    }


}

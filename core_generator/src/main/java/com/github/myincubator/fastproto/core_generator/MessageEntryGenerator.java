package com.github.myincubator.fastproto.core_generator;



import com.github.myincubator.fastproto.wrapper.*;
import com.github.myincubator.fastproto.wrapper.Object;

import java.io.*;

import java.util.*;



public class MessageEntryGenerator {






    public static File generate(Object o, String out_dir, String pack, Map<String, Object> map, Map<String, Meta> metaMap) throws IOException {



        StringWriter sw=new StringWriter();
        PrintWriter pw=new PrintWriter(sw);

        String className=o.getName();

        if(pack!=null&&!pack.equals("")) {
            pw.format("package %s;\n",pack);
        }

        pw.format("import io.netty.buffer.ByteBuf;  import io.netty.buffer.ByteBufUtil;");
        pw.format("public final class %s {\n",o.getName());//类声明


        Map<Integer, List<Filed>> oneOf=new HashMap<>();//同一个OneOf结构存储到一个List中



        pw.println();
        pw.format("private int %s_size=0;\n",className);
        pw.println();


        o.getAllFiled().forEach(//除oneof以为的进行代码生成
                f-> filedGen(pw,f,map,metaMap,oneOf,className)
        );


        oneOf.keySet().forEach(//生成OneOf声明
                index-> OneOfMessageGenerator.Gen(oneOf.get(index),pw,metaMap,className)
        );
        pw.println();

        decode(pw,className,o.getAllFiled());

        pw.println();

        encode(pw,o.getAllFiled());

        pw.println();

        decode2(pw,className,o.getAllFiled());

        pw.println();

        getByteSize(pw,className);

        pw.println();

        build(pw,className+"Build");

        pw.println();
        verify(pw,o.getAllFiled());

        pw.println();


        BuildEntryGenerator.generate(pw,o,map,metaMap);

        pw.println();


        for (Object object : o.getObject()) {
            if (object.getObjectType().getType().equals(ObjectType.Message.getType())) {
                NestedMessageGenerator.generate(pw,object,map,metaMap);
            } else if (object.getObjectType().getType().equals(ObjectType.Enum.getType())) {
                NestedEnumGenerator.generate(pw,object);
            }
        }
        pw.println("}");




        File file=Util.genFile(out_dir,pack,className);

        Util.WriterContent(file,sw);
        return file;

    }



    private static void filedGen(PrintWriter pw, Filed filed, Map<String, Object> map, Map<String, Meta> metaMap, Map<Integer,List<Filed>> oneOf, String className){

        Object mapObject=map.get(filed.getFileTypeName());

        String label=filed.getFiledLabel().getLabel();
        if(filed.getHasOneOf()){                                //判断是否为Onof里的属性
            int oneOfIndex= filed.getOneIndex();
            List<Filed> filedList=oneOf.get(oneOfIndex);
            if(filedList==null){            //判断Map表中是否有对应的存储结构
                List<Filed> list=new ArrayList<>();
                list.add(filed);
                oneOf.put(oneOfIndex,list);
            }else {
                filedList.add(filed);
            }
        }else if(mapObject!=null){                             //判断是否是Map类型
            MapMessageGenerator.Gen(filed,pw,metaMap,mapObject,className);
        }else if(label.equals(FiledLabel.Repeated.getLabel())){//判断标签是否为可多次出现的
            RepeatedMessageGenerator.Gen(filed,pw,metaMap,className);
        }else {
            MessageGenerator.Gen(filed,pw,metaMap,className);//repeated不进行操作
        }

    }


    private static void getByteSize(PrintWriter pw,String className){
        pw.println("    public int getByteSize(){");
        pw.format("         return this.%s_size;\n",className);
        pw.println("    }");
    }

    private static void decode(PrintWriter pw, String className, List<Filed> filedList){
        pw.format("     public static %s decode(ByteBuf buf){\n",className);
        pw.format("         %s value_1=new %s();\n",className,className);
        pw.format("         int f_Index=buf.readerIndex();");
        pw.format("         while(buf.readerIndex()<buf.writerIndex()){\n");
        pw.format("             int num_1=Serializer.decodeVarInt32(buf);\n");
        pw.format("              switch(num_1){\n");
        for(Filed filed:filedList){
            String filedName=filed.getFiledName();
            pw.format("             case %s_Tag:\n", filedName);
            pw.format("                 decode_%s(buf,value_1);\n",filedName);
            pw.format("                 break;\n");
        }
        pw.format("default: Serializer.skipUnknownField(num_1,buf);");
        pw.format("              }\n");
        pw.format("         }\n");
        pw.format("         value_1.%s_size=buf.readerIndex()-f_Index;\n",className);
        pw.format("         return value_1;\n");
        pw.println("    }\n");
    }

    private static void decode2(PrintWriter pw, String className, List<Filed> filedList){
        pw.format("     public static %s decode(ByteBuf buf,int length_1){\n",className);
        pw.format("         %s value_1=new %s();\n",className,className);
        pw.format("         int f_Index=buf.readerIndex();\n");
        pw.format("         while(buf.readerIndex()<f_Index+length_1){\n");
        pw.format("             int num_1=Serializer.decodeVarInt32(buf);\n");
        pw.format("              switch(num_1){\n");
        Set<Integer> set=new HashSet<>();
        for(Filed filed:filedList){
            String filedName=filed.getFiledName();
            pw.format("             case %s_Tag:\n", filed.getFiledName());
            pw.format("                 decode_%s(buf,value_1);\n",filedName);
            pw.format("                 break;\n");
        }
        pw.format("default: Serializer.skipUnknownField(num_1,buf);");
        pw.format("              }\n");
        pw.format("         }\n");

        pw.format("         value_1.%s_size=length_1;\n",className);
        pw.format("         value_1.verify();\n");
        pw.format("         return value_1;\n");
        pw.println("    }\n");
    }

    private static void encode(PrintWriter pw,  List<Filed> filedList){
        pw.format("     public void encode(ByteBuf buf){\n");
        Set<Integer> set=new HashSet<>();
        for(Filed filed: filedList) {
            if(filed.getHasOneOf()){
                set.add(filed.getOneIndex());
                continue;
            }
            String filedName=filed.getFiledName();
            pw.format("         if(has_%s()){\n",filedName);
            pw.format("             this.encode_%s(buf);\n",filedName);
            pw.format("         }\n");
            pw.println();

        }
        for(int oneOf:set){
            pw.format("     if(hasOneOf%d()){",oneOf);
            pw.format("         encodeOneOf_%d(buf);",oneOf);
            pw.format("     }");
        }
        pw.format("     }");
    }


    private static void build(PrintWriter pw,String buildName){
        pw.format("public static %s newBuilder(){\n",buildName);
        pw.format("return new %s();\n",buildName);
        pw.format("}\n");
    }

    private static void verify(PrintWriter pw,List<Filed> filed){
        pw.format("     private void verify(){\n");
        for(Filed filed1:filed) {
            if(filed1.getFiledLabel().getLabel().equals(FiledLabel.Required.getLabel())){
                pw.format("         if(this.%s==null){\n",filed1.getFiledName());
                pw.format("             throw new RuntimeException(\"required %s\");\n",filed1.getFiledName());
                pw.format("         }\n");
            }
        }
        pw.format("     }\n");
    }






}
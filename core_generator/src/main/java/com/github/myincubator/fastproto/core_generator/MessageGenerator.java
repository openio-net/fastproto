package com.github.myincubator.fastproto.core_generator;

import com.github.myincubator.fastproto.wrapper.Filed;
import com.github.myincubator.fastproto.wrapper.Meta;

import java.io.PrintWriter;
import java.util.Map;

public class MessageGenerator {

    public static void Gen(Filed filed, PrintWriter pw, Map<String, Meta> metaMap, String className) {
        pw.println();
        pw.format("     private %s %s;", Util.getJavaType(filed,metaMap),filed.getFiledName());
        pw.format("    public final static int %s_Num = %d;\n",filed.getFiledName(),filed.getNum());

        pw.format("    public final static int %s_Tag=%d;//the value is num<<<3|wireType\n",filed.getFiledName(), Util.getTag(filed));

        pw.format("    public final static int %s_TagEncodeSize=%d;\n",filed.getFiledName(), Util.getTagEncodeSize(filed));
        pw.println();
        pw.println();
        encode(pw,filed);
        pw.println();
        decode(pw,metaMap,filed,className);
        pw.println();
        set(pw,metaMap,filed,className);
        pw.println();
        get(pw,metaMap,filed);
        pw.println();
        has(pw,filed);
        pw.println();

    }



    private static void encode(PrintWriter pw,Filed value){
        String fileName=value.getFiledName();
        pw.format("     private void encode_%s(ByteBuf buf){\n",fileName);
        pw.format("             Serializer.encodeVarInt32(buf,%s_Tag);\n", value.getFiledName());
        Util.encodeFiled(pw,value,"buf","this."+fileName);
        pw.format("     }\n");
    }


    private static void decode(PrintWriter pw,  Map<String, Meta> metaMap,Filed value,String className){
        String fileName=value.getFiledName();
        pw.format("     private static void decode_%s(ByteBuf buf,%s a_1){\n",fileName,className);
        pw.format("         %s value_1=null;\n", Util.getJavaType(value,metaMap));
        Util.filedDecode(value,pw,"buf","value_1\n",metaMap);
        pw.format("          a_1.%s=value_1;\n",fileName);
        pw.format("     }\n");
    }

    private static void set(PrintWriter pw,  Map<String, Meta> metaMap,Filed value,String className){
        String fileName=value.getFiledName();
        pw.format("     private  void set_%s(%s value_1){\n",fileName, Util.getJavaType(value,metaMap));
        pw.format("         %s_size+=%s_TagEncodeSize;",className,fileName);
        pw.println("        int size_1=0;\n");
        Util.size(pw,value.getFileType().getType(),"size_1","value_1");
        pw.format("          %s_size+=size_1;\n",className);
        pw.format("          this.%s=value_1;\n",fileName);
        pw.format("     }\n");
    }

    private static void get(PrintWriter pw,  Map<String, Meta> metaMap,Filed value){
        String fileName=value.getFiledName();
        pw.format("     public  %s get_%s(){\n", Util.getJavaType(value,metaMap),fileName);
        pw.format("        return this.%s;\n",fileName);
        pw.println("    }");
    }
    private static void has(PrintWriter pw,Filed value){
        String fileName=value.getFiledName();
        pw.format("     private  boolean has_%s(){\n",fileName);
        pw.format("        return this.%s!=null;\n",fileName);
        pw.println("    }");
    }
}

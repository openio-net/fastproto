package com.github.openio.fastproto.core_generator;

import com.github.openio.fastproto.wrapper.Filed;
import com.github.openio.fastproto.wrapper.Meta;
import org.apache.commons.text.CaseUtils;

import java.io.PrintWriter;
import java.util.Map;

public class MessageGenerator {

    Filed filed;

    String className;

    public MessageGenerator(Filed filed, String className) {
        this.filed = filed;
        this.className = className;
    }

    public void generate(PrintWriter pw, Map<String, Meta> metaMap) {
        pw.println();
        pw.format("     private %s %s;", Util.getJavaType(filed, metaMap), filed.getFiledName());
        pw.format("    public final static int %s_Num = %d;\n", filed.getFiledName(), filed.getNum());

        pw.format("    public final static int %s_Tag=%d;//the value is num<<<3|wireType\n", filed.getFiledName(), Util.getTag(filed));

        pw.format("    public final static int %s_TagEncodeSize=%d;\n", filed.getFiledName(), Util.getTagEncodeSize(filed));
        pw.println();
        pw.println();
        generateEncode(pw, filed);
        pw.println();
        generateDecode(pw, metaMap, filed, className);
        pw.println();
        generateSet(pw, metaMap, filed, className);
        pw.println();
        generateGet(pw, metaMap, filed);
        pw.println();
        generateHas(pw, filed);
        pw.println();

    }


    private void generateEncode(PrintWriter pw, Filed value) {
        String fileName = value.getFiledName();
        pw.format("     private void encode_%s(ByteBuf buf){\n", fileName);
        pw.format("             Serializer.encodeVarInt32(buf,%s_Tag);\n", value.getFiledName());
        Util.encodeFiled(pw, value, "buf", "this." + fileName);
        pw.format("     }\n");
    }


    private void generateDecode(PrintWriter pw, Map<String, Meta> metaMap, Filed value, String className) {
        String fileName = value.getFiledName();
        pw.format("     private static void decode_%s(ByteBuf buf,%s a_1){\n", fileName, className);
        pw.format("         %s value_1=null;\n", Util.getJavaType(value, metaMap));
        Util.filedDecode(value, pw, "buf", "value_1\n", metaMap);
        pw.format("          a_1.%s=value_1;\n", fileName);
        pw.format("     }\n");
    }

    private void generateSet(PrintWriter pw, Map<String, Meta> metaMap, Filed value, String className) {
        String fileName = value.getFiledName();
        pw.format("     private  void set_%s(%s value_1){\n", fileName, Util.getJavaType(value, metaMap));
        pw.format("         %s_size+=%s_TagEncodeSize;", className, fileName);
        pw.println("        int size_1=0;\n");
        Util.size(pw, value.getFileType().getType(), "size_1", "value_1");
        pw.format("          %s_size+=size_1;\n", className);
        pw.format("          this.%s=value_1;\n", fileName);
        pw.format("     }\n");
    }

    private void generateGet(PrintWriter pw, Map<String, Meta> metaMap, Filed value) {
        String fileName = value.getFiledName();
        pw.format("     public  %s get%s(){\n", Util.getJavaType(value, metaMap), CaseUtils.toCamelCase(fileName, true));
        pw.format("        return this.%s;\n", fileName);
        pw.println("    }");
    }

    private void generateHas(PrintWriter pw, Filed value) {
        String fileName = value.getFiledName();
        pw.format("     private  boolean has%s(){\n", CaseUtils.toCamelCase(fileName, true));
        pw.format("        return this.%s!=null;\n", fileName);
        pw.println("    }");
    }
}

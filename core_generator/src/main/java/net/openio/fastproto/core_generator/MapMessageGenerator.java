package net.openio.fastproto.core_generator;

import net.openio.fastproto.wrapper.Filed;
import net.openio.fastproto.wrapper.Message;
import net.openio.fastproto.wrapper.Meta;
import org.apache.commons.text.CaseUtils;

import java.io.PrintWriter;
import java.util.Map;

public class MapMessageGenerator {


    private final Filed filed;

    private final Message map;

    private final String className;

    public MapMessageGenerator(Filed filed, Message map, String className) {
        this.filed = filed;
        this.map = map;
        this.className = className;
    }

    private static void generateSet(PrintWriter pw, Filed key, Filed value, Filed filed, String className, Map<String, Meta> metaMap) {
        String keyType = Util.getJavaType(key, metaMap);
        String valueType = Util.getJavaType(value, metaMap);
        String fileName = filed.getFiledName();
        pw.format("     private void set_%s(java.util.Map<%s,%s> map){", fileName, keyType, valueType);
        pw.format("         if(map==null) return;\n");
        pw.format("         %s_Length=new java.util.HashMap<>(map.keySet().size());", fileName);
        pw.format("         this.%s=new java.util.HashMap<>(map.keySet().size());\n", fileName);

        pw.format("int key_Length1=Serializer.computeVarInt32Size(%d);//the number is key tag\n", Util.getTag(key));
        pw.format("int value_Length1=Serializer.computeVarInt32Size(%d);//the number is value tag\n", Util.getTag(value));

        pw.format("         for(%s s_1:map.keySet()){\n",keyType);
        pw.format("             int key_value_length=0;");
        pw.format("             key_value_length+= key_Length1;//add the map value tag length\n");
        //add key encode length
        pw.format("             //sum the key length\n");
        Util.size(pw,key.getFileType().getType(),"key_value_length","s_1");


        //add value encode length
        pw.format("             %s value_1=map.get(s_1);\n",valueType);
        pw.format("             key_value_length+=value_Length1;//add the map value tag length\n");
        pw.format("             //sum the value length\n");
        Util.size(pw,value.getFileType().getType(),"key_value_length","value_1");

        pw.format("             %s_Length.put(s_1,key_value_length)\n;",fileName);
        pw.format("             %s_size+=key_value_length;\n",className);
        pw.format("             this.%s.put(s_1,value_1);",fileName);
        pw.format("             %s_size+=Serializer.computeVarInt64Size(key_value_length);",className);
        pw.format("         }\n");
        pw.format("         %s_size+=map.keySet().size()*%s_TagEncodeSize;\n",className, filed.getFiledName());

        pw.format("     }\n");
    }

    private static void generateSet(PrintWriter pw, String fileName, Map<String, Meta> metaMap, Filed key, Filed value) {
        pw.format("     public %s get%s(%s key){\n", Util.getJavaType(value, metaMap), CaseUtils.toCamelCase(fileName, true), Util.getJavaType(key, metaMap));
        pw.format("         if(this.%s==null){\n", fileName);
        pw.format("             return null;");
        pw.println("        }\n");
        pw.format("         return this.%s.get(key);\n", fileName);
        pw.println("    }");
    }

    private static void generateGetKeySet(PrintWriter pw, String fileName, Map<String, Meta> metaMap, Filed key) {
        pw.format("     public java.util.Set<%s> get%sKeySet(){\n", Util.getJavaType(key, metaMap), CaseUtils.toCamelCase(fileName, true));
        pw.format("         if(this.%s==null){\n", fileName);
        pw.format("             return null;");
        pw.println("        }\n");
        pw.format("         return this.%s.keySet();\n", fileName);
        pw.println("    }");
    }

    private static void generatePut(PrintWriter pw, String fileName, Map<String, Meta> metaMap, Filed key, Filed value) {
        pw.format("     private void put_%s(%s key,%s value,int length){\n", fileName, Util.getJavaType(key, metaMap), Util.getJavaType(value, metaMap));

        pw.format("         if(this.%s==null){\n", fileName);
        pw.format("             this.%s=new java.util.HashMap<>();\n", fileName);
        pw.format("             this.%s_Length=new java.util.HashMap<>();", fileName);
        pw.println("        }\n");
        pw.format("         this.%s_Length.put(key,length);", fileName);
        pw.format("         this.%s.put(key,value);\n", fileName);
        pw.println("    }");
    }

    private static void generateDecode(PrintWriter pw, String fileName, Map<String, Meta> metaMap, Filed key, Filed value, String className) {

        pw.format("     private static void decode_%s(ByteBuf buf,%s a_1){\n", fileName, className);
        pw.format("         int length_1=Serializer.decodeVarInt32(buf);\n");
        pw.format("         int end_Index=buf.readerIndex()+length_1;\n");
        pw.format("         %s key=null;\n", Util.getJavaType(key, metaMap));
        pw.format("         %s value=null;\n", Util.getJavaType(value, metaMap));
        pw.format("         int tag=Serializer.decodeVarInt32(buf);\n");
        pw.format("         if(tag==%s){\n", Util.getTag(key));
        Util.filedDecode(key, pw, "buf", "key", metaMap);
        pw.format("         }else if(tag==%s){\n", Util.getTag(value));
        Util.filedDecode(value,pw,"buf","value",metaMap);
        pw.format("         }\n");
        pw.format("         tag=Serializer.decodeVarInt32(buf);\n");
        pw.format("         if(tag==%s){\n", Util.getTag(key));
        Util.filedDecode(value,pw,"buf","value",metaMap);
        pw.format("         }else if(tag==%s){\n", Util.getTag(value));
        Util.filedDecode(value,pw,"buf","value",metaMap);
        pw.format("         }\n");
        pw.format("        if(key==null||value==null){\n");
        pw.format("            throw new RuntimeException(\" %s decode is wrong\");\n",fileName);
        pw.format("        }");
        pw.format("        a_1.put_%s(key,value,length_1);\n",fileName);
        pw.format("     }\n");
    }

    private static void generateEncode(PrintWriter pw, Filed key, Filed value, String fileName, Map<String, Meta> metaMap) {
        pw.format("private void encode_%s(ByteBuf buf){\n", fileName);
        pw.format("     for(%s key_1 : %s.keySet()){", Util.getJavaType(key, metaMap), fileName);
        pw.format("         Serializer.encodeVarInt32(buf,%s_Tag);\n", fileName);
        pw.format("         int length_1=%s_Length.get(key_1);\n", fileName);
        pw.format("         Serializer.encodeVarInt32(buf,length_1);\n");
        pw.format("         Serializer.encodeVarInt32(buf,%d);\n", Util.getTag(key));
        Util.encodeFiled(pw, key, "buf", "key_1");
        pw.format("         Serializer.encodeVarInt32(buf,%d);\n", Util.getTag(value));
        pw.format("         %s value_1=%s.get(key_1);\n", Util.getJavaType(value, metaMap), fileName);
        Util.encodeFiled(pw, value, "buf", "value_1");
        pw.format("     }\n");
        pw.format("}\n");
    }

    private static void generateHas(PrintWriter pw, String fileName) {
        pw.format("     public boolean has%s(){\n", CaseUtils.toCamelCase(fileName, true));
        pw.format("         if(this.%s==null){\n", fileName);
        pw.format("             return false;\n");
        pw.format("         }");
        pw.format("         return this.%s.keySet().size()!=0;\n", fileName);
        pw.println("     }");
    }

    public void Generate(PrintWriter pw, Map<String, Meta> metaMap) {

        Filed key = null;
        Filed value = null;
        for (Filed filed1 : map.getAllFiled()) {

            if (filed1.getFiledName().equals("key")) {
                key = filed1;
            } else {
                value = filed1;
            }
        }
        String keyType = Util.getJavaType(key, metaMap);

        String valueType = Util.getJavaType(value, metaMap);
        String fileName = filed.getFiledName();

        pw.format("     private  java.util.Map<%s,%s> %s;\n", keyType, valueType, fileName);
        pw.format("     private java.util.Map<%s,java.lang.Integer> %s_Length;\n", keyType, fileName);
        pw.format("    public final static int %s_Num = %d;\n", filed.getFiledName(), filed.getNum());
        pw.format("    public final static int %s_Tag=%d;\n", filed.getFiledName(), Util.getTag(filed));
        pw.format("    public final static int %s_TagEncodeSize=%d;\n", filed.getFiledName(), Util.getTagEncodeSize(filed));

        pw.println();
        pw.println();
        generateSet(pw, key, value, filed, className, metaMap);
        pw.println();

        generateSet(pw, fileName, metaMap, key, value);

        pw.println();

        generateGetKeySet(pw, fileName, metaMap, key);
        pw.println();
        generateHas(pw, fileName);
        pw.println();
        generateEncode(pw, key, value, fileName, metaMap);
        pw.println();
        generateDecode(pw, fileName, metaMap, key, value, className);
        pw.println();
        generatePut(pw, fileName, metaMap, key, value);
        pw.println();

    }


}

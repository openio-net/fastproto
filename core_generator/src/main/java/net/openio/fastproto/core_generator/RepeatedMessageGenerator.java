/*
 * Licensed to the OpenIO.Net under one or more
 * contributor license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.openio.fastproto.core_generator;

import net.openio.fastproto.wrapper.Filed;
import net.openio.fastproto.wrapper.Meta;
import net.openio.fastproto.wrapper.Option;
import org.apache.commons.text.CaseUtils;

import java.io.PrintWriter;
import java.util.Map;

public class RepeatedMessageGenerator {

    private final Filed filed;

    String className;

    public RepeatedMessageGenerator(Filed filed, String className) {
        this.filed = filed;
        this.className = className;
    }

    public void Gen(PrintWriter pw, Map<String, Meta> metaMap) {
        Boolean packed = false;
        for (Option option : filed.getAllOption()) {
            if (option.getKey().equals("packed")) {
                packed = (Boolean) option.getValue();
            }
        }
        pw.println();
        pw.println();

        pw.format("        private java.util.List<%s> %s;\n", Util.getJavaType(filed, metaMap), filed.getFiledName());

        pw.format("    public final static int %s_Num = %d;\n",filed.getFiledName(),filed.getNum());

        pw.format("    public final static int %s_Tag=%d;//the value is num<<3|wireType\n",filed.getFiledName(), Util.getTag(filed));

        pw.format("    public final static int %s_TagEncodeSize=%d;\n",filed.getFiledName(), Util.getTagEncodeSize(filed));

        if(packed) {
            pw.format("        private int %s_Length=0;\n", filed.getFiledName());
        }
        pw.println();
        pw.println();
        set(pw,filed,className,metaMap,packed);
        pw.println();
        get(pw,metaMap,filed);
        pw.println();
        getSize(pw,filed);
        pw.println();
        decode(pw,metaMap,filed,className,packed);
        pw.println();
        encode(pw,filed,metaMap,packed);
        pw.println();
        add(pw,metaMap,filed);
        pw.println();
        has(pw,filed.getFiledName());
        pw.println();



    }

    private void set(PrintWriter pw, Filed filed, String ClassName, Map<String, Meta> metaMap, boolean packed) {
        pw.format("     private void set_%s(java.util.List<%s> list_1){\n", filed.getFiledName(), Util.getJavaType(filed, metaMap));
        pw.format("         this.%s=new java.util.ArrayList<>(list_1.size());", filed.getFiledName());
        if (packed) {
            packedSet(pw, filed, ClassName, metaMap);
        } else {
            set(pw, filed, ClassName, metaMap);
        }
        pw.format("     }");
    }


    private void packedSet(PrintWriter pw, Filed filed, String ClassName, Map<String, Meta> metaMap) {
        String fileName = filed.getFiledName();
        pw.format("         this.%s_size+=%s_TagEncodeSize;//add tag length\n", ClassName, fileName);
        pw.format("          int length_1=0;\n");
        pw.format("          for(%s value_1: list_1){\n", Util.getJavaType(filed, metaMap));
        Util.size(pw, filed.getFileType().getType(), "length_1", "value_1");
        pw.format("             this.%s.add(value_1);", fileName);
        pw.format("          }\n");
        pw.format("         this.%s_size+=Serializer.computeVarInt32Size(length_1);//add length byte size\n", ClassName);//添加length的byte长度
        pw.format("         this.%s_size+=length_1;\n", ClassName);
        pw.format("         this.%s_Length=length_1;\n", fileName);

    }


    private void set(PrintWriter pw, Filed filed, String ClassName, Map<String, Meta> metaMap) {
        String fileName = filed.getFiledName();
        pw.format("         this.%s_size+=%s_TagEncodeSize*list_1.size();//add tag length\n", ClassName, fileName);
        pw.format("          for(%s value_1: list_1){\n", Util.getJavaType(filed, metaMap));
        pw.format("             this.%s.add(value_1);", fileName);
        pw.format("             int length_1=0;\n");
        Util.size(pw, filed.getFileType().getType(), "length_1", "value_1");
        pw.format("             this.%s_size+=length_1;\n", ClassName);
        pw.format("          }\n");

    }


    private void get(PrintWriter pw, Map<String, Meta> metaMap, Filed value) {
        String fileName = value.getFiledName();
        pw.format("     public %s get%s(int index){\n", Util.getJavaType(value, metaMap), CaseUtils.toCamelCase(fileName, true));
        pw.format("         if(this.%s==null){\n", fileName);
        pw.format("             return null;");
        pw.println("        }\n");
        pw.format("         return this.%s.get(index);\n", fileName);
        pw.println("    }");
    }


    private void getSize(PrintWriter pw, Filed value) {
        String fileName = value.getFiledName();
        pw.format("     public int get%sSize(){\n", CaseUtils.toCamelCase(fileName, true));
        pw.format("         if(this.%s==null){\n", fileName);
        pw.format("             return 0;");
        pw.println("        }\n");
        pw.format("         return this.%s.size();\n", fileName);
        pw.println("    }");
    }


    private void add(PrintWriter pw, Map<String, Meta> metaMap, Filed value) {
        String fileName = value.getFiledName();
        pw.format("     private void add_%s(%s value){\n", fileName, Util.getJavaType(value, metaMap));
        pw.format("         if(this.%s==null){\n", fileName);
        pw.format("             this.%s=new java.util.ArrayList<>();\n", fileName);
        pw.println("        }\n");
        pw.format("         this.%s.add(value);\n", fileName);
        pw.println("    }");
    }


    private void decode(PrintWriter pw, Map<String, Meta> metaMap, Filed value, String className, boolean packed) {
        String fileName = value.getFiledName();
        pw.format("     private static void decode_%s(ByteBuf buf,%s a_1){\n", fileName, className);
        if (packed) {
            packedDecode(pw, metaMap, value);
        } else {
            decode(pw, metaMap, value);
        }
        pw.format("     }\n");
    }


    private void packedDecode(PrintWriter pw, Map<String, Meta> metaMap, Filed value) {
        String fileName = value.getFiledName();
        pw.format("         if(a_1.%s_Length!=0){// has init\n ", fileName);
        pw.format("             a_1.%s=new java.util.ArrayList<>();\n", fileName);
        pw.format("         }\n");
        pw.format("         a_1.%s_Length=Serializer.decodeVarInt32(buf);\n", fileName);
        pw.format("         int end_index=buf.readerIndex()+a_1.%s_Length;\n", fileName);
        pw.format("         while(buf.readerIndex()<end_index){\n");
        pw.format("             %s value_1=null;\n", Util.getJavaType(value, metaMap));
        Util.filedDecode(value, pw, "buf", "value_1\n", metaMap);
        pw.format("             a_1.add_%s(value_1);\n", fileName);
        pw.format("         }\n");



    }


    private void decode(PrintWriter pw, Map<String, Meta> metaMap, Filed value) {
        String fileName = value.getFiledName();
        pw.format("         %s value_1=null;\n", Util.getJavaType(value, metaMap));
        Util.filedDecode(value, pw, "buf", "value_1", metaMap);
        pw.format("             a_1.add_%s(value_1);\n", fileName);
    }


    private void encode(PrintWriter pw, Filed value, Map<String, Meta> metaMap, boolean packed) {
        String fileName = value.getFiledName();
        pw.format("     private void encode_%s(ByteBuf buf){\n", fileName);
        if (packed) {
            packedEncode(pw, value, metaMap);
        } else {
            encode(pw, value, metaMap);
        }
        pw.format("     }\n");
    }

    private void packedEncode(PrintWriter pw, Filed value, Map<String, Meta> metaMap) {
        String fileName = value.getFiledName();
        pw.format("         Serializer.encodeVarInt32(buf,%s_Tag);\n", value.getFiledName());
        pw.format("         Serializer.encodeVarInt32(buf,%s_Length);\n", fileName);
        pw.format("         for(%s value_1: %s){\n", Util.getJavaType(value, metaMap), fileName);
        Util.encodeFiled(pw, value, "buf", "value_1\n");
        pw.format("         }");
    }

    private void encode(PrintWriter pw, Filed value, Map<String, Meta> metaMap) {
        String fileName = value.getFiledName();
        pw.format("         for(%s value_1: %s){\n", Util.getJavaType(value, metaMap), fileName);
        pw.format("         Serializer.encodeVarInt32(buf,%s_Tag);\n", value.getFiledName());
        Util.encodeFiled(pw, value, "buf", "value_1");
        pw.format("         }\n");

    }

    private void has(PrintWriter pw, String filedName) {
        pw.format("     public boolean has%s(){\n", CaseUtils.toCamelCase(filedName, true));
        pw.format("         if(this.%s==null){\n", filedName);
        pw.format("             return false;\n");
        pw.format("         }\n");
        pw.format("         return this.%s.size()!=0;\n", filedName);
        pw.println("    }");
    }


}

/**
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
package net.openio.fastproto.core.generator;

import net.openio.fastproto.wrapper.Filed;
import net.openio.fastproto.wrapper.Meta;

import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

/**
 * The OneOfMessageGenerator class is responsible for
 * generating code for the fields and methods related to a oneof group in the message class.
 */
public class OneOfMessageGenerator {
    List<Filed> filed1;

    String className;

    public OneOfMessageGenerator(List<Filed> filed, String name) {
        this.filed1 = filed;
        this.className = name;
    }

    public void generate(PrintWriter pw, Map<String, Meta> metaMap) {
        int oneOfIndex = filed1.get(0).getOneIndex();
        pw.format("private int endOneOfNum_%d=-1;\n", oneOfIndex);
        pw.println();
        for (Filed filed : filed1) {
            pw.format("private %s %s;\n", Util.getJavaType(filed, metaMap), filed.getFiledName());
            pw.format("    public final static int %s_Num = %d;\n", filed.getFiledName(), filed.getNum());
            pw.format("    public final static int %s_Tag=%d;\n", filed.getFiledName(), Util.getTag(filed));
            pw.format("    public final static int %s_TagEncodeSize=%d;\n", filed.getFiledName(), Util.getTagEncodeSize(filed));
            pw.println();
        }

        generateSet(filed1, pw, metaMap, oneOfIndex, className);
        pw.println();
        generateGet(pw, filed1);
        pw.println();
        generateEncode(pw, filed1);
        pw.println();
        generateDecode(pw, filed1, metaMap, className);
        pw.println();
        generateHas(pw, filed1);
        pw.println();
    }

    private void generateSet(List<Filed> filed1, PrintWriter pw, Map<String, Meta> metaMap, int oneOf, String className) {
        for (Filed filed : filed1) {
            String filedName = filed.getFiledName();
            String javaClass = Util.getJavaType(filed, metaMap);
            pw.format("     private void setOneOf%d_%s(%s value_1){\n", oneOf, filedName, javaClass);
            pw.format("         this.%s=value_1;\n", filedName);
//            pw.format("         if(endOneOfNum_%d!=-1){\n",oneOf);
//            pw.format("             switch(endOneOfNum_%d){\n",oneOf);
//            int i=2;
//            for(Filed filed2:filed1){
//                pw.format("             case %s_Num :\n",filed2.getFiledName());
//                pw.format("                 %s_size-=%s_TagEncodeSize;\n",className, filed2.getFiledName());
//                pw.format("                 %s value_%d=this.%s;\n", Util.getJavaType(filed2,metaMap),i,filed2.getFiledName());
//                pw.format("                 int length_%d=0;\n",i);
//                Util.size(pw,filed2.getFileType().getType(),"length_"+i,"value_"+i);
//                pw.format("                 %s_size-=length_%d;\n",className,i);
//                pw.format("                 this.%s=null;\n",filed2.getFiledName());
//                pw.format("                 break;\n");
//                i++;
//
//            }
//            pw.format("             }\n");
            pw.format("         %s_size+=%s_TagEncodeSize;\n", className, filed.getFiledName());
            pw.format("         endOneOfNum_%d=%s_Num;\n", oneOf, filed.getFiledName());
            Util.size(pw, filed.getFileType().getType(), className + "_size", "value_1");
//            pw.format("         }\n");
            pw.format("     }\n");
        }

    }

    private void generateGet(PrintWriter pw, List<Filed> filed1) {
        int oneOfIndex = filed1.get(0).getOneIndex();

        pw.format("    public java.lang.Object getOneOf%dValue(){\n", oneOfIndex);
        pw.format("         if(endOneOfNum_%d==-1) return null;\n", oneOfIndex);
        pw.format("         switch(endOneOfNum_%d){\n", oneOfIndex);
        for (Filed filed : filed1) {
            pw.format("             case %s_Num :\n", filed.getFiledName());
            pw.format("            return this.%s;\n", filed.getFiledName());
        }
        pw.println("        }");
        pw.println("return null;");
        pw.println("    }");

    }

    private void generateEncode(PrintWriter pw, List<Filed> filed1) {
        pw.println();
        int oneOf = filed1.get(0).getOneIndex();

        pw.println();

        pw.format("     private void encodeOneOf_%d(ByteBuf buf){\n", oneOf);
        pw.format("         switch(endOneOfNum_%d){", oneOf);
        for (Filed filed : filed1) {

            String filedName = filed.getFiledName();
            pw.format("             case %s_Num :\n", filed.getFiledName());
            pw.format("         Serializer.encodeVarInt32(buf,%s_Tag);\n", filed.getFiledName());
            Util.encodeFiled(pw, filed, "buf", "this." + filedName);
            pw.format("         break;");
        }
        pw.println("        }");
        pw.format("     }\n");
        pw.println();

    }

    private void generateDecode(PrintWriter pw, List<Filed> filed1, Map<String, Meta> metaMap, String className) {
        int oneOf = filed1.get(0).getOneIndex();
        pw.println();
        for (Filed filed : filed1) {
            pw.println();
            String filedName = filed.getFiledName();
            pw.format("     private static void decode_%s(ByteBuf buf,%s value_1){\n", filedName, className);
            pw.format("         if(value_1.endOneOfNum_%d!=-1){\n", oneOf);
            pw.format("             switch(value_1.endOneOfNum_%d){\n", oneOf);
            for (Filed filed2 : filed1) {
                pw.format("             case %s_Tag :\n", filed2.getFiledName());
                pw.format("                 value_1.%s=null;\n", filed2.getFiledName());
                pw.format("                 break;\n");

            }
            pw.format("             }\n");
            pw.format("     }\n");
            pw.format("             value_1.endOneOfNum_%d=%s_Num;", oneOf, filed.getFiledName());
            pw.format("             %s value_2=null;", Util.getJavaType(filed, metaMap));
            Util.filedDecode(filed, pw, "buf", "value_2", metaMap);
            pw.format("             value_1.%s=value_2;\n", filedName);

            pw.println();
            pw.println("}");
        }


    }

    private void generateHas(PrintWriter pw, List<Filed> list) {
        int oneOfIndex = list.get(0).getOneIndex();
        pw.format("     public boolean hasOneOf%d(){\n", oneOfIndex);
        pw.format("         return endOneOfNum_%d!=-1;\n", oneOfIndex);
        pw.println("     }");
    }




}

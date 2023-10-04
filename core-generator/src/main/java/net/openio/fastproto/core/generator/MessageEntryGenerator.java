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
import net.openio.fastproto.wrapper.FiledLabel;
import net.openio.fastproto.wrapper.Message;
import net.openio.fastproto.wrapper.Meta;
import net.openio.fastproto.wrapper.ObjectType;
import org.apache.commons.text.CaseUtils;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * The MessageEntryGenerator class is responsible for generating code
 * for a message entry based on the provided message and package information.
 */
public class MessageEntryGenerator {


    private final Message message;

    private final String outDir;

    private final String packages;


    public MessageEntryGenerator(Message message, String outDir, String aPackage) {
        this.message = message;
        this.outDir = outDir;
        packages = aPackage;
    }

    /**
     * @param map     Store map information
     * @param metaMap Meta information of entity class
     */
    public File generate(Map<String, Message> map, Map<String, Meta> metaMap) throws IOException {

        Message o = message;
        String outDir = this.outDir;
        String pack = packages;


        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);

        String className = o.getName();

        if (pack != null && !pack.equals("")) {
            pw.format("package %s;\n", pack);
        }

        pw.format("import io.netty.buffer.ByteBuf;  import io.netty.buffer.ByteBufUtil;");
        pw.format("public final class %s {\n", o.getName()); ///Class declaration


        Map<Integer, List<Filed>> oneOf = new HashMap<>(); //The same oneof structure is stored in a list


        pw.println();
        pw.format("private int %s_size=0;\n", className);
        pw.println();


        o.getAllFiled().forEach(//Except oneof, code generation is performed for
                f -> filedGenerate(pw, f, map, metaMap, oneOf, className)
        );


        oneOf.keySet().forEach(//Generate oneof declaration
                index -> new OneOfMessageGenerator(oneOf.get(index), className).generate(pw, metaMap)
        );
        pw.println();

        generateDecode(pw, className, o.getAllFiled());

        pw.println();

        generateEncode(pw, o.getAllFiled());

        pw.println();

        generateDecode2(pw, className, o.getAllFiled());

        pw.println();

        generateGetByteSize(pw, className);

        pw.println();

        generateBuild(pw, className + "Build");

        pw.println();
        generateVerify(pw, o.getAllFiled());

        pw.println();


        new BuildEntryGenerator(o).generate(pw, map, metaMap);

        pw.println();


        for (Message message : o.getObject()) {
            if (message.getObjectType().getType().equals(ObjectType.Message.getType())) {

                new NestedMessageGenerator(message).generate(pw, map, metaMap);

            } else if (message.getObjectType().getType().equals(ObjectType.Enum.getType())) {

                new NestedEnumGenerator(message).generate(pw);
            }
        }
        pw.println("}");


        File file = Util.genFile(outDir, pack, className);

        Util.writerContent(file, sw);
        return file;

    }


    private void filedGenerate(PrintWriter pw, Filed filed, Map<String, Message> map,
                               Map<String, Meta> metaMap, Map<Integer, List<Filed>> oneOf,
                               String className) {

        Message mapMessage = map.get(filed.getFileTypeName());

        String label = filed.getFiledLabel().getLabel();
        if (filed.getHasOneOf()) {                                //Determine whether it is an attribute in onof
            int oneOfIndex = filed.getOneIndex();
            List<Filed> filedList = oneOf.get(oneOfIndex);
            if (filedList == null) {           //Determine whether there is a corresponding storage structure in the map table
                List<Filed> list = new ArrayList<>();
                list.add(filed);
                oneOf.put(oneOfIndex, list);
            } else {
                filedList.add(filed);
            }
        } else if (mapMessage != null) {                             //Determine whether it is a map type
            new MapMessageGenerator(filed, mapMessage, className).generate(pw, metaMap);
        } else if (label.equals(FiledLabel.Repeated.getLabel())) { //Determine whether the label can appear multiple times
            new RepeatedMessageGenerator(filed, className).gen(pw, metaMap);
        } else {
            new MessageGenerator(filed, className).generate(pw, metaMap); //Repeated: no operation
        }

    }


    private void generateGetByteSize(PrintWriter pw, String className) {
        pw.println("    public int getByteSize(){");
        pw.format("         return this.%s_size;\n", className);
        pw.println("    }");
    }

    private void generateDecode(PrintWriter pw, String className, List<Filed> filedList) {
        pw.format("     public static %s decode(ByteBuf buf){\n", className);
        pw.format("         %s value_1=new %s();\n", className, className);
        pw.format("         int f_Index=buf.readerIndex();");
        pw.format("         while(buf.readerIndex()<buf.writerIndex()){\n");
        pw.format("             int num_1=Serializer.decodeVarInt32(buf);\n");
        pw.format("              switch(num_1){\n");
        for (Filed filed : filedList) {
            String filedName = filed.getFiledName();
            pw.format("             case %s_Tag:\n", filedName);
            pw.format("                 decode_%s(buf,value_1);\n", filedName);
            pw.format("                 break;\n");
        }
        pw.format("default: Serializer.skipUnknownField(num_1,buf);");
        pw.format("              }\n");
        pw.format("         }\n");
        pw.format("         value_1.%s_size=buf.readerIndex()-f_Index;\n", className);
        pw.format("         return value_1;\n");
        pw.println("    }\n");
    }

    private void generateDecode2(PrintWriter pw, String className, List<Filed> filedList) {
        pw.format("     public static %s decode(ByteBuf buf,int length_1){\n", className);
        pw.format("         %s value_1=new %s();\n", className, className);
        pw.format("         int f_Index=buf.readerIndex();\n");
        pw.format("         while(buf.readerIndex()<f_Index+length_1){\n");
        pw.format("             int num_1=Serializer.decodeVarInt32(buf);\n");
        pw.format("              switch(num_1){\n");
        Set<Integer> set = new HashSet<>();
        for (Filed filed : filedList) {
            String filedName = filed.getFiledName();
            pw.format("             case %s_Tag:\n", filed.getFiledName());
            pw.format("                 decode_%s(buf,value_1);\n", filedName);
            pw.format("                 break;\n");
        }
        pw.format("default: Serializer.skipUnknownField(num_1,buf);");
        pw.format("              }\n");
        pw.format("         }\n");

        pw.format("         value_1.%s_size=length_1;\n", className);
        pw.format("         value_1.verify();\n");
        pw.format("         return value_1;\n");
        pw.println("    }\n");
    }

    private void generateEncode(PrintWriter pw, List<Filed> filedList) {
        pw.format("     public void encode(ByteBuf buf){\n");
        Set<Integer> set = new HashSet<>();
        for (Filed filed : filedList) {
            if (filed.getHasOneOf()) {
                set.add(filed.getOneIndex());
                continue;
            }
            String filedName = filed.getFiledName();
            pw.format("         if(has%s()){\n",
                CaseUtils.toCamelCase(filedName, true));
            pw.format("             this.encode_%s(buf);\n", filedName);
            pw.format("         }\n");
            pw.println();

        }
        for (int oneOf:set) {
            pw.format("     if(hasOneOf%d()){", oneOf);
            pw.format("         encodeOneOf_%d(buf);", oneOf);
            pw.format("     }");
        }
        pw.format("     }");
    }


    private void generateBuild(PrintWriter pw, String buildName) {
        pw.format("public static %s newBuilder(){\n", buildName);
        pw.format("return new %s();\n", buildName);
        pw.format("}\n");
    }

    private void generateVerify(PrintWriter pw, List<Filed> filed) {
        pw.format("     private void verify(){\n");
        for (Filed filed1 : filed) {
            if (filed1.getFiledLabel().getLabel().equals(FiledLabel.Required.getLabel())) {
                pw.format("         if(this.%s==null){\n", filed1.getFiledName());
                pw.format("             throw new RuntimeException(\"required %s\");\n", filed1.getFiledName());
                pw.format("         }\n");
            }
        }
        pw.format("     }\n");
    }






}

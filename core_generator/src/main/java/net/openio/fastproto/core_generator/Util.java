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
package net.openio.fastproto.core_generator;



import net.openio.fastproto.exception.FastProtoException;
import net.openio.fastproto.wrapper.*;
import org.jboss.forge.roaster.Roaster;

import java.io.*;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Map;


public  class Util {

     static int getTag(Filed filed){
        return (filed.getNum()<<3)|getWireType(filed);

    }

     static int getWireType(Filed filed){
        String type=filed.getFileType().getType();
        String label=filed.getFiledLabel().getLabel();
        if(label.equals(FiledLabel.Repeated.getLabel())){
            for(Option option:filed.getAllOption()){
                if(option.getKey().equals("packed")){
                    if((Boolean) option.getValue()) return 2;
                }
            }
        }
        if(type.equals(FiledType.String.getType())
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

     static String getJavaType(Filed filed, Map<String, Meta> metaMap){
        String type=filed.getFileType().getType();
        if(type.equals(FiledType.Message.getType())){
            return metaMap.get(filed.getFileTypeName()).getJavaObjectName();
        }else if(type.equals(FiledType.Enum.getType())){
            return metaMap.get(filed.getFileTypeName()).getJavaObjectName();
        }else {
            return filed.getFileTypeName();
        }
    }

     static int computeVarUIntSize(final int value) {
        if ((value & (0xffffffff << 7)) == 0) {
            return 1;
        } else if ((value & (0xffffffff << 14)) == 0) {
            return 2;
        } else if ((value & (0xffffffff << 21)) == 0) {
            return 3;
        } else if ((value & (0xffffffff << 28)) == 0) {
            return 4;
        } else {
            return 5;
        }
    }

     static int getTagEncodeSize(Filed filed){
        return computeVarUIntSize(getTag(filed));
    }

     static void filedDecode(Filed filed, PrintWriter pw, String bufName, String var, Map<String,Meta> map){
        switch (filed.getFileType().getType()){
            case FiledType.eMessage:
                pw.format("             %s=%s.decode(%s,Serializer.decodeVarInt32(%s));\n",var,getJavaType(filed,map),bufName,bufName);
                break;
            case FiledType.eEnum:
                pw.format("             %s=%s.get(Serializer.decodeVarInt32(%s));\n",var,getJavaType(filed,map),bufName);
                break;
            case FiledType.eFloat :
                pw.format("             %s=Serializer.decodeFloat(%s);\n",var,bufName);
                break;
            case FiledType.eString:
                pw.format("             %s=Serializer.decodeString(%s,Serializer.decodeVarInt32(%s));\n",var,bufName,bufName);
                break;
            case FiledType.eBytes:
                pw.format("             %s=Serializer.decodeByteString(%s,Serializer.decodeVarInt32(%s));\n",var,bufName,bufName);
                break;
            case FiledType.eDouble:
                pw.format("             %s=Serializer.decodeDouble(%s);\n",var,bufName);
                break;
            case FiledType.eFixed32:
                pw.format("             %s=Serializer.decode32(%s);\n",var,bufName);
                break;
            case FiledType.eFixed64:
                pw.format("             %s=Serializer.decode64(%s);\n",var,bufName);
                break;
            case FiledType.esFixed32:
                pw.format("             %s=Serializer.decode32(%s);\n",var,bufName);
                break;
            case FiledType.esFixed64:
                pw.format("             %s=Serializer.decode64(%s);\n",var,bufName);
                break;
            case FiledType.euInt64:
                pw.format("             %s=Serializer.decodeVarInt64(%s);\n",var,bufName);
                break;
            case FiledType.euInt32:
                pw.format("             %s=Serializer.decodeVarInt32(%s);\n",var,bufName);
                break;
            case FiledType.esInt64:
                pw.format("             %s=Serializer.decodeZigzag64(Serializer.decodeVarInt64(%s));\n",var,bufName);
                break;
            case FiledType.esInt32:
                pw.format("             %s=Serializer.decodeZigzag32(Serializer.decodeVarInt32(%s));\n",var,bufName);
                break;
            case FiledType.eInt32:
                pw.format("             %s=Serializer.decodeVarInt32(%s);\n",var,bufName);
                break;
            case FiledType.eInt64:
                pw.format("             %s=Serializer.decodeVarInt64(%s);\n",var,bufName);
                break;
            case FiledType.eBool:
                pw.format("             %s=Serializer.decodeBoolean(%s);\n",var,bufName);
                break;
        }

    }


    static void encodeFiled(PrintWriter pw,Filed f,String bufName,String var){
        switch (f.getFileType().getType()){
            case FiledType.eMessage:
                pw.format("Serializer.encodeVarInt32(%s,%s.getByteSize());\n",bufName,var);
                pw.format("%s.encode(%s);\n",var,bufName);
                break;
            case FiledType.eEnum:
                pw.format("            Serializer.encodeVarInt32(%s,%s.getNum());\n",bufName,var);
                break;
            case FiledType.eFloat :
                pw.format("            Serializer.encodeFloat(%s,%s);\n",bufName,var);
                break;
            case FiledType.eString:
                pw.format("            Serializer.encodeString(%s,%s);\n",bufName,var);
                break;
            case FiledType.eBytes:
                pw.format("            Serializer.encodeByteString(%s,%s);\n",bufName,var);
                break;
            case FiledType.eDouble:
                pw.format("            Serializer.encodeDouble(%s,%s);\n",bufName,var);
                break;
            case FiledType.eFixed32:
                pw.format("            Serializer.encode32(%s,%s);\n",bufName,var);
                break;
            case FiledType.eFixed64:
                pw.format("            Serializer.encode64(%s,%s);\n",bufName,var);
                break;
            case FiledType.esFixed32:
                pw.format("            Serializer.encode32(%s,%s);\n",bufName,var);
                break;
            case FiledType.esFixed64:
                pw.format("            Serializer.encode64(%s,%s);\n",bufName,var);
                break;
            case FiledType.euInt64:
                pw.format("            Serializer.encodeVarInt64(%s,%s);\n",bufName,var);
                break;
            case FiledType.euInt32:
                pw.format("            Serializer.encodeVarUInt32(%s,%s);\n",bufName,var);
                break;
            case FiledType.esInt64:

                pw.format("            Serializer.encodeVarInt64(%s,Serializer.encodeZigzag64(%s));\n",bufName,var);
                break;
            case FiledType.esInt32:
                pw.format("            Serializer.encodeVarUInt32(%s,Serializer.encodeZigzag32(%s));\n",bufName,var);
                break;
            case FiledType.eInt32:
                pw.format("            Serializer.encodeVarInt32(%s,%s);\n",bufName,var);
                break;
            case FiledType.eInt64:
                pw.format("            Serializer.encodeVarInt64(%s,%s);\n",bufName,var);
                break;
            case FiledType.eBool:
                pw.format("            Serializer.encodeBoolean(%s,%s);\n",bufName,var);
                break;
        }
    }

     static void size(PrintWriter pw,String type,String var,String deValue){
        switch (type){
            case FiledType.eInt32:
                pw.format("             %s+=Serializer.computeVarInt32Size(%s);\n",var,deValue);
                break;
            case FiledType.eInt64:
                pw.format("             %s+=Serializer.computeVarInt64Size(%s);\n",var,deValue);
                break;
            case FiledType.esInt32:
                pw.format("             %s+=Serializer.computeVarUInt32Size(Serializer.encodeZigzag32(%s));\n",var,deValue);
                break;
            case FiledType.esInt64:
                pw.format("             %s+=Serializer.computeVarInt64Size(Serializer.encodeZigzag64(%s));\n",var,deValue);
                break;
            case FiledType.euInt32:
                pw.format("             %s+=Serializer.computeVarUInt32Size(%s);\n",var,deValue);
                break;
            case FiledType.euInt64:
                pw.format("             %s+=Serializer.computeVarInt64Size(%s);\n",var,deValue);
                break;
            case FiledType.eEnum:
                pw.format("             %s+=Serializer.computeVarInt32Size(%s.getNum());\n",var,deValue);
                break;
            case FiledType.eBool:
                pw.format("             %s+=1;\n",var);
                break;
            case FiledType.eDouble:
                pw.format("             %s+=8;\n",var);
                break;
            case FiledType.eFloat:
                pw.format("             %s+=4;\n",var);
                break;
            case FiledType.eFixed32:
                pw.format("             %s+=4;\n",var);
                break;
            case FiledType.eFixed64:
                pw.format("             %s+=8;\n",var);
                break;
            case FiledType.esFixed32:
                pw.format("             %s+=4;\n",var);
                break;
            case FiledType.esFixed64:
                pw.format("             %s+=8;\n",var);
                break;

            case FiledType.eBytes:

                pw.format("             %s+=Serializer.computeVarInt32Size(%s.length);\n",var,deValue);
                pw.format("             %s+=%s.length;\n",var,deValue);
                break;
            case FiledType.eString:
                pw.format("             %s+=Serializer.computeVarInt32Size(ByteBufUtil.utf8Bytes(%s));\n",var,deValue);
                pw.format("             %s+=ByteBufUtil.utf8Bytes(%s);// value length \n",var,deValue);
                break;

            case FiledType.eMessage:
                pw.format("             %s+=Serializer.computeVarInt32Size(%s.getByteSize());\n",var,deValue);
                pw.format("             %s+=%s.getByteSize();\n",var,deValue);
                break;

        }
    }


     static File genFile(String dir, String pack, String fileName) throws IOException {
        String[] paths=pack.split("\\.");
        char end=dir.charAt(dir.length()-1);
        if(end=='/'||end=='\\'){
            dir=dir.substring(0,dir.length()-1);
        }
        StringBuilder path=new StringBuilder(dir);
        Arrays.stream(paths).forEach(
                a-> path.append('/').append(a)
        );

        File fileDir=new File(path.toString());
        if(!fileDir.isDirectory()){
            if(!fileDir.mkdirs()){
                throw new FastProtoException.FailToMakeDirException("Cannot create the dir "+fileDir.toPath().toString());
            }
        }
        path.append('/').append(fileName).append(".java");

        File file=new File(path.toString());
        if(!file.exists()){
            if (!file.createNewFile()){
                throw new FastProtoException.FailToCreateFileException("Cannot create the file "+file.toPath().toString());
            }
        }
        return file;

    }

     static void WriterContent(File file, StringWriter sw) throws IOException {
        String content= Roaster.format(sw.toString());
        try (Writer w = Files.newBufferedWriter(file.toPath())) {
            w.write(content);
        }
    }

    static void WriterContent(File file, String sw) throws IOException {
        String content = Roaster.format(sw);
        try (Writer w = Files.newBufferedWriter(file.toPath())) {
            w.write(content);
        }
    }



}

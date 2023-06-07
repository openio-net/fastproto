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
package net.openio.fastproto.compile;


import net.openio.fastproto.config.Config;
import net.openio.fastproto.exception.FastProtoException;
import com.github.os72.protocjar.Protoc;
import com.google.protobuf.DescriptorProtos;
import net.openio.fastproto.wrapper.ObjectType;
import net.openio.fastproto.wrapper.Filed;
import net.openio.fastproto.wrapper.FiledLabel;
import net.openio.fastproto.wrapper.FiledType;
import net.openio.fastproto.wrapper.Option;
import net.openio.fastproto.wrapper.OptionType;
import net.openio.fastproto.wrapper.Message;
import net.openio.fastproto.wrapper.Meta;
import net.openio.fastproto.wrapper.Package;
import org.apache.commons.text.CaseUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Parse the contents of proto file.
 */
public class Parser {

    private static final Set<String> KEYWORD = new HashSet<>();
    //Store the package information corresponding to each proto
    private final List<Package> list = new ArrayList<>();

    public List<Package> getList() {
        return list;
    }
    //Store meta information of each entity class
    public Map<String, Meta> metas = new HashMap<>();
    //Store all dependent files to be loaded
    private final Set<String> importSet = new HashSet<>();
    //Store map information of all required files
    public Map<String, Message> maps = new HashMap<>();


    static {
        KEYWORD.add("null");
        KEYWORD.add("false");
        KEYWORD.add("true");
        KEYWORD.add("const");
        KEYWORD.add("goto");
        KEYWORD.add("while");
        KEYWORD.add("volatile");
        KEYWORD.add("void");
        KEYWORD.add("try");
        KEYWORD.add("transient");
        KEYWORD.add("throws");
        KEYWORD.add("throw");
        KEYWORD.add("this");
        KEYWORD.add("synchronized");
        KEYWORD.add("switch");
        KEYWORD.add("super");
        KEYWORD.add("strictfp");
        KEYWORD.add("static");
        KEYWORD.add("short");
        KEYWORD.add("return");
        KEYWORD.add("public");
        KEYWORD.add("protected");
        KEYWORD.add("private");
        KEYWORD.add("package");
        KEYWORD.add("new");
        KEYWORD.add("native");
        KEYWORD.add("long");
        KEYWORD.add("instanceof");
        KEYWORD.add("interface");
        KEYWORD.add("int");
        KEYWORD.add("import");
        KEYWORD.add("implements");
        KEYWORD.add("if");
        KEYWORD.add("for");
        KEYWORD.add("float");
        KEYWORD.add("finally");
        KEYWORD.add("final");
        KEYWORD.add("extends");
        KEYWORD.add("enum");
        KEYWORD.add("else");
        KEYWORD.add("double");
        KEYWORD.add("do");
        KEYWORD.add("default");
        KEYWORD.add("continue");
        KEYWORD.add("class");
        KEYWORD.add("char");
        KEYWORD.add("catch");
        KEYWORD.add("case");
        KEYWORD.add("byte");
        KEYWORD.add("boolean");
        KEYWORD.add("break");
        KEYWORD.add("assert");
        KEYWORD.add("abstract");
    }

    /**
     *
     *  Parse the proto file information to generate the corresponding package
     *
     *  @param config
     *  @throws IOException
     *  @throws InterruptedException
     */
    public   void parse(Config config) throws IOException, InterruptedException {

        for (String s : config.getProtoFiles()) {
            importSet.add(s);
            String s1 = s.substring(0, s.length() - 6);
            String[] args = {"-v3.11.1", "-I", config.getFileDir(), "--descriptor_set_out",
                    config.getFileDir() + s1 + ".desc", config.getFileDir() + s};
            int result = Protoc.runProtoc(args); //Generate desc file and call protoc

            DescriptorProtos.FileDescriptorSet fileDescriptorSet;
            FileInputStream is = null;
            File file = new File(config.getFileDir() + s1 + ".desc");
            if (!file.exists()) {
                throw new FileNotFoundException(file.getAbsolutePath() + " is not exists");
            }
            try {

                is = new FileInputStream(file);
                fileDescriptorSet = DescriptorProtos.FileDescriptorSet.parseFrom(is);
                is.close();

            } catch (Exception e) {
                e.printStackTrace();

                return;
            } finally {
                if (is != null) {
                    is.close();
                }
            }
            Package pack = new Package();
            parsePackage(pack, fileDescriptorSet, config.getFileDir());
            list.add(pack);
        }

    }

    private void parseImportPack(String fileDir, Set<String> importSet) throws IOException, InterruptedException {
        for (String s : importSet) {
            importSet.add(s);
            String s1 = s.substring(0, s.length() - 6);
            String[] args = {"-v3.11.1", "-I", fileDir, "--descriptor_set_out", fileDir + s1 + ".desc", fileDir + s};
            int result = Protoc.runProtoc(args); //Generate desc file and call protoc

            DescriptorProtos.FileDescriptorSet fileDescriptorSet;
            FileInputStream is = null;
            File file = new File(fileDir + s1 + ".desc");
            if (!file.exists()) {
                throw new FileNotFoundException(file.getAbsolutePath() + " is not exists");
            }
            try {

                is = new FileInputStream(file);
                fileDescriptorSet = DescriptorProtos.FileDescriptorSet.parseFrom(is);
                is.close();

            } catch (Exception e) {
                e.printStackTrace();

                return;
            } finally {
                if (is != null) {
                    is.close();
                }
            }
            Package pack = new Package();
            parsePackage(pack, fileDescriptorSet, fileDir);
        }
    }

    private void parsePackage(Package pack, DescriptorProtos.FileDescriptorSet fileDescriptorSet, String fileDir) throws IOException, InterruptedException {
        for (DescriptorProtos.FileDescriptorProto proto : fileDescriptorSet.getFileList()) {
            for (String list : proto.getDependencyList()) {
                pack.addImportFile(list);
            }
            pack.setFileName(proto.getName());
            pack.setPackageName(proto.getPackage());
            pack.setJavaPackName(proto.getOptions().getJavaPackage());
            for (DescriptorProtos.DescriptorProto descriptorProto : proto.getMessageTypeList()) {

                parseMessage(pack, descriptorProto);

            }
            for (DescriptorProtos.EnumDescriptorProto descriptorProto:proto.getEnumTypeList()) {
                parseEnum(pack, descriptorProto);

            }
            Set<String> set = new HashSet<>();
            for (String s:proto.getDependencyList()) {
                if (importSet.contains(s)) {
                    continue;
                }
                importSet.add(s);
                set.add(s);
            }
            parseImportPack(fileDir, set);

        }


    }

    private void parseMessage(Package pack, DescriptorProtos.DescriptorProto proto) {
        metas.put(pack.getPackageName() + "." + proto.getName(),
                new Meta(proto.getName(), pack.getJavaPackName(), pack.getPackageName(), "", pack.getPackageName() + "." + proto.getName()));
        Message message = new Message();
        message.setObjectType(ObjectType.Message);
        message.setName(proto.getName());
        for (DescriptorProtos.DescriptorProto proto1 : proto.getNestedTypeList()) {

            if (proto1.getOptions().getMapEntry()) {
                parseMap(message, proto1, pack.getPackageName() + "." + proto.getName());
            } else {
                parseMessage(message, proto1, proto.getName(), pack.getPackageName(), pack.getJavaPackName());
            }
        }
        for (DescriptorProtos.EnumDescriptorProto proto1 : proto.getEnumTypeList()) {

            parseEnum(message, proto1, proto.getName(), pack.getPackageName(), pack.getJavaPackName());
        }




        Set<String> fileName = new HashSet<>();
        for (DescriptorProtos.FieldDescriptorProto proto1:proto.getFieldList()) {

            parseMessageFiled(message, proto1, fileName);
        }

        pack.addObject(message);

    }


    private void parseMessage(Message message1, DescriptorProtos.DescriptorProto proto, String filed, String protoPack, String javaPack) {
        metas.put(protoPack + "." + filed + "." + proto.getName(),
                new Meta(proto.getName(), javaPack, protoPack, filed, protoPack + "." + filed + "." + proto.getName())); //添加类元数据


        Message message = new Message();
        message.setObjectType(ObjectType.Message);
        message.setName(proto.getName());
        for (DescriptorProtos.DescriptorProto proto1 : proto.getNestedTypeList()) {

            if (proto1.getOptions().getMapEntry()) {
                parseMap(message, proto1, protoPack + "." + filed);
            } else {
                parseMessage(message, proto1, filed + "." + proto.getName(), protoPack, javaPack);
            }
        }

        for (DescriptorProtos.EnumDescriptorProto proto1 : proto.getEnumTypeList()) {
            parseEnum(message, proto1, filed + "." + proto.getName(), protoPack, javaPack);
        }


        Set<String> fieldName = new HashSet<>();
        for (DescriptorProtos.FieldDescriptorProto proto1:proto.getFieldList()) {
            parseMessageFiled(message, proto1, fieldName);
        }

        message1.addObject(message);

    }



    private void parseEnum(Package pack, DescriptorProtos.EnumDescriptorProto proto) {


        metas.put(pack.getPackageName() + "." + proto.getName(),
                new Meta(proto.getName(), pack.getJavaPackName(), pack.getPackageName(),
                        "", pack.getPackageName() + "." + proto.getName()));


        Message message = new Message();
        message.setObjectType(ObjectType.Enum);
        message.setName(proto.getName());
        for (DescriptorProtos.EnumValueDescriptorProto proto1 : proto.getValueList()) {
            parseEnumFiled(message, proto1);
        }

        pack.addObject(message);
    }

    private void parseEnum(Message message1, DescriptorProtos.EnumDescriptorProto proto, String filed, String protoPack, String javaClass) {
        metas.put(protoPack + "." + filed + "." + proto.getName(),
                new Meta(proto.getName(), javaClass, protoPack, filed, protoPack + "." + filed + "." + proto.getName()));

        Message message = new Message();
        message.setObjectType(ObjectType.Enum);
        message.setName(proto.getName());

        for (DescriptorProtos.EnumValueDescriptorProto proto1 : proto.getValueList()) {

            parseEnumFiled(message, proto1);
        }

        message1.addObject(message);
    }


    private void parseMessageFiled(Message message, DescriptorProtos.FieldDescriptorProto descriptorProto, Set<String> fileName) {

        Filed filed = new Filed();
        if (descriptorProto.hasOneofIndex()) {
            filed.setOneIndex(descriptorProto.getOneofIndex());
            filed.setHasOneOf(true);
        }

        filed.setNum(descriptorProto.getNumber());
        boolean cfl = KEYWORD.contains(descriptorProto.getName());
        String name = CaseUtils.toCamelCase(descriptorProto.getName(), cfl, '_');
        if (fileName.contains(name)) {
            throw new FastProtoException.AttributeNameConflictException(filed.getFiledName() + " name of attribute: " + name + ", which conflicts with other attribute names. Please rename it.");
        }
        filed.setFiledName(name);
        if (descriptorProto.hasTypeName()) {
            filed.setFileTypeName(descriptorProto.getTypeName().substring(1));
        }

        if (descriptorProto.getLabel().name().equals("LABEL_REPEATED")) {
            filed.setFiledLabel(FiledLabel.Repeated);
            if (descriptorProto.getOptions().getPacked()) {
                Option option = new Option("packed", true, OptionType.FiledOption);
                filed.addOption(option);
            }
        } else if (descriptorProto.getLabel().name().equals("LABEL_REQUIRED")) {
            filed.setFiledLabel(FiledLabel.Required);
        } else {
            filed.setFiledLabel(FiledLabel.Optional);
        }

        if (filed.getFiledName().equals("x_uint32")) {
            System.out.println();
        }

        switch (descriptorProto.getType().getNumber()) {
            case 1:
                filed.setFileType(FiledType.Double);
                filed.setFileTypeName(FiledType.Double.getJavaClass().getName());
                break;
            case 2:
                filed.setFileType(FiledType.Float);
                filed.setFileTypeName(FiledType.Float.getJavaClass().getName());
                break;
            case 3:
                filed.setFileTypeName(FiledType.Int64.getJavaClass().getName());
                filed.setFileType(FiledType.Int64);
                break;
            case 4:
                filed.setFileTypeName(FiledType.uInt64.getJavaClass().getName());
                filed.setFileType(FiledType.uInt64);
                break;
            case 5:
                filed.setFileTypeName(FiledType.Int32.getJavaClass().getName());
                filed.setFileType(FiledType.Int32);
                break;
            case 6:
                filed.setFileTypeName(FiledType.Fixed64.getJavaClass().getName());
                filed.setFileType(FiledType.Fixed64);
                break;
            case 7:
                filed.setFileTypeName(FiledType.Fixed32.getJavaClass().getName());
                filed.setFileType(FiledType.Fixed32);
                break;
            case 8:
                filed.setFileTypeName(FiledType.Bool.getJavaClass().getName());
                filed.setFileType(FiledType.Bool);
                break;
            case 9:
                filed.setFileTypeName(FiledType.String.getJavaClass().getName());
                filed.setFileType(FiledType.String);
                break;
            case 10:
                break;
            case 11:
                filed.setFileType(FiledType.Message);

                filed.setFileTypeName(descriptorProto.getTypeName().substring(1));
                break;

            case 12:
                filed.setFileTypeName("byte[]");
                filed.setFileType(FiledType.Bytes);
                break;
            case 13:
                filed.setFileTypeName(FiledType.uInt32.getJavaClass().getName());
                filed.setFileType(FiledType.uInt32);
                break;
            case 14:
                filed.setFileType(FiledType.Enum);
            filed.setFileTypeName(descriptorProto.getTypeName().substring(1));
                break;
            case 15:
                filed.setFileTypeName(FiledType.sFixed32.getJavaClass().getName());
                filed.setFileType(FiledType.sFixed32);
                break;
            case 16:
                filed.setFileTypeName(FiledType.sFixed64.getJavaClass().getName());
                filed.setFileType(FiledType.sFixed64);
                break;
            case 17:
                filed.setFileTypeName(FiledType.sInt32.getJavaClass().getName());
                filed.setFileType(FiledType.sInt32);
                break;
            case 18:
                filed.setFileTypeName(FiledType.sInt64.getJavaClass().getName());
                filed.setFileType(FiledType.sInt64);
                break;
            default:

        }

       /**TYPE_DOUBLE = 1;
        TYPE_FLOAT = 2;
        // Not ZigZag encoded.  Negative numbers take 10 bytes.  Use TYPE_SINT64 if
        // negative values are likely.
        TYPE_INT64 = 3;
        TYPE_UINT64 = 4;
        // Not ZigZag encoded.  Negative numbers take 10 bytes.  Use TYPE_SINT32 if
        // negative values are likely.
        TYPE_INT32 = 5;
        TYPE_FIXED64 = 6;
        TYPE_FIXED32 = 7;
        TYPE_BOOL = 8;
        TYPE_STRING = 9;
        // Tag-delimited aggregate.
        // Group type is deprecated and not supported in proto3. However, Proto3
        // implementations should still be able to parse the group wire format and
        // treat group fields as unknown fields.
        TYPE_GROUP = 10;
        TYPE_MESSAGE = 11;  // Length-delimited aggregate.

        // New in version 2.
        TYPE_BYTES = 12;
        TYPE_UINT32 = 13;
        TYPE_ENUM = 14;
        TYPE_SFIXED32 = 15;
        TYPE_SFIXED64 = 16;
        TYPE_SINT32 = 17;  // Uses ZigZag encoding.
        TYPE_SINT64 = 18;  // Uses ZigZag encoding.*/
        message.addFiled(filed);

    }

    private void parseEnumFiled(Message message, DescriptorProtos.EnumValueDescriptorProto descriptorProto) {
        Filed filed = new Filed();
        filed.setFiledName(descriptorProto.getName());
        filed.setNum(descriptorProto.getNumber());
        filed.setFileType(FiledType.Int32);
        message.addFiled(filed);
    }

    private void parseMap(Message message, DescriptorProtos.DescriptorProto proto, String field) {

        Message message1 = new Message();
        message1.setObjectType(ObjectType.Map);
        message1.setName(proto.getName());
        for (DescriptorProtos.FieldDescriptorProto fieldDescriptorProto : proto.getFieldList()) {
            parseMapFiled(message1, fieldDescriptorProto);
        }
        message.addObject(message1);
        maps.put(field + "." + proto.getName(), message1);

    }

    private void parseMapFiled(Message message, DescriptorProtos.FieldDescriptorProto proto) {
        Filed filed = new Filed();
        filed.setFiledName(proto.getName());
        filed.setFiledLabel(FiledLabel.Required);
        filed.setNum(proto.getNumber());
        if (proto.hasTypeName()) {
            filed.setFileTypeName(proto.getTypeName().substring(1));
        }
        switch (proto.getType().getNumber()) {
            case 1:
                filed.setFileType(FiledType.Double);
                filed.setFileTypeName(FiledType.Double.getJavaClass().getName());
                break;
            case 2:
                filed.setFileType(FiledType.Float);
                filed.setFileTypeName(FiledType.Float.getJavaClass().getName());
                break;
            case 3:
                filed.setFileTypeName(FiledType.Int64.getJavaClass().getName());
                filed.setFileType(FiledType.Int64);
                break;
            case 4:
                filed.setFileTypeName(FiledType.uInt64.getJavaClass().getName());
                filed.setFileType(FiledType.uInt64);
                break;
            case 5:
                filed.setFileTypeName(FiledType.Int32.getJavaClass().getName());
                filed.setFileType(FiledType.Int32);
                break;
            case 6:
                filed.setFileTypeName(FiledType.Fixed64.getJavaClass().getName());
                filed.setFileType(FiledType.Fixed64);
                break;
            case 7:
                filed.setFileTypeName(FiledType.Fixed32.getJavaClass().getName());
                filed.setFileType(FiledType.Fixed32);
                break;
            case 8:
                filed.setFileTypeName(FiledType.Bool.getJavaClass().getName());
                filed.setFileType(FiledType.Bool);
                break;
            case 9:
                filed.setFileTypeName(FiledType.String.getJavaClass().getName());
                filed.setFileType(FiledType.String);
                break;
            case 10:
                break;
            case 11:
                filed.setFileType(FiledType.Message);

                filed.setFileTypeName(proto.getTypeName().substring(1));
                break;

            case 12:
                filed.setFileTypeName("byte[]");
                filed.setFileType(FiledType.Bytes);
                break;
            case 13:
                filed.setFileTypeName(FiledType.Double.getJavaClass().getName());
                filed.setFileType(FiledType.uInt32);
                break;
            case 14:
                filed.setFileType(FiledType.Enum);
                filed.setFileTypeName(proto.getTypeName().substring(1));
                break;
            case 15:
                filed.setFileTypeName(FiledType.sFixed32.getJavaClass().getName());
                filed.setFileType(FiledType.sFixed32);
                break;
            case 16:
                filed.setFileTypeName(FiledType.sFixed64.getJavaClass().getName());
                filed.setFileType(FiledType.sFixed64);
                break;
            case 17:
                filed.setFileTypeName(FiledType.sInt32.getJavaClass().getName());
                filed.setFileType(FiledType.sInt32);
                break;
            case 18:
                filed.setFileTypeName(FiledType.sInt64.getJavaClass().getName());
                filed.setFileType(FiledType.sInt64);
                break;
            default:

        }
        message.addFiled(filed);
    }






}

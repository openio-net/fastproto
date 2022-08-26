package com.fastproto.compile;


import com.google.protobuf.DescriptorProtos;
import com.google.protobuf.DescriptorProtos.FileDescriptorSet;
import com.google.protobuf.Descriptors;
import com.fastproto.config.Config;
import com.fastproto.wrapper.*;
import com.fastproto.wrapper.Object;
import com.fastproto.wrapper.Package;


import java.io.FileInputStream;
import java.util.*;


public class Parse {

    private static List<Package> list=new ArrayList<>();

    public static List<Package> getList() {
        return list;
    }


    private static String exeAddress="src/main/resources/protoc/Window64/protoc.exe";
    public  void parse(Config config) throws Exception {
        Runtime runtime = Runtime.getRuntime();
        for (String s : config.getProtoFiles()) {
            String s1 = s.substring(0, s.length() - 6);
            String l = exeAddress + " " + "-I=" + config.getFileDir() + " " + "--descriptor_set_out=" + config.getFileDir()  + s1 + ".desc " + " " + config.getFileDir()  + s;
           System.out.println("cmd "+l);
            Process p = runtime.exec(l);
            p.waitFor();
            System.out.println(new String(p.getErrorStream().readAllBytes()));
            FileInputStream is=new FileInputStream(config.getFileDir()+s1+".desc");

            DescriptorProtos.FileDescriptorSet fileDescriptorSet=DescriptorProtos.FileDescriptorSet.parseFrom(is);
            Package pack=new ProtoPackage();
            Pack(pack,fileDescriptorSet);
            list.add(pack);
        }

    }

    private void Pack(Package pack ,DescriptorProtos.FileDescriptorSet fileDescriptorSet ){
        for(DescriptorProtos.FileDescriptorProto proto:fileDescriptorSet.getFileList()){
            pack.setFileName(proto.getName());
            pack.setPackageName(proto.getPackage());
            for(DescriptorProtos.DescriptorProto descriptorProto:proto.getMessageTypeList()){
                Message(pack,descriptorProto);

            }


        }
    }

    private void Message(Package pack,DescriptorProtos.DescriptorProto proto){
        Object object=new ProtoObject();
        object.setObjectType(ObjectType.Message);
        object.setName(proto.getName());
        for(DescriptorProtos.DescriptorProto proto1:proto.getNestedTypeList()){

            if(proto1.getOptions().getMapEntry()){
                Map(object,proto1);
            }else {
                Message(object, proto1);
            }
        }

        for(DescriptorProtos.EnumDescriptorProto proto1 :proto.getEnumTypeList()){
            Enum(object,proto1);
        }



        for(DescriptorProtos.FieldDescriptorProto proto1:proto.getFieldList()){

            MessageFiled(object,proto1);
        }

        pack.addObject(object);

    }


    private void Message(Object object1, DescriptorProtos.DescriptorProto proto){
        Object object=new ProtoObject();
        object.setObjectType(ObjectType.Message);
        object.setName(proto.getName());

        for(DescriptorProtos.DescriptorProto proto1:proto.getNestedTypeList()){
            if(proto1.getOptions().getMapEntry()){

                Map(object,proto1);
            }else {
                Message(object, proto1);
            }
        }

        for(DescriptorProtos.EnumDescriptorProto proto1 :proto.getEnumTypeList()){
            Enum(object,proto1);
        }



        for(DescriptorProtos.FieldDescriptorProto proto1:proto.getFieldList()){
            MessageFiled(object,proto1);
        }

        object1.addObject(object);

    }



    private void Enum(Package pack,DescriptorProtos.EnumDescriptorProto proto){
        Object object=new ProtoObject();
        object.setObjectType(ObjectType.Enum);
        object.setName(proto.getName());
        for(DescriptorProtos.EnumValueDescriptorProto proto1:proto.getValueList()){
            EnumFiled(object,proto1);
        }

        pack.addObject(object);
    }

    private void Enum(Object object1,DescriptorProtos.EnumDescriptorProto proto){
        Object object=new ProtoObject();
        object.setObjectType(ObjectType.Enum);
        object.setName(proto.getName());

        for(DescriptorProtos.EnumValueDescriptorProto proto1:proto.getValueList()){
            EnumFiled(object,proto1);
        }

        object1.addObject(object);
    }




    private void MessageFiled(Object object,DescriptorProtos.FieldDescriptorProto descriptorProto){

        Filed filed=new ProtoFiled();
        filed.setTag(descriptorProto.getNumber());
        filed.setFiledName(descriptorProto.getName());
        if(descriptorProto.hasTypeName()) {
            filed.setFileTypeName(descriptorProto.getTypeName().substring(1));
        }

        if(descriptorProto.getLabel().name().equals("LABEL_REPEATED")){
            filed.setFiledLabel(FiledLabel.Repeated);
        }else if(descriptorProto.getLabel().name().equals("LABEL_REQUIRED")){
            filed.setFiledLabel(FiledLabel.Required);
        }else {
            filed.setFiledLabel(FiledLabel.Optional);
        }

        switch (descriptorProto.getType().getNumber()){
            case 1: filed.setFileType(FiledType.Double);
            case 2: filed.setFileType(FiledType.Float);
            case 3: filed.setFileType(FiledType.Int64);
            case 4: filed.setFileType(FiledType.uInt64);
            case 5: filed.setFileType(FiledType.Int32);
            case 6: filed.setFileType(FiledType.Fixed64);
            case 7: filed.setFileType(FiledType.Fixed32);
            case 8: filed.setFileType(FiledType.Bool);
            case 9: filed.setFileType(FiledType.String);
            case 10: ;;
            case 11: filed.setFileType(FiledType.Message);

            filed.setFileTypeName(descriptorProto.getTypeName().substring(1));

            case 12: filed.setFileType(FiledType.Bytes);
            case 13: filed.setFileType(FiledType.uInt32);
            case 14: filed.setFileType(FiledType.Enum);filed.setFileTypeName(descriptorProto.getTypeName().substring(1));
            case 15: filed.setFileType(FiledType.sFixed32);
            case 16: filed.setFileType(FiledType.sFixed64);
            case 17: filed.setFileType(FiledType.sInt32);
            case 18: filed.setFileType(FiledType.sInt64);

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
        object.addFiled(filed);

    }

    private void EnumFiled(Object object, DescriptorProtos.EnumValueDescriptorProto descriptorProto){
        Filed filed=new ProtoFiled();
        filed.setFiledName(descriptorProto.getName());
        filed.setTag(descriptorProto.getNumber());
        filed.setFileType(FiledType.Int32);
        object.addObject(object);
    }

    private void Map(Object object,DescriptorProtos.DescriptorProto proto){

        Object object1=new ProtoObject();
        object1.setObjectType(ObjectType.Map);
        object1.setName(proto.getName());
        for(DescriptorProtos.FieldDescriptorProto fieldDescriptorProto: proto.getFieldList()){
            MapFiled(object1,fieldDescriptorProto);
        }
        object.addObject(object1);

    }

    private void MapFiled(Object object,DescriptorProtos.FieldDescriptorProto proto){
        Filed filed=new ProtoFiled();
        filed.setFiledName(proto.getName());
        filed.setFiledLabel(FiledLabel.Repeated);
        filed.setTag(proto.getNumber());
        if(proto.hasTypeName()){
            filed.setFileTypeName(proto.getTypeName().substring(1));
        }
        switch (proto.getType().getNumber()){
            case 1: filed.setFileType(FiledType.Double);
            case 2: filed.setFileType(FiledType.Float);
            case 3: filed.setFileType(FiledType.Int64);
            case 4: filed.setFileType(FiledType.uInt64);
            case 5: filed.setFileType(FiledType.Int32);
            case 6: filed.setFileType(FiledType.Fixed64);
            case 7: filed.setFileType(FiledType.Fixed32);
            case 8: filed.setFileType(FiledType.Bool);
            case 9: filed.setFileType(FiledType.String);
            case 10: ;;
            case 11: filed.setFileType(FiledType.Message);filed.setFileTypeName(proto.getTypeName().substring(1));
            case 12: filed.setFileType(FiledType.Bytes);
            case 13: filed.setFileType(FiledType.uInt32);
            case 14: filed.setFileType(FiledType.Enum);filed.setFileTypeName(proto.getTypeName().substring(1));
            case 15: filed.setFileType(FiledType.sFixed32);
            case 16: filed.setFileType(FiledType.sFixed64);
            case 17: filed.setFileType(FiledType.sInt32);
            case 18: filed.setFileType(FiledType.sInt64);

        }
        object.addFiled(filed);
    }






}

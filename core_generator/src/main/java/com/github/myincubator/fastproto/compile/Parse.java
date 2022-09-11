package com.github.myincubator.fastproto.compile;


import com.github.myincubator.fastproto.wrapper.*;
import com.github.myincubator.fastproto.wrapper.Object;
import com.github.myincubator.fastproto.wrapper.Package;
import com.github.os72.protocjar.Protoc;
import com.google.protobuf.DescriptorProtos;
import com.github.myincubator.fastproto.config.Config;



import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;


public class Parse {

    private  List<Package> list=new ArrayList<>();

    private Set<String> importSet=new HashSet<>();

    public  List<Package> getList() {
        return list;
    }

    public  Map<String, Meta> metas=new HashMap<>(); //以proto的全类名为索引

    public  Map<String, Object> maps=new HashMap<>();//存储Map的信息

    public   void parse(Config config) throws IOException, InterruptedException {

        for (String s : config.getProtoFiles()) {
            importSet.add(s);
            String s1 = s.substring(0, s.length() - 6);
            String[] args = {"-v3.11.1", "-I",config.getFileDir(),"--descriptor_set_out",config.getFileDir()  + s1 + ".desc",config.getFileDir()  + s};
            int result= Protoc.runProtoc(args);//生成desc文件，调用protoc

            DescriptorProtos.FileDescriptorSet fileDescriptorSet;
            FileInputStream is=null;
            File file=new File(config.getFileDir()+s1+".desc");
            if(!file.exists()){
                throw new RuntimeException("file is not exists");
            }
            try {

                is=new FileInputStream(file);
                fileDescriptorSet=DescriptorProtos.FileDescriptorSet.parseFrom(is);
                is.close();

            }catch (Exception e){
                e.printStackTrace();

                return;
            }finally {
                if(is!=null) {
                    is.close();
                }
            }
            Package pack=new ProtoPackage();
            Pack(pack,fileDescriptorSet,config.getFileDir());
            list.add(pack);
        }

    }

    private void importPack(String fileDir,Set<String> importSet ) throws IOException, InterruptedException {
        for (String s : importSet) {
            importSet.add(s);
            String s1 = s.substring(0, s.length() - 6);
            String[] args = {"-v3.11.1", "-I", fileDir, "--descriptor_set_out", fileDir + s1 + ".desc", fileDir + s};
            int result = Protoc.runProtoc(args);//生成desc文件，调用protoc

            DescriptorProtos.FileDescriptorSet fileDescriptorSet;
            FileInputStream is = null;
            File file = new File(fileDir + s1 + ".desc");
            if (!file.exists()) {
                throw new RuntimeException("file is not exists");
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
            Package pack = new ProtoPackage();
            Pack(pack, fileDescriptorSet,fileDir);
        }
    }

    private  void Pack(Package pack ,DescriptorProtos.FileDescriptorSet fileDescriptorSet ,String fileDir) throws IOException, InterruptedException {
        for(DescriptorProtos.FileDescriptorProto proto:fileDescriptorSet.getFileList()){
            for( String list:proto.getDependencyList()) {
                pack.addImportFile(list);
            }
            pack.setFileName(proto.getName());
            pack.setPackageName(proto.getPackage());
            pack.setJavaPackName(proto.getOptions().getJavaPackage());
            for(DescriptorProtos.DescriptorProto descriptorProto:proto.getMessageTypeList()){

                Message(pack,descriptorProto);

            }
            for(DescriptorProtos.EnumDescriptorProto descriptorProto:proto.getEnumTypeList()){
                Enum(pack,descriptorProto);

            }
            Set<String> set=new HashSet<>();
            for(String s:proto.getDependencyList()){
                if(importSet.contains(s)){
                    continue;
                }
                importSet.add(s);
                set.add(s);
            }
            importPack(fileDir,set);

        }


    }

    private  void Message(Package pack,DescriptorProtos.DescriptorProto proto){
        metas.put(pack.getPackageName()+"."+proto.getName(),
                new ProtoMeta(proto.getName(),pack.getJavaPackName(),pack.getPackageName(),"", pack.getPackageName()+"."+proto.getName()));
        Object object=new ProtoObject();
        object.setObjectType(ObjectType.Message);
        object.setName(proto.getName());
        for(DescriptorProtos.DescriptorProto proto1:proto.getNestedTypeList()){

            if(proto1.getOptions().getMapEntry()){
                Map(object,proto1,pack.getPackageName()+"."+proto.getName());
            }else {
                Message(object, proto1,proto.getName(),pack.getPackageName(), pack.getJavaPackName());
            }
        }
        for(DescriptorProtos.EnumDescriptorProto proto1 :proto.getEnumTypeList()){

            Enum(object,proto1,proto.getName(),pack.getPackageName(), pack.getJavaPackName());
        }





        for(DescriptorProtos.FieldDescriptorProto proto1:proto.getFieldList()){

            MessageFiled(object,proto1);
        }

        pack.addObject(object);

    }


    private  void Message(Object object1, DescriptorProtos.DescriptorProto proto,String filed,String protoPack,String javaPack){
        metas.put(protoPack+"."+filed+"."+proto.getName(),
                new ProtoMeta(proto.getName(),javaPack,protoPack,filed,protoPack+"."+filed+"."+proto.getName()));//添加类元数据


        Object object=new ProtoObject();
        object.setObjectType(ObjectType.Message);
        object.setName(proto.getName());
        for(DescriptorProtos.DescriptorProto proto1:proto.getNestedTypeList()){

            if(proto1.getOptions().getMapEntry()){
                Map(object,proto1,protoPack+"."+filed);
            }else {
                Message(object, proto1,filed+"."+proto.getName(),protoPack,javaPack);
            }
        }

        for(DescriptorProtos.EnumDescriptorProto proto1 :proto.getEnumTypeList()){
            Enum(object,proto1,filed+"."+proto.getName(),protoPack,javaPack);
        }



        for(DescriptorProtos.FieldDescriptorProto proto1:proto.getFieldList()){
            MessageFiled(object,proto1);
        }

        object1.addObject(object);

    }



    private  void Enum(Package pack,DescriptorProtos.EnumDescriptorProto proto){


        metas.put(pack.getPackageName()+"."+proto.getName(),
                new ProtoMeta(proto.getName(),pack.getJavaPackName(),pack.getPackageName(),
                        "", pack.getPackageName()+"."+proto.getName()));


        Object object=new ProtoObject();
        object.setObjectType(ObjectType.Enum);
        object.setName(proto.getName());
        for(DescriptorProtos.EnumValueDescriptorProto proto1:proto.getValueList()){
            EnumFiled(object,proto1);
        }

        pack.addObject(object);
    }

    private  void Enum(Object object1,DescriptorProtos.EnumDescriptorProto proto,String filed,String protoPack,String javaClass){
        metas.put(protoPack+"."+filed+"."+proto.getName(),
                new ProtoMeta(proto.getName(),javaClass,protoPack,filed,protoPack+"."+filed+"."+proto.getName()));

        Object object=new ProtoObject();
        object.setObjectType(ObjectType.Enum);
        object.setName(proto.getName());

        for(DescriptorProtos.EnumValueDescriptorProto proto1:proto.getValueList()){

            EnumFiled(object,proto1);
        }

        object1.addObject(object);
    }




    private  void MessageFiled(Object object,DescriptorProtos.FieldDescriptorProto descriptorProto){

        Filed filed=new ProtoFiled();
        if(descriptorProto.hasOneofIndex()){
            filed.setOneIndex(descriptorProto.getOneofIndex());
            filed.setHasOneOf(true);
        }

        filed.setNum(descriptorProto.getNumber());
        filed.setFiledName(descriptorProto.getName());
        if(descriptorProto.hasTypeName()) {
            filed.setFileTypeName(descriptorProto.getTypeName().substring(1));
        }

        if(descriptorProto.getLabel().name().equals("LABEL_REPEATED")){
            filed.setFiledLabel(FiledLabel.Repeated);
            if(descriptorProto.getOptions().getPacked()){
                Option option=new ProtoOption("packed",true,OptionType.FiledOption);
                filed.addOption(option);
            }
        }else if(descriptorProto.getLabel().name().equals("LABEL_REQUIRED")){
            filed.setFiledLabel(FiledLabel.Required);
        }else {
            filed.setFiledLabel(FiledLabel.Optional);
        }

        if(filed.getFiledName().equals("x_uint32")){
            System.out.println();
        }

        switch (descriptorProto.getType().getNumber()){
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
            case 10: ;;break;
            case 11: filed.setFileType(FiledType.Message);

            filed.setFileTypeName(descriptorProto.getTypeName().substring(1));break;

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

    private   void EnumFiled(Object object, DescriptorProtos.EnumValueDescriptorProto descriptorProto){
        Filed filed=new ProtoFiled();
        filed.setFiledName(descriptorProto.getName());
        filed.setNum(descriptorProto.getNumber());
        filed.setFileType(FiledType.Int32);
        object.addFiled(filed);
    }

    private   void Map(Object object,DescriptorProtos.DescriptorProto proto,String field){

        Object object1=new ProtoObject();
        object1.setObjectType(ObjectType.Map);
        object1.setName(proto.getName());
        for(DescriptorProtos.FieldDescriptorProto fieldDescriptorProto: proto.getFieldList()){
            MapFiled(object1,fieldDescriptorProto);
        }
        object.addObject(object1);
        maps.put(field+"."+proto.getName(),object1);

    }

    private   void MapFiled(Object object,DescriptorProtos.FieldDescriptorProto proto){
        Filed filed=new ProtoFiled();
        filed.setFiledName(proto.getName());
        filed.setFiledLabel(FiledLabel.Required);
        filed.setNum(proto.getNumber());
        if(proto.hasTypeName()){
            filed.setFileTypeName(proto.getTypeName().substring(1));
        }
        switch (proto.getType().getNumber()){
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
            case 10: ;;break;
            case 11: filed.setFileType(FiledType.Message);

                filed.setFileTypeName(proto.getTypeName().substring(1));break;

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

        }
        object.addFiled(filed);
    }






}

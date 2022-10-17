package com.github.openio.fastproto.wrapper;

import com.github.openio.fastproto.error.ErrorMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class Package {
    
    private static final Logger logger = LoggerFactory.getLogger(Message.class);
    
    
    private final List<Option> options = new ArrayList<>();
    private final List<String> ImportFile = new ArrayList<>();
    private final List<String> ImportPublicFile = new ArrayList<>();
    private final List<Message> messages = new ArrayList<>();
    private String fileName;
    private String packageName;
    private String javaPackage;
    
    public List<Option> getOption() {
        return options;
    }
    
    
    public Package addOption(Option option) {
        if (option == null) {
            recordLoggerOne(ErrorMessage.ERROR_OPTIONAL_NULL.getMethodName(), option.toString(),ErrorMessage.ERROR_OPTIONAL_NULL.getMessage());
            throw new RuntimeException(ErrorMessage.ERROR_OPTIONAL_NULL.getMessage());
        }
        options.add(option);
        return this;
    }
    
    
    public String getFileName() {
        return fileName;
    }
    
    
    public Package setFileName(String fileName) {
        this.fileName = fileName;
        return this;
    }
    
    
    public List<String> getImportFile() {
        return ImportFile;
    }
    
    
    public Package addImportFile(String path) {
        if (path == null) {
            recordLoggerOne(ErrorMessage.ERROR_IMPORT_FILE_PATH_NULL.getMethodName(), path,ErrorMessage.ERROR_IMPORT_FILE_PATH_NULL.getMessage());
            throw new RuntimeException(ErrorMessage.ERROR_PATH_NULL.getMessage());
        }
        ImportFile.add(path);
        return this;
    }
    
    
    public List<String> getImportPublicFile() {
        return ImportPublicFile;
    }
    
    
    public Package addPublicPackage(String path) {
        if (path == null) {
            recordLoggerOne(ErrorMessage.ERROR_PATH_NULL.getMethodName(),path,ErrorMessage.ERROR_PATH_NULL.getMessage());
            throw new RuntimeException(ErrorMessage.ERROR_PATH_NULL.getMessage());
        }
        ImportPublicFile.add(path);
        return this;
    }
    
    
    public String getPackageName() {
        return packageName;
    }
    
    public Package setPackageName(String name) {
        if (name == null) {
            recordLoggerOne(ErrorMessage.ERROR_PACKAGE_NAME_NULL.getMethodName(),name,ErrorMessage.ERROR_OPTIONAL_NULL.getMessage());
            throw new RuntimeException(ErrorMessage.ERROR_PACKAGE_NAME_NULL.getMessage());
        }
        this.packageName = name;
        return this;
    }
    
    public List<Message> getAllObject() {
        return messages;
    }
    
    public Message getObjectByName(String Name) {
        if (Name == null) {
            recordLoggerOne(ErrorMessage.ERROR_NAME_NULL.getMethodName(),Name,ErrorMessage.ERROR_NAME_NULL.getMessage() );
            throw new RuntimeException(ErrorMessage.ERROR_NAME_NULL.getMessage());
        }
        for (Message message : messages) {
            if (message.getName().equals(Name))
                return message;
        }
        return null;
    }
    
    public Package addObject(Message message) {
        if (message == null) {
            recordLoggerOne(ErrorMessage.ERROR_OBJ_NULL.getMethodName(),message.toString(),ErrorMessage.ERROR_OBJ_NULL.getMessage());
            throw new RuntimeException(ErrorMessage.ERROR_OBJ_NULL.getMessage());
        }
        messages.add(message);
        return this;
    }
    
    public String getJavaPackName() {
        return javaPackage;
    }
    
    public Package setJavaPackName(String name) {
        this.javaPackage = name;
        return this;
    }
    
    public String toString() {
        return "ProtoPackage{" +
                "fileName='" + fileName + '\'' +
                ", packageName='" + packageName + '\'' +
                ", javaPackage='" + javaPackage + '\'' +
                ", options=" + options +
                ", ImportFile=" + ImportFile +
                ", ImportPublicFile=" + ImportPublicFile +
                ", objects=" + messages +
                '}';
    }
    
    private void recordLoggerOne(String errorMethod, String inputParam, String message) {
        logger.error("errorMethod:{} , inputParams:{} and errorMessage:can not mkdirs:{}",errorMethod, inputParam, message);
    }
    
    
}

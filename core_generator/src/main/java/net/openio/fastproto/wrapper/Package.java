package net.openio.fastproto.wrapper;

import java.util.ArrayList;
import java.util.List;

public class Package {


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
            throw new RuntimeException("option is null");
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
            throw new RuntimeException("path is null");
        }
        ImportFile.add(path);
        return this;
    }


    public List<String> getImportPublicFile() {
        return ImportPublicFile;
    }


    public Package addPublicPackage(String path) {
        if (path == null) {
            throw new RuntimeException("path is null");
        }
        ImportPublicFile.add(path);
        return this;
    }


    public String getPackageName() {
        return packageName;
    }

    public Package setPackageName(String name) {
        if (name == null) {
            throw new RuntimeException("PackageName is null");
        }
        this.packageName = name;
        return this;
    }

    public List<Message> getAllObject() {
        return messages;
    }

    public Message getObjectByName(String Name) {
        if (Name == null) {
            throw new RuntimeException("Name is null");
        }
        for (Message message : messages) {
            if (message.getName().equals(Name))
                return message;
        }
        return null;
    }

    public Package addObject(Message message) {
        if (message == null) {
            throw new RuntimeException("object is null");
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
}

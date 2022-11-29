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
            throw new NullPointerException("the option is null");
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
            throw new NullPointerException("the path is null");
        }
        ImportFile.add(path);
        return this;
    }


    public List<String> getImportPublicFile() {
        return ImportPublicFile;
    }


    public Package addPublicPackage(String path) {
        if (path == null) {
            throw new NullPointerException("the path is null");
        }
        ImportPublicFile.add(path);
        return this;
    }


    public String getPackageName() {
        return packageName;
    }

    public Package setPackageName(String name) {
        if (name == null) {
            throw new NullPointerException("the path is null");
        }
        this.packageName = name;
        return this;
    }

    public List<Message> getAllObject() {
        return messages;
    }

    public Message getObjectByName(String Name) {
        if (Name == null) {
            throw new NullPointerException("the name is null");
        }
        for (Message message : messages) {
            if (message.getName().equals(Name))
                return message;
        }
        return null;
    }

    public Package addObject(Message message) {
        if (message == null) {
            throw new NullPointerException("the message is null");
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

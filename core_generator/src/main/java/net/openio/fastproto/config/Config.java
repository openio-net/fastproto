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
package net.openio.fastproto.config;

import java.util.ArrayList;
import java.util.List;

/**
 * Configuration class for file and Java output paths.
 */
public class Config {

    private String fileDir = ""; //File scan root path

    private  String javaOut = ""; //Java file output path

    private final List<String> protoFiles = new ArrayList<String>(); //File name

    public String getFileDir() {
        return fileDir;
    }

    public void setFileDir(String fileDir1) {
        char a = fileDir1.charAt(fileDir1.length() - 1);
        if (a == '/' && a == '\\') {
            this.fileDir = fileDir1;
        } else {
            this.fileDir = fileDir1 + '/';
        }

    }

    public String getJavaOut() {
        return javaOut;
    }

    public void setJavaOut(String fileDir1) {
        this.javaOut = fileDir1;
    }

    public void addProtoFiles(String file) {
        protoFiles.add(file);
    }

    public void addProtoFiles(List files) {
        protoFiles.addAll(files);
    }

    public List<String> getProtoFiles() {
        return this.protoFiles;
    }
}

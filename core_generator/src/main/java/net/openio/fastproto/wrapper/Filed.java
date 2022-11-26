/*
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

public class Filed {


    private final List<Option> options = new ArrayList<>();
    private int tag;
    private FiledLabel filedLabel;
    private FiledType filedType;
    private String fileName;
    private boolean oneOf;
    private int oneIndex;
    private String fileTypeName;


    public int getNum() {
        return tag;
    }


    public Filed setNum(int tag) {
        this.tag = tag;
        return this;
    }


    public FiledLabel getFiledLabel() {
        return this.filedLabel;
    }


    public Filed setFiledLabel(FiledLabel filedLabel) {
        this.filedLabel = filedLabel;
        return this;
    }


    public FiledType getFileType() {
        return filedType;
    }


    public Filed setFileType(FiledType filedType) {
        this.filedType = filedType;
        return this;
    }


    public String getFiledName() {
        return fileName;
    }

    public Filed setFiledName(String filedName) {
        this.fileName = filedName;
        return this;
    }

    public String getFileTypeName() {
        return this.fileTypeName;
    }

    public Filed setFileTypeName(String filedName) {
        this.fileTypeName = filedName;

        return this;
    }

    public List<Option> getAllOption() {
        return options;
    }


    public Option getOption(String key) {
        for (Option option : options) {
            if (option.getKey().equals(key)) {
                return option;
            }
        }
        return null;
    }


    public Filed addOption(Option option) {
        if (option == null) {
            throw new NullPointerException("the option is null");
        }
        options.add(option);
        return this;
    }

    public boolean getHasOneOf() {
        return oneOf;
    }

    public Filed setHasOneOf(boolean b) {
        oneOf = b;
        return this;
    }

    public int getOneIndex() {
        return 0;
    }


    public Filed setOneIndex(int oneIndex) {
        this.oneIndex = oneIndex;
        return this;
    }


    public String toString() {
        return "ProtoFiled{" +
                "tag=" + tag +
                ", filedLabel=" + filedLabel +
                ", filedType=" + filedType +
                ", fileName='" + fileName + '\'' +
                ", oneOf=" + oneOf +
                ", oneIndex=" + oneIndex +
                ", options=" + options +
                ", fileTypeName='" + fileTypeName + '\'' +
                '}';
    }
}

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

/**
 * Represents a message in FastProto.
 */
public class Message {

    private String name;

    private ObjectType objectType;

    private final List<Filed> filed = new ArrayList<>();

    private final List<Option> options = new ArrayList<>();

    private final List<Message> messages = new ArrayList<>();


    public String getName() {
        return name;
    }


    public ObjectType getObjectType() {
        return objectType;
    }


    public Message setObjectType(ObjectType objectType) {
        this.objectType = objectType;
        return this;
    }


    public Message setName(String name) {
        this.name = name;
        return this;
    }


    public List<Filed> getAllFiled() {

        return filed;
    }


    public Filed getFile(String filedName) {
        for (Filed filed : this.filed) {
            if (filed.getFiledName().equals(filedName)) {
                return filed;
            }
        }
        return null;
    }


    public Message addFiled(Filed filed) {
        this.filed.add(filed);
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


    public Message addOption(Option option) {
        if (option == null) {
            throw new NullPointerException("the option is null");
        }
        options.add(option);
        return this;
    }


    public Message addObject(Message message) {
        this.messages.add(message);
        return this;
    }


    public List<Message> getObject() {
        return messages;
    }


    public String toString() {
        return "Message{" + "name='" + name
            + '\'' + ", objectType=" + objectType
            + ", filed=" + filed + ", options=" + options
            + ", objects=" + messages + '}';
    }

}

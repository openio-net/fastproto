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

/**
 * Represents metadata for a class in FastProto.
 */
public class Meta {

    private final String className;


    private final String javaPackageName;

    private final String protoPackName;

    private final String filed;

    private final String objectName;


    public Meta(String className, String javaPackageName, String protoPackName, String filed, String objectName) {
        this.className = className;
        this.javaPackageName = javaPackageName;
        this.protoPackName = protoPackName;
        this.filed = filed;
        this.objectName = objectName;
    }

    public String getClassName() {
        return className;
    }

    public String getProtoPackName() {
        return protoPackName;
    }

    public String getJavaPackageName() {
        return javaPackageName;
    }

    public String getField() {
        return filed;
    }

    public String getObjectName() {
        return objectName;
    }

    public String getJavaObjectName() {
        if (filed != null && !filed.equals("")) {
            if (javaPackageName != null && !javaPackageName.equals("")) {
                return javaPackageName + "." + filed + "." + className;
            } else {
                return filed + "." + className;
            }
        }


        if (javaPackageName != null && !javaPackageName.equals("")) {
            return javaPackageName + "." + className;
        } else {
            return className;
        }
    }
}

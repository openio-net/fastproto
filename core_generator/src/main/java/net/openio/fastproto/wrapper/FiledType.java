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
 * Enumeration representing the type of a field in FastProto.
 */
public enum FiledType {

    Int32("int32", Integer.class),
    Int64("int64", Long.class),
    Double("double", java.lang.Double.class),
    Float("float", java.lang.Float.class),
    uInt32("uint32", Integer.class),
    uInt64("uInt64", Long.class),
    sInt32("sInt32", Integer.class),
    sInt64("sInt64", Long.class),
    Fixed32("fixed32", Integer.class),
    Fixed64("fixed64", Long.class),
    sFixed32("sfixed32", Integer.class),
    sFixed64("sfixed64", Long.class),
    Bool("bool", Boolean.class),
    String("String", String.class),
    Bytes("bytes", byte[].class),
    Message("message", Object.class),
    Enum("enum", Enum.class);

    public final static String E_INT_32 = "int32";
    public final static String E_INT_64 = "int64";
    public final static String E_DOUBLE = "double";
    public final static String E_FLOAT = "float";
    public final static String EU_INT_32 = "uint32";
    public final static String EU_INT_64 = "uInt64";
    public final static String ES_INT_32 = "sInt32";
    public final static String ES_INT_64 = "sInt64";
    public final  static String E_FIXED_32 = "fixed32";
    public final  static String E_FIXED_64 = "fixed64";
    public final  static String ES_FIXED_32 = "sfixed32";
    public final  static String ES_FIXED_64 = "sfixed64";
    public final  static String E_BOOL = "bool";
    public final  static String E_STRING = "String";
    public final  static String E_BYTES = "bytes";
    public final  static String E_MESSAGE = "message";
    public final  static String E_ENUM = "enum";

    String type;

    Class javaClass;

    public Class getJavaClass() {
        return javaClass;
    }
    FiledType(String type, Class javaClass) {
        this.type = type;
        this.javaClass = javaClass;
    }

    public java.lang.String getType() {
        return type;
    }


}

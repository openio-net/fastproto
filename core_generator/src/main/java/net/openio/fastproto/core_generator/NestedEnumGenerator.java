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
package net.openio.fastproto.core_generator;

import net.openio.fastproto.wrapper.Filed;
import net.openio.fastproto.wrapper.Message;

import java.io.File;
import java.io.PrintWriter;
import java.util.List;

public class NestedEnumGenerator {

    Message message;

    public NestedEnumGenerator(Message message) {
        this.message = message;
    }

    public File generate(PrintWriter pw) {
        pw.println("public static enum " + message.getName() + "{");
        pw.println();
        generateEnumFiled(pw, message.getAllFiled(), message.getName());

        pw.println("    int num;");
        pw.println();
        pw.println("    " + message.getName() + "( int num ){");
        pw.println("        this.num = num;");
        pw.println("    }");
        pw.println();
        pw.println("    int getNum" + "( ){");
        pw.println("        return this.num;");
        pw.println("    }");
        pw.println("}");
        pw.println();
        pw.println();
        pw.println();

        return null;
    }

    private void generateEnumFiled(PrintWriter pw, List<Filed> list, String className) {
        StringBuilder s = new StringBuilder(list.size() * 40);
        s.append("  public static ").append(className).append(" get(int tag){");
        for (Filed filed : list) {
            pw.println("    " + filed.getFiledName() + " ( " + filed.getNum() + " ),");
            pw.println();
            s.append("      if(tag==").append(filed.getNum()).append("){\n");
            s.append("          return ").append(className).append(".").append(filed.getFiledName()).append(";\n");
            s.append("  }\n");
        }
        pw.println(";");
        s.append("      return null;\n    }");
        pw.println(s);


    }
}

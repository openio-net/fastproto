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
package net.openio.fastproto.core.generator;

import net.openio.fastproto.wrapper.Filed;
import net.openio.fastproto.wrapper.Message;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

/**
 * Generates an enum entry for a given message.
 */
public class EnumEntryGenerator {


    private final Message message;

    private final String outDir;

    private final String packages;

    public EnumEntryGenerator(Message message, String outDir, String aPackage) {
        this.message = message;
        this.outDir = outDir;
        packages = aPackage;
    }

    public File generate() throws IOException {
        Message message = this.message;
        String javaOut = this.outDir;
        String pack = this.packages;
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        if (pack != null && !pack.equals("")) {
            pw.format("package %s;\n", pack);
        }
        pw.println("public enum " + message.getName() + "{");
        pw.println();
        enumFiled(pw, message.getAllFiled(), message.getName());

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

        File file = Util.genFile(javaOut, pack, message.getName());
        Util.writerContent(file, sw);
        return file;
    }

    private void enumFiled(PrintWriter pw, List<Filed> list, String className) {
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

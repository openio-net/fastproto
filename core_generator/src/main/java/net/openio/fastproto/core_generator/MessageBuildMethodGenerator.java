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
package net.openio.fastproto.core_generator;

import net.openio.fastproto.wrapper.Filed;
import net.openio.fastproto.wrapper.Meta;
import org.apache.commons.text.CaseUtils;

import java.io.PrintWriter;
import java.util.Map;

/**
 * The MessageBuildMethodGenerator class is responsible for generating setter, getter, clear, and has methods for a field in a message builder class.
 */
public class MessageBuildMethodGenerator {

    Filed filed;

    String className;

    public MessageBuildMethodGenerator(Filed filed, String className) {
        this.filed = filed;
        this.className = className;
    }

    public void generate(PrintWriter pw, Map<String, Meta> metaMap) {
        pw.println();
        generateSet(pw, filed, metaMap, className);
        pw.println();
        pw.println();
        generateGet(pw, filed, metaMap);
        pw.println();
        generateClear(pw, filed, className);
        pw.println();
        generateHas(pw, filed);
        pw.println();

    }


    private void generateSet(PrintWriter pw, Filed filed, Map<String, Meta> metaMap, String className) {

        pw.format("     public %s set%s(%s a){\n", className, CaseUtils.toCamelCase(filed.getFiledName(), true), Util.getJavaType(filed, metaMap));
        pw.format("         this.%s=a;\n", filed.getFiledName());
        pw.format("         return this;\n");
        pw.println("    }");

    }

    private void generateGet(PrintWriter pw, Filed filed, Map<String, Meta> metaMap) {

        pw.format("     public %s get%s(){\n", Util.getJavaType(filed, metaMap), CaseUtils.toCamelCase(filed.getFiledName(), true));
        pw.format("        return this.%s;\n", filed.getFiledName());
        pw.println("    }");

    }

    private void generateClear(PrintWriter pw, Filed filed, String className) {

        pw.format("     public %s clear%s(){\n", className, CaseUtils.toCamelCase(filed.getFiledName(), true));
        pw.format("        this.%s=null;\n", filed.getFiledName());
        pw.format("         return this;");
        pw.println("    }");

    }

    private void generateHas(PrintWriter pw, Filed filed) {

        pw.format("     public boolean has%s(){\n", CaseUtils.toCamelCase(filed.getFiledName(), true));
        pw.format("        return this.%s!=null;\n", filed.getFiledName());
        pw.println("    }");

    }

}

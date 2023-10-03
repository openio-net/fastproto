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
import net.openio.fastproto.wrapper.Meta;
import org.apache.commons.text.CaseUtils;

import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

/**
 * The OneOfBuildMethodGenerator class is responsible for generating code for the methods related to a oneof group in the builder class.
 */
public class OneOfBuildMethodGenerator {

    List<Filed> filed;

    String className;

    public OneOfBuildMethodGenerator(List<Filed> filed, String name) {
        this.filed = filed;
        this.className = name;
    }

    public void generate(PrintWriter pw, Map<String, Meta> metaMap) {
        pw.println();
        set(pw, filed, metaMap, className);

        pw.println();
        pw.println();
        generateGet(pw, filed);
        pw.println();
        generateClear(pw, filed, className);
        pw.println();
        generateHas(pw, filed);
        pw.println();

    }


    private void set(PrintWriter pw, List<Filed> filed1, Map<String, Meta> metaMap, String className) {
        int oneOfIndex = filed1.get(0).getOneIndex();
        for (Filed filed : filed1) {
            pw.format("     public %s setOneOf%d%sValue(%s a){\n",
                    className, oneOfIndex, CaseUtils.toCamelCase(filed.getFiledName(), true), Util.getJavaType(filed, metaMap));
            pw.format("     if(a==null) {\n");
            pw.format("         endSet%dNum=-1;\n", oneOfIndex);
            pw.format("         return this;\n");
            pw.format("     }");
            pw.format("     endSet%dNum=%d;\n", oneOfIndex, filed.getNum());
            pw.format("     this.%s=a;\n", filed.getFiledName());
            pw.format("     return this;\n");
            pw.println("    }\n");
        }
    }

    private void generateGet(PrintWriter pw, List<Filed> filed1) {
        int oneOfIndex = filed1.get(0).getOneIndex();

        pw.format("    public java.lang.Object getOneOf%dValue(){\n", oneOfIndex);
        pw.format("         if(endSet%dNum==-1) return null;", oneOfIndex);
        pw.format("         switch(endSet%dNum){\n", oneOfIndex);
        for (Filed filed : filed1) {
            pw.format("         case %d :\n", filed.getNum());
            pw.format("            return this.%s;\n", filed.getFiledName());
        }
        pw.println("        }");
        pw.format("return null;");
        pw.println("    }");

    }

    private void generateClear(PrintWriter pw, List<Filed> list, String className) {
        int oneOfIndex = list.get(0).getOneIndex();
        pw.format("     public %s clearOneOf%d(){\n", className, oneOfIndex);
        for (Filed filed : list) {
            pw.format("         this.%s=null;\n", filed.getFiledName());
        }
        pw.format("         endSet%dNum=-1;\n", oneOfIndex);
        pw.format("         return this;\n");
        pw.println("     }");
    }

    private void generateHas(PrintWriter pw, List<Filed> list) {
        int oneOfIndex = list.get(0).getOneIndex();
        pw.format("     public boolean hasOneOf%d(){\n", oneOfIndex);
        pw.format("         return endSet%dNum!=-1;\n", oneOfIndex);
        pw.println("     }");
    }

    private void getNum(PrintWriter pw, List<Filed> list) {
        int oneOfIndex = list.get(0).getOneIndex();
        pw.format("     public int getOneOf%dNum(){\n", oneOfIndex);
        pw.format("         return endSet%dNum;\n", oneOfIndex);
        pw.println("     }");
    }
}

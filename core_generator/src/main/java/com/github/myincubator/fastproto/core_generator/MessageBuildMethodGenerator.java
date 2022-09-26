package com.github.myincubator.fastproto.core_generator;

import com.github.myincubator.fastproto.wrapper.Filed;
import com.github.myincubator.fastproto.wrapper.Meta;
import org.apache.commons.text.CaseUtils;

import java.io.PrintWriter;
import java.util.Map;

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

package com.github.myincubator.fastproto.core_generator;

import com.github.myincubator.fastproto.wrapper.Filed;
import com.github.myincubator.fastproto.wrapper.Meta;
import org.apache.commons.text.CaseUtils;

import java.io.PrintWriter;
import java.util.Map;

public class RepeatedBuildMethodGenerator {

    private final Filed filed;

    String className;

    public RepeatedBuildMethodGenerator(Filed filed, String className) {
        this.filed = filed;
        this.className = className;
    }

    public void generate(PrintWriter pw, Map<String, Meta> metaMap, String className) {
        pw.println();
        generateSet(pw, filed, metaMap, className);
        pw.println();
        pw.println();
        generateGet(pw, filed, metaMap);
        pw.println();
        generateRemove(pw, filed, className);
        pw.println();
        generateSize(pw, filed);
        pw.println();
        generateClear(pw, filed.getFiledName(), className);
        pw.println();
        generateHas(pw, filed.getFiledName());
        pw.println();
    }


    private void generateSet(PrintWriter pw, Filed filed, Map<String, Meta> metaMap, String className) {

        String fileName = filed.getFiledName();
        pw.format("     public %s add%s(%s a){\n", className, CaseUtils.toCamelCase(fileName, true), Util.getJavaType(filed, metaMap));
        pw.format("         if(a==null){");
        pw.format("             throw new RuntimeException(\"a is null\");");
        pw.format("         }");
        pw.format("         if(this.%s==null){\n", filed.getFiledName());
        pw.format("             this.%s=new java.util.ArrayList<>();\n", fileName);
        pw.format("             this.%s.add(a);\n", fileName);
        pw.println("        }else{");
        pw.format("             this.%s.add(a);\n",fileName);
        pw.println("        }");
        pw.println("        return this;");
        pw.println("    }");

    }

    private void generateGet(PrintWriter pw, Filed filed, Map<String, Meta> metaMap) {
        String fileName = filed.getFiledName();
        pw.format("     public %s get%s(int index){\n", Util.getJavaType(filed, metaMap), CaseUtils.toCamelCase(fileName, true));
        pw.format("         if(this.%s==null||index>=this.%s.size()){\n", fileName, fileName);
        pw.format("             throw new RuntimeException(\"%s is null or index bigger than %s size\");\n", fileName, fileName);
        pw.println("        }\n");
        pw.format("        return this.%s.get(index);\n", filed.getFiledName());
        pw.println("    }");
    }


    private void generateRemove(PrintWriter pw, Filed filed, String className) {
        String fileName = filed.getFiledName();
        pw.format("     public %s remove%s(int index){\n", className, CaseUtils.toCamelCase(fileName, true));
        pw.format("         if(this.%s==null||index>=this.%s.size()){\n", fileName, fileName);
        pw.format("             throw new RuntimeException(\"%s is null or index bigger than %s size\");\n", fileName, fileName);
        pw.println("        }\n");
        pw.format("        this.%s.remove(index);\n", fileName);
        pw.println("        return this;\n");
        pw.println("    }");
    }


    private void generateSize(PrintWriter pw, Filed filed) {
        String fileName = filed.getFiledName();
        pw.format("     public int size%s(){\n", CaseUtils.toCamelCase(fileName, true));
        pw.format("         if(this.%s==null){\n", fileName);
        pw.format("             throw new RuntimeException(\"%s is null\");\n", fileName);
        pw.println("        }\n");
        pw.format("        return this.%s.size();\n", fileName);
        pw.println("    }");
    }

    private void generateClear(PrintWriter pw, String filedName, String className) {
        pw.format("     public %s clear%s(){\n", className, CaseUtils.toCamelCase(filedName, true));
        pw.format("         this.%s=null;\n", filedName);
        pw.format("         return this;\n");
        pw.println("     }");
    }

    private void generateHas(PrintWriter pw, String filedName) {
        pw.format("     public boolean has%s(){\n", CaseUtils.toCamelCase(filedName, true));
        pw.format("         if(this.%s==null){\n", filedName);
        pw.format("             return false;\n");
        pw.format("         }\n");
        pw.format("         return this.%s.size()!=0;\n", filedName);
        pw.println("     }");
    }

}

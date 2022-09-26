package com.github.myincubator.fastproto.core_generator;

import com.github.myincubator.fastproto.wrapper.Filed;
import com.github.myincubator.fastproto.wrapper.Message;
import com.github.myincubator.fastproto.wrapper.Meta;
import org.apache.commons.text.CaseUtils;

import java.io.PrintWriter;
import java.util.Map;

public class MapBuildMethodGenerator {
    private final Filed filed;

    private final Message message;
    String className;

    public MapBuildMethodGenerator(Filed filed, Message message, String className) {
        this.filed = filed;
        this.message = message;
        this.className = className;
    }

    public void generate(PrintWriter pw, Map<String, Meta> metaMap) {
        Filed key = null;
        Filed value = null;
        for (Filed filed1 : message.getAllFiled()) {
            if (filed1.getFiledName().equals("key")) {
                key = filed1;
            } else {
                value = filed1;
            }
        }

        pw.println();
        generatePut(pw, filed.getFiledName(), metaMap, key, value, className);
        pw.println();
        generateGet(pw, filed.getFiledName(), metaMap, key, value);
        pw.println();
        generateGetKeySet(pw, filed.getFiledName(), metaMap, key);
        pw.println();
        generateRemove(pw, filed.getFiledName(), metaMap, key);
        pw.println();
        generateClear(pw, filed.getFiledName(), className);
        pw.println();
        generateHas(pw, filed.getFiledName());


    }

    public void generatePut(PrintWriter pw, String fileName, Map<String, Meta> metaMap, Filed key, Filed value, String className) {
        pw.format("     public %s put%s(%s key,%s value){\n", className, CaseUtils.toCamelCase(fileName, true), Util.getJavaType(key, metaMap), Util.getJavaType(value, metaMap));
        pw.format("         if(key==null||value==null){");
        pw.format("             throw new RuntimeException(\"key or value is null\");");
        pw.format("         }");
        pw.format("         if(this.%s==null){\n", fileName);
        pw.format("             this.%s=new java.util.HashMap<>();\n", fileName);
        pw.println("        }\n");
        pw.format("         this.%s.put(key,value);\n", fileName);
        pw.println("        return this;\n");
        pw.println("    }");
    }

    public void generateGet(PrintWriter pw, String fileName, Map<String, Meta> metaMap, Filed key, Filed value) {
        pw.format("     public %s get%s(%s key){\n", Util.getJavaType(value, metaMap), CaseUtils.toCamelCase(fileName, true), Util.getJavaType(key, metaMap));
        pw.format("         if(this.%s==null){\n", fileName);
        pw.format("             throw new RuntimeException(\"%s is null\");\n", fileName);
        pw.println("        }\n");
        pw.format("         return this.%s.get(key);\n", fileName);
        pw.println("    }");
    }

    public void generateGetKeySet(PrintWriter pw, String fileName, Map<String, Meta> metaMap, Filed key) {
        pw.format("     public java.util.Set<%s> get%s(){\n", Util.getJavaType(key, metaMap), CaseUtils.toCamelCase(fileName, true));
        pw.format("         if(this.%s==null){\n", fileName);
        pw.format("             throw new RuntimeException(\"%s is null\");\n", fileName);
        pw.println("        }\n");
        pw.format("         return this.%s.keySet();\n", fileName);
        pw.println("    }");
    }

    public void generateRemove(PrintWriter pw, String fileName, Map<String, Meta> metaMap, Filed key) {
        pw.format("     public void remove%s(%s key){\n", CaseUtils.toCamelCase(fileName, true), Util.getJavaType(key, metaMap));
        pw.format("         if(this.%s==null){\n", fileName);
        pw.format("             throw new RuntimeException(\"%s is null\");\n", fileName);
        pw.println("        }\n");
        pw.format("         this.%s.remove(key);\n", fileName);
        pw.println("    }\n");
    }

    public void generateClear(PrintWriter pw, String fileName, String className) {
        pw.format("     public %s clear%s(){\n", className, CaseUtils.toCamelCase(fileName, true));
        pw.format("         this.%s=null;\n", fileName);
        pw.format("         return this;\n");
        pw.println("    }");
    }

    public void generateHas(PrintWriter pw, String fileName) {
        pw.format("     public boolean has%s(){\n", CaseUtils.toCamelCase(fileName, true));
        pw.format("         if(this.%s==null){\n", fileName);
        pw.format("             return false;\n");
        pw.format("         }");
        pw.format("         return this.%s.keySet().size()!=0;\n", fileName);
        pw.println("     }");
    }
}

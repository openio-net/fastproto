package com.github.myincubator.fastproto.core_generator;

import com.github.myincubator.fastproto.wrapper.Filed;
import com.github.myincubator.fastproto.wrapper.Meta;
import org.apache.commons.text.CaseUtils;

import java.io.PrintWriter;
import java.util.Map;

public class MessageBuildMethodGenerator {


    public static void generate(PrintWriter pw,Filed filed, Map<String, Meta> metaMap, String className){
        pw.println();
        set(pw,filed,metaMap,className);

        pw.println();
        pw.println();
        get(pw,filed,metaMap);
        pw.println();
        clear(pw,filed,className);
        pw.println();
        has(pw,filed);
        pw.println();

    }


    private static void set(PrintWriter pw, Filed filed, Map<String, Meta> metaMap,String className){

        pw.format("     public %s set%s(%s a){\n",className,CaseUtils.toCamelCase(filed.getFiledName(),true), Util.getJavaType(filed,metaMap));
        pw.format("         this.%s=a;\n",filed.getFiledName());
        pw.format("         return this;\n");
        pw.println("    }");

    }

    private static void get(PrintWriter pw, Filed filed, Map<String, Meta> metaMap){

        pw.format("     public %s get%s(){\n", Util.getJavaType(filed,metaMap),CaseUtils.toCamelCase(filed.getFiledName(),true));
        pw.format("        return this.%s;\n",filed.getFiledName());
        pw.println("    }");

    }

    private static void clear(PrintWriter pw, Filed filed,String className){

        pw.format("     public %s clear%s(){\n", className,CaseUtils.toCamelCase(filed.getFiledName(),true));
        pw.format("        this.%s=null;\n",filed.getFiledName());
        pw.format("         return this;");
        pw.println("    }");

    }

    private static void has(PrintWriter pw, Filed filed){

        pw.format("     public boolean has%s(){\n", CaseUtils.toCamelCase(filed.getFiledName(),true));
        pw.format("        return this.%s!=null;\n",filed.getFiledName());
        pw.println("    }");

    }

}

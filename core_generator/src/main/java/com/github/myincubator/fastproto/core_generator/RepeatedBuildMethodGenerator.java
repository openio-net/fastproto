package com.github.myincubator.fastproto.core_generator;

import com.github.myincubator.fastproto.wrapper.Filed;
import com.github.myincubator.fastproto.wrapper.Meta;

import java.io.PrintWriter;
import java.util.Map;

public class RepeatedBuildMethodGenerator {


    public static void generate(PrintWriter pw,Filed filed, Map<String, Meta> metaMap, String className){
        pw.println();
        set(pw,filed,metaMap,className);
        pw.println();
        pw.println();
        get(pw,filed,metaMap);
        pw.println();
        remove(pw,filed,className);
        pw.println();
        size(pw,filed);
        pw.println();
        clear(pw, filed.getFiledName(), className);
        pw.println();
        has(pw, filed.getFiledName());
        pw.println();
    }


    private static void set(PrintWriter pw, Filed filed, Map<String, Meta> metaMap,String className){

        String fileName=filed.getFiledName();
        pw.format("     public %s add_%s_value(%s a){\n",className,fileName, Util.getJavaType(filed,metaMap));
        pw.format("         if(a==null){");
        pw.format("             throw new RuntimeException(\"a is null\");");
        pw.format("         }");
        pw.format("         if(this.%s==null){\n",filed.getFiledName());
        pw.format("             this.%s=new java.util.ArrayList<>();\n",fileName);
        pw.format("             this.%s.add(a);\n",fileName);
        pw.println("        }else{");
        pw.format("             this.%s.add(a);\n",fileName);
        pw.println("        }");
        pw.println("        return this;");
        pw.println("    }");

    }

    private static void get(PrintWriter pw, Filed filed, Map<String, Meta> metaMap){
        String fileName=filed.getFiledName();
        pw.format("     public %s get_%s_value(int index){\n", Util.getJavaType(filed,metaMap),fileName);
        pw.format("         if(this.%s==null||index>=this.%s.size()){\n",fileName,fileName);
        pw.format("             throw new RuntimeException(\"%s is null or index bigger than %s size\");\n",fileName,fileName);
        pw.println("        }\n");
        pw.format("        return this.%s.get(index);\n",filed.getFiledName());
        pw.println("    }");
    }


    private static void remove(PrintWriter pw, Filed filed,String className){
        String fileName=filed.getFiledName();
        pw.format("     public %s remove_%s_value(int index){\n",className,fileName);
        pw.format("         if(this.%s==null||index>=this.%s.size()){\n",fileName,fileName);
        pw.format("             throw new RuntimeException(\"%s is null or index bigger than %s size\");\n",fileName,fileName);
        pw.println("        }\n");
        pw.format("        this.%s.remove(index);\n",fileName);
        pw.println("        return this;\n");
        pw.println("    }");
    }


    private static void size(PrintWriter pw, Filed filed){
        String fileName=filed.getFiledName();
        pw.format("     public int size_%s(){\n",fileName);
        pw.format("         if(this.%s==null){\n",fileName);
        pw.format("             throw new RuntimeException(\"%s is null\");\n",fileName);
        pw.println("        }\n");
        pw.format("        return this.%s.size();\n",fileName);
        pw.println("    }");
    }

    private static void clear(PrintWriter pw, String  filedName,String className){
        pw.format("     public %s clear_%s(){\n",className,filedName);
        pw.format("         this.%s=null;\n",filedName);
        pw.format("         return this;\n");
        pw.println("     }");
    }

    private static void has(PrintWriter pw, String  filedName){
        pw.format("     public boolean has_%s(){\n",filedName);
        pw.format("         if(this.%s==null){\n",filedName);
        pw.format("             return false;\n");
        pw.format("         }\n");
        pw.format("         return this.%s.size()!=0;\n",filedName);
        pw.println("     }");
    }

}

package com.github.myincubator.fastproto.core_generator;

import com.github.myincubator.fastproto.wrapper.Filed;
import com.github.myincubator.fastproto.wrapper.Meta;
import com.github.myincubator.fastproto.wrapper.Object;
import org.apache.commons.text.CaseUtils;

import java.io.PrintWriter;
import java.util.Map;

public class MapBuildMethodGenerator {

    public static void generate(PrintWriter pw, Filed filed, Object object, Map<String, Meta> metaMap, String className){
        Filed key=null;
        Filed value=null;
        for(Filed filed1:object.getAllFiled()){
            if(filed1.getFiledName().equals("key")){
                key=filed1;
            }else {
                value=filed1;
            }
        }

        pw.println();
        put(pw, filed.getFiledName(),metaMap,key,value,className);
        pw.println();
        get(pw,filed.getFiledName(),metaMap,key,value);
        pw.println();
        getKeySet(pw,filed.getFiledName(),metaMap,key);
        pw.println();
        remove(pw,filed.getFiledName(),metaMap,key);
        pw.println();
        clear(pw,filed.getFiledName(),className);
        pw.println();
        has(pw,filed.getFiledName());


    }

    public static void put(PrintWriter pw, String fileName,  Map<String, Meta> metaMap, Filed key,Filed value,String className){
        pw.format("     public %s put%s(%s key,%s value){\n",className,CaseUtils.toCamelCase(fileName,true), Util.getJavaType(key,metaMap), Util.getJavaType(value,metaMap));
        pw.format("         if(key==null||value==null){");
        pw.format("             throw new RuntimeException(\"key or value is null\");");
        pw.format("         }");
        pw.format("         if(this.%s==null){\n",fileName);
        pw.format("             this.%s=new java.util.HashMap<>();\n",fileName);
        pw.println("        }\n");
        pw.format("         this.%s.put(key,value);\n",fileName);
        pw.println("        return this;\n");
        pw.println("    }");
    }

    public static void get(PrintWriter pw, String fileName,  Map<String, Meta> metaMap, Filed key,Filed value){
        pw.format("     public %s get%s(%s key){\n", Util.getJavaType(value,metaMap),CaseUtils.toCamelCase(fileName,true), Util.getJavaType(key,metaMap));
        pw.format("         if(this.%s==null){\n",fileName);
        pw.format("             throw new RuntimeException(\"%s is null\");\n",fileName);
        pw.println("        }\n");
        pw.format("         return this.%s.get(key);\n",fileName);
        pw.println("    }");
    }

    public static void getKeySet(PrintWriter pw, String fileName,  Map<String, Meta> metaMap, Filed key){
        pw.format("     public java.util.Set<%s> get%s(){\n", Util.getJavaType(key,metaMap),CaseUtils.toCamelCase(fileName,true));
        pw.format("         if(this.%s==null){\n",fileName);
        pw.format("             throw new RuntimeException(\"%s is null\");\n",fileName);
        pw.println("        }\n");
        pw.format("         return this.%s.keySet();\n",fileName);
        pw.println("    }");
    }

    public static void remove(PrintWriter pw, String fileName,  Map<String, Meta> metaMap, Filed key){
        pw.format("     public void remove%s(%s key){\n", CaseUtils.toCamelCase(fileName,true),Util.getJavaType(key,metaMap));
        pw.format("         if(this.%s==null){\n",fileName);
        pw.format("             throw new RuntimeException(\"%s is null\");\n",fileName);
        pw.println("        }\n");
        pw.format("         this.%s.remove(key);\n",fileName);
        pw.println("    }\n");
    }

    public static void clear(PrintWriter pw, String fileName,  String className){
        pw.format("     public %s clear%s(){\n",className,CaseUtils.toCamelCase(fileName,true));
        pw.format("         this.%s=null;\n",fileName);
        pw.format("         return this;\n");
        pw.println("    }");
    }

    public static void has(PrintWriter pw, String fileName){
        pw.format("     public boolean has%s(){\n",CaseUtils.toCamelCase(fileName,true));
        pw.format("         if(this.%s==null){\n",fileName);
        pw.format("             return false;\n");
        pw.format("         }");
        pw.format("         return this.%s.keySet().size()!=0;\n",fileName);
        pw.println("     }");
    }
}

package com.github.myincubator.fastproto.core_generator;

import com.github.myincubator.fastproto.wrapper.Filed;

import com.github.myincubator.fastproto.wrapper.Object;


import java.io.*;

import java.util.List;


public class EnumEntryGenerator {



    public static File generate(Object object, String javaOut, String pack) throws IOException {
        StringWriter sw=new StringWriter();
        PrintWriter pw=new PrintWriter(sw);
        if(pack!=null&&!pack.equals("")) {
            pw.format("package %s;\n",pack);
        }
        pw.println("public enum "+object.getName()+"{");
        pw.println();
        EnumFiled(pw,object.getAllFiled(),object.getName());

        pw.println("    int num;");
        pw.println();
        pw.println("    "+ object.getName()+"( int num ){");
        pw.println("        this.num = num;");
        pw.println("    }");
        pw.println();
        pw.println("    int getNum"+"( ){");
        pw.println("        return this.num;");
        pw.println("    }");
        pw.println("}");
        pw.println();
        pw.println();
        pw.println();

        File file=Util.genFile(javaOut,pack, object.getName());
        Util.WriterContent(file,sw);
        return file;
    }

    private static void EnumFiled(PrintWriter pw, List<Filed> list, String className){
        StringBuilder s=new StringBuilder(list.size()*40);
        s.append("  public static ").append(className).append(" get(int tag){");
        for(Filed filed: list){
            pw.println("    "+filed.getFiledName()+" ( "+filed.getNum()+" ),");
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

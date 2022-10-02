package com.github.myincubator.fastproto.core_generator;

import com.github.myincubator.fastproto.wrapper.Filed;
import com.github.myincubator.fastproto.wrapper.Message;

import java.io.File;
import java.io.PrintWriter;
import java.util.List;

public class NestedEnumGenerator {

    Message message;

    public NestedEnumGenerator(Message message) {
        this.message = message;
    }

    public File generate(PrintWriter pw) {
        pw.println("public static enum " + message.getName() + "{");
        pw.println();
        generateEnumFiled(pw, message.getAllFiled(), message.getName());

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

        return null;
    }

    private void generateEnumFiled(PrintWriter pw, List<Filed> list, String className) {
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

package com.github.openio.fastproto.core_generator;

import com.github.openio.fastproto.wrapper.Filed;
import com.github.openio.fastproto.wrapper.Message;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;


public class EnumEntryGenerator {


    private final Message message;

    private final String outDir;

    private final String Package;

    public EnumEntryGenerator(Message message, String outDir, String aPackage) {
        this.message = message;
        this.outDir = outDir;
        Package = aPackage;
    }

    public File generate() throws IOException {
        Message message = this.message;
        String javaOut = this.outDir;
        String pack = this.Package;
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        if (pack != null && !pack.equals("")) {
            pw.format("package %s;\n", pack);
        }
        pw.println("public enum " + message.getName() + "{");
        pw.println();
        EnumFiled(pw, message.getAllFiled(), message.getName());

        pw.println("    int num;");
        pw.println();
        pw.println("    " + message.getName() + "( int num ){");
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

        File file = Util.genFile(javaOut, pack, message.getName());
        Util.WriterContent(file,sw);
        return file;
    }

    private void EnumFiled(PrintWriter pw, List<Filed> list, String className) {
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

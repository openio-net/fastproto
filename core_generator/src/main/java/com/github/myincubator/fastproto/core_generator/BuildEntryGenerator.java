package com.github.myincubator.fastproto.core_generator;

import com.github.myincubator.fastproto.wrapper.Filed;
import com.github.myincubator.fastproto.wrapper.FiledLabel;
import com.github.myincubator.fastproto.wrapper.Message;
import com.github.myincubator.fastproto.wrapper.Meta;
import org.apache.commons.text.CaseUtils;

import java.io.PrintWriter;
import java.util.*;

public class BuildEntryGenerator {

    private final Message message;

    public BuildEntryGenerator(Message message) {
        this.message = message;
    }

    /**
     * @param map     ：Stored map information
     * @param metaMap ：Message object information
     * @return
     */
    public void generate(PrintWriter pw, Map<String, Message> map, Map<String, Meta> metaMap) {
        Message o = this.message;
        String buildName = o.getName() + "Build";

        pw.format("     public static class %s {\n", buildName);//类声明


        Map<Integer, List<Filed>> oneOf = new HashMap<>();//同一个OneOf结构存储到一个List中




        o.getAllFiled().forEach(//除oneof以为的进行代码生成
                f-> filedGenerate(pw, f, map, metaMap, oneOf)
        );


        oneOf.keySet().forEach(//生成OneOf声明
                index -> new OneOfBuildFiledGenerator(oneOf.get(index)).generate(pw, metaMap)
        );

        o.getAllFiled().forEach(//除oneof以为的进行代码生成
                f-> methodGenerate(pw, f, map, metaMap, buildName)
        );


        oneOf.keySet().forEach(//生成OneOf声明
                index -> new OneOfBuildMethodGenerator(oneOf.get(index), buildName).generate(pw, metaMap)
        );

        pw.println();
        generateBuild(pw, o.getAllFiled(), o.getName(), map, metaMap);

        generateClear(pw, o.getAllFiled(), buildName);

        pw.println();
        pw.format("     private %s(){\n",buildName);
        pw.println("    }");


        pw.println("    }");



    }


    private void filedGenerate(PrintWriter pw, Filed filed, Map<String, Message> map, Map<String, Meta> metaMap, Map<Integer, List<Filed>> oneOf) {

        Message mapMessage = map.get(filed.getFileTypeName());
        String label = filed.getFiledLabel().getLabel();
        if (filed.getHasOneOf()) {
            int oneOfIndex = filed.getOneIndex();
            List<Filed> filedList = oneOf.get(oneOfIndex);
            if (filedList == null) {
                List<Filed> list = new ArrayList<>();
                list.add(filed);
                oneOf.put(oneOfIndex, list);
            } else {
                filedList.add(filed);
            }
        } else if (mapMessage != null) {
            new MapBuildFileGenerator(filed, mapMessage).generate(pw, metaMap);
        } else if (label.equals(FiledLabel.Repeated.getLabel())) {
            new RepeatedBuildFileGenerator(filed).generate(pw, metaMap);
        } else {
            new MessageBuildFileGenerator(filed).generate(pw, metaMap);
        }

    }

    private void methodGenerate(PrintWriter pw, Filed filed, Map<String, Message> map, Map<String, Meta> metaMap, String className) {
        Message mapMessage = map.get(filed.getFileTypeName());
        String label = filed.getFiledLabel().getLabel();
        if (filed.getHasOneOf()) {
            return;
        } else if (mapMessage != null) {
            new MapBuildMethodGenerator(filed, mapMessage, className).generate(pw, metaMap);
        } else if (label.equals(FiledLabel.Repeated.getLabel())) {
            new RepeatedBuildMethodGenerator(filed, className).generate(pw, metaMap, className);
        } else {
            new MessageBuildMethodGenerator(filed, className).generate(pw, metaMap);
        }
    }


    private void generateBuild(PrintWriter pw, List<Filed> filed1, String className, Map<String, Message> map, Map<String, Meta> metaMap) {
        pw.format("     public %s build(){\n", className);
        pw.format("         %s value_1=new %s();\n", className, className);
        for (Filed filed : filed1) {
            String filedName = filed.getFiledName();
            if (filed.getHasOneOf()) {//oneOf
                int oneOf = filed.getOneIndex();
                pw.format("         if(this.endSet%dNum==%d){\n", oneOf, filed.getNum());
                pw.format("             value_1.setOneOf%d_%s(this.%s);\n", oneOf, filedName, filedName);
                pw.format("         }\n");
            } else if (filed.getFiledLabel().getLabel().equals(FiledLabel.Repeated.getLabel())) {//repeated
                pw.format("         if(this.has%s()){\n", CaseUtils.toCamelCase(filedName,true));
                pw.format("             value_1.set_%s(this.%s);\n", filedName, filedName);
                pw.format("         }\n");
            } else if (filed.getFiledLabel().getLabel().equals(FiledLabel.Required.getLabel())) {//required
                pw.format("         if(this.%s==null){\n", filedName);
                pw.format("             throw new RuntimeException(\" %s is required\");\n", filedName);
                pw.format("         }\n");
                pw.format("             value_1.set_%s(this.%s);\n", filedName, filedName);
            } else if (map.get(filed.getFileTypeName()) != null) {//map
                pw.format("     if(has%s){\n", CaseUtils.toCamelCase(filedName,true));
                pw.format("             value_1.set_%s(this.%s);\n", filedName, filedName);pw.format("");
                pw.format("     }\n");
            } else {//option
                pw.format("         if(this.%s!=null){\n", filedName);
                pw.format("             value_1.set_%s(this.%s);\n", filedName, filedName);
                pw.format("         }\n");
            }
        }
        pw.format("         return value_1;\n");
        pw.format("     }\n");
    }

    private void generateClear(PrintWriter pw, List<Filed> filed1, String buildName) {
        pw.format("     public %s clear(){\n", buildName);
        Set<Integer> set = new HashSet<>();
        for (Filed filed : filed1) {

            String filedName = filed.getFiledName();

            if (filed.getHasOneOf()) {//oneOf
                int oneOf = filed.getOneIndex();
                if (set.contains(oneOf)) {
                    continue;
                }
                set.add(oneOf);
                pw.format("         endSet%dNum=-1;",oneOf);
            }
            pw.format("         this.%s=null;\n", filed.getFiledName());
        }
        pw.format("         return this;\n");
        pw.format("     }\n");
    }
}

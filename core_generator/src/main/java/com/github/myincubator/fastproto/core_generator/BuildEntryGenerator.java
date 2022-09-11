package com.github.myincubator.fastproto.core_generator;

import com.github.myincubator.fastproto.wrapper.Filed;
import com.github.myincubator.fastproto.wrapper.FiledLabel;
import com.github.myincubator.fastproto.wrapper.Meta;
import com.github.myincubator.fastproto.wrapper.Object;

import java.io.PrintWriter;
import java.util.*;

public class BuildEntryGenerator {


    /**
     *
     * @param o :需要生成的Object
     * @param map   ：存储了map信息的
     * @param metaMap ：
     * @return
     */
    public static void generate(PrintWriter pw, Object o, Map<String, Object> map, Map<String, Meta> metaMap) {

        String buildName=o.getName()+"Build";

        pw.format("     public static class %s {\n",buildName);//类声明


        Map<Integer, List<Filed>> oneOf=new HashMap<>();//同一个OneOf结构存储到一个List中




        o.getAllFiled().forEach(//除oneof以为的进行代码生成
                f-> filedGen(pw,f,map,metaMap,oneOf)
        );


        oneOf.keySet().forEach(//生成OneOf声明
                index-> OneOfBuildFiledGenerator.generate(pw,oneOf.get(index),map,metaMap)
        );

        o.getAllFiled().forEach(//除oneof以为的进行代码生成
                f-> methodGen(pw,f,map,metaMap,buildName)
        );


        oneOf.keySet().forEach(//生成OneOf声明
                index-> OneOfBuildMethodGenerator.generate(pw,oneOf.get(index),metaMap,buildName)
        );

        pw.println();
        build(pw,o.getAllFiled(),o.getName(),map,metaMap);

        clear(pw,o.getAllFiled(),buildName);

        pw.println();
        pw.format("     private %s(){\n",buildName);
        pw.println("    }");


        pw.println("    }");



    }


    private static void filedGen(PrintWriter pw, Filed filed, Map<String, Object> map, Map<String, Meta> metaMap, Map<Integer,List<Filed>> oneOf){

        Object mapObject=map.get(filed.getFileTypeName());
        String label=filed.getFiledLabel().getLabel();
        if(filed.getHasOneOf()){                                //判断是否为Onof里的属性
            int oneOfIndex= filed.getOneIndex();
            List<Filed> filedList=oneOf.get(oneOfIndex);
            if(filedList==null){            //判断Map表中是否有对应的存储结构
                List<Filed> list=new ArrayList<>();
                list.add(filed);
                oneOf.put(oneOfIndex,list);
            }else {
                filedList.add(filed);
            }
        }else if(mapObject!=null){                             //判断是否是Map类型
            MapBuildFileGenerator.generate(pw,filed,metaMap,mapObject);
        }else if(label.equals(FiledLabel.Repeated.getLabel())){//判断标签是否为可多次出现的
            RepeatedBuildFileGenerator.generate(pw,filed,map,metaMap);
        }else {
            MessageBuildFileGenerator.generate(pw,filed,map,metaMap);//repeated不进行操作
        }

    }

    private static void methodGen(PrintWriter pw, Filed filed, Map<String, Object> map, Map<String, Meta> metaMap, String className){
        Object mapObject=map.get(filed.getFileTypeName());
        String label=filed.getFiledLabel().getLabel();
        if(filed.getHasOneOf()){                                //判断是否为Onof里的属性
            return ;
        }else if(mapObject!=null){                             //判断是否是Map类型
            MapBuildMethodGenerator.generate(pw,filed,mapObject,metaMap,className);
        }else if(label.equals(FiledLabel.Repeated.getLabel())){//判断标签是否为可多次出现的
            RepeatedBuildMethodGenerator.generate(pw,filed,metaMap,className);
        }else {
            MessageBuildMethodGenerator.generate(pw,filed,metaMap,className);//普通进行操作
        }
    }


    private static void build(PrintWriter pw, List<Filed> filed1, String className, Map<String, Object> map, Map<String,Meta> metaMap) {
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
                pw.format("         if(this.has_%s()){\n", filedName);
                pw.format("             value_1.set_%s(this.%s);\n", filedName, filedName);
                pw.format("         }\n");
            } else if (filed.getFiledLabel().getLabel().equals(FiledLabel.Required.getLabel())) {//required
                pw.format("         if(this.%s==null){\n", filedName);
                pw.format("             throw new RuntimeException(\" %s is required\");\n", filedName);
                pw.format("         }\n");
                pw.format("             value_1.set_%s(this.%s);\n", filedName, filedName);
            } else if (map.get(filed.getFileTypeName()) != null) {//map
                pw.format("     if(has_%s){\n",filedName);
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

    private static void clear(PrintWriter pw,List<Filed> filed1,String buildName) {
        pw.format("     public %s clear(){\n", buildName);
        Set<Integer> set=new HashSet<>();
        for (Filed filed : filed1) {

            String filedName = filed.getFiledName();

            if (filed.getHasOneOf()) {//oneOf
                int oneOf = filed.getOneIndex();
                if(set.contains(oneOf)){
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

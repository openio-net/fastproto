package com.github.myincubator.fastproto.core_generator;

import com.github.myincubator.fastproto.wrapper.Filed;
import com.github.myincubator.fastproto.wrapper.Meta;
import com.github.myincubator.fastproto.wrapper.Object;

import java.io.PrintWriter;
import java.util.Map;

public class MapBuildFileGenerator {

    public static void generate(PrintWriter pw, Filed filed, Map<String, Meta> metaMap, Object o){

        Filed key=null;
        Filed value=null;
        for(Filed filed1:o.getAllFiled()){
            if(filed1.getFiledName().equals("key")){
                key=filed1;
            }else {
                value=filed1;
            }
        }

        pw.println();
        pw.format("    private java.util.Map<%s,%s> %s;\n", Util.getJavaType(key,metaMap), Util.getJavaType(value,metaMap),filed.getFiledName());

        pw.println();
    }





}

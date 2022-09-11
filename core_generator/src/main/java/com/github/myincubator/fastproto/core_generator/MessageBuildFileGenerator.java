package com.github.myincubator.fastproto.core_generator;

import com.github.myincubator.fastproto.wrapper.Filed;
import com.github.myincubator.fastproto.wrapper.Meta;
import com.github.myincubator.fastproto.wrapper.Object;

import java.io.PrintWriter;
import java.util.Map;

public class MessageBuildFileGenerator {


    public static void generate(PrintWriter pw, Filed filed, Map<String, Object> mapSet, Map<String, Meta> metaMap){

        pw.format("    private %s %s;\n", Util.getJavaType(filed,metaMap),filed.getFiledName());

    }
}

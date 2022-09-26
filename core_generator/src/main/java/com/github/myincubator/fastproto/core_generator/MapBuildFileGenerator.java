package com.github.myincubator.fastproto.core_generator;

import com.github.myincubator.fastproto.wrapper.Filed;
import com.github.myincubator.fastproto.wrapper.Message;
import com.github.myincubator.fastproto.wrapper.Meta;

import java.io.PrintWriter;
import java.util.Map;

public class MapBuildFileGenerator {

    Filed filed;

    Message message;

    public MapBuildFileGenerator(Filed filed, Message message) {
        this.filed = filed;
        this.message = message;
    }

    public void generate(PrintWriter pw, Map<String, Meta> metaMap) {

        Filed key = null;
        Filed value = null;
        for (Filed filed1 : message.getAllFiled()) {
            if (filed1.getFiledName().equals("key")) {
                key = filed1;
            } else {
                value = filed1;
            }
        }

        pw.println();
        pw.format("    private java.util.Map<%s,%s> %s;\n", Util.getJavaType(key,metaMap), Util.getJavaType(value,metaMap),filed.getFiledName());

        pw.println();
    }





}

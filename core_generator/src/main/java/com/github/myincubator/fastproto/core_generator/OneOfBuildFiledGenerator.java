package com.github.myincubator.fastproto.core_generator;

import com.github.myincubator.fastproto.wrapper.Filed;
import com.github.myincubator.fastproto.wrapper.Meta;

import com.github.myincubator.fastproto.wrapper.Object;

import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

public class OneOfBuildFiledGenerator {

    public static void generate(PrintWriter pw, List<Filed> filed, Map<String, Object> map, Map<String, Meta> metaMap){
        int oneOfIndex=filed.get(0).getOneIndex();

        pw.format("private final static int oneOfIndex%d=%d;\n\n",oneOfIndex,oneOfIndex);
        pw.format("//OneOfIndex为%d的最后设置的值的Num\n",oneOfIndex);
        pw.format("private  int endSet%dNum=-1;\n",oneOfIndex);

        for(Filed filed1:filed){
            filedGen(pw,filed1,metaMap);
        }
    }


    private static void filedGen(PrintWriter pw,Filed filed,Map<String,Meta> metaMap){

        pw.format("    private %s %s;\n", Util.getJavaType(filed, metaMap), filed.getFiledName());

        pw.println();
    }


}

package com.github.myincubator.fastproto.core_generator;

import com.github.myincubator.fastproto.wrapper.Filed;
import com.github.myincubator.fastproto.wrapper.Meta;

import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

public class OneOfBuildFiledGenerator {

    List<Filed> filed;

    public OneOfBuildFiledGenerator(List<Filed> filed) {
        this.filed = filed;
    }

    public void generate(PrintWriter pw, Map<String, Meta> metaMap) {
        int oneOfIndex = filed.get(0).getOneIndex();

        pw.format("private final static int oneOfIndex%d=%d;\n\n", oneOfIndex, oneOfIndex);
        pw.format("//OneOfIndex is %d last value\n", oneOfIndex);
        pw.format("private  int endSet%dNum=-1;\n", oneOfIndex);

        for (Filed filed1 : filed) {
            filedGenerate(pw, filed1, metaMap);
        }
    }


    private void filedGenerate(PrintWriter pw, Filed filed, Map<String, Meta> metaMap) {

        pw.format("    private %s %s;\n", Util.getJavaType(filed, metaMap), filed.getFiledName());

        pw.println();
    }


}

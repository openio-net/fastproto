/**
 * Licensed to the OpenIO.Net under one or more
 * contributor license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.openio.fastproto.core.generator;

import net.openio.fastproto.wrapper.Filed;
import net.openio.fastproto.wrapper.Meta;

import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

/**
 * The OneOfBuildFiledGenerator class is responsible for generating code for the fields within a oneof group in the builder class.
 */
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

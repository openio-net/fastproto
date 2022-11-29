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
package net.openio.fastproto.core_generator;

import net.openio.fastproto.wrapper.Filed;
import net.openio.fastproto.wrapper.Message;
import net.openio.fastproto.wrapper.Meta;

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

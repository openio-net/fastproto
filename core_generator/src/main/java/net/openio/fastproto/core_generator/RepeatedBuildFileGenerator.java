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
import net.openio.fastproto.wrapper.Meta;

import java.io.PrintWriter;
import java.util.Map;

/**
 * The RepeatedBuildFileGenerator class is responsible for generating code for a repeated field in the message class.
 */
public class RepeatedBuildFileGenerator {

    Filed filed;


    public RepeatedBuildFileGenerator(Filed filed) {
        this.filed = filed;
    }

    public void generate(PrintWriter pw, Map<String, Meta> metaMap) {

        pw.format("    private java.util.List<%s> %s;\n", Util.getJavaType(filed, metaMap), filed.getFiledName());


    }
}

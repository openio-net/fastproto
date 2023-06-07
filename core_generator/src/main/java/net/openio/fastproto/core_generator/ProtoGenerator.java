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

import net.openio.fastproto.compile.Parser;
import net.openio.fastproto.config.Config;
import net.openio.fastproto.wrapper.Message;
import net.openio.fastproto.wrapper.Meta;
import net.openio.fastproto.wrapper.ObjectType;
import net.openio.fastproto.wrapper.Package;
import com.google.common.base.Joiner;
import org.jboss.forge.roaster.Roaster;
import org.jboss.forge.roaster.model.source.JavaClassSource;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * Generate java file
 *
 */
public class ProtoGenerator {

    /**
     *
     * @param config
     *
     * @return
     * @throws IOException
     */
        public static List<File> generate(Config config) throws IOException, InterruptedException {
            Parser parse = new Parser();
            parse.parse(config);
            List<File> files = new ArrayList<>();
            for (Package pack:parse.getList()) {
                generate(pack, config.getJavaOut(), parse.maps, parse.metas, files);
            }
            return files;
        }


    private static void generate(Package pack, String outDir, Map<String, Message> map, Map<String, Meta> metaMap, List<File> list) throws IOException {
        Set<String> javaPack = new HashSet<>();
        for (Message o : pack.getAllObject()) {
            javaPack.add(pack.getJavaPackName());
            if (o.getObjectType().getType().equals(ObjectType.Message.getType())) {
                list.add(new MessageEntryGenerator(o, outDir, pack.getJavaPackName()).generate(map, metaMap));
            } else if (o.getObjectType().getType().equals(ObjectType.Enum.getType())) {
                list.add(new EnumEntryGenerator(o, outDir, pack.getJavaPackName()).generate());
            }
        }
        for (String javaPackage : javaPack) {
            String javaDir = Joiner.on('/').join(javaPackage.split("\\."));
            File file1 = new File(outDir + javaDir + "/" + "Serializer.java");
            if (file1.exists()) {
                file1.delete();
            }

            try (InputStream is = Util.class.getResourceAsStream("/net/openio/fastproto/Serializer.java")) {
                JavaClassSource codecClass = (JavaClassSource) Roaster.parse(is);
                if (javaPackage.equals("")) {
                    codecClass.setDefaultPackage();
                } else {
                    codecClass.setPackage(javaPackage);
                }
                File file = Util.genFile(outDir, javaPackage, "Serializer");
                Util.writerContent(file, codecClass.toString());
                list.add(file);
            }
        }
    }
}

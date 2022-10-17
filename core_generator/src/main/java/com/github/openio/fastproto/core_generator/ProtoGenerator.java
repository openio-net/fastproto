package com.github.openio.fastproto.core_generator;

import com.github.openio.fastproto.compile.Parser;
import com.github.openio.fastproto.config.Config;
import com.github.openio.fastproto.wrapper.Message;
import com.github.openio.fastproto.wrapper.Meta;
import com.github.openio.fastproto.wrapper.ObjectType;
import com.github.openio.fastproto.wrapper.Package;
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
     * @return
     * @throws IOException
     */
        public static List<File> generate(Config config) throws IOException, InterruptedException {
            Parser parse = new Parser();
            parse.parse(config);
            List<File> files = new ArrayList<>();
            for(Package pack:parse.getList()){
                generate(pack, config.getJavaOut(), parse.maps,parse.metas,files);
            }
            return files;
        }


    private static void generate(Package pack, String out_dir, Map<String, Message> map, Map<String, Meta> metaMap, List<File> list) throws IOException {
        Set<String> javaPack = new HashSet<>();
        for (Message o : pack.getAllObject()) {
            javaPack.add(pack.getJavaPackName());
            if (o.getObjectType().getType().equals(ObjectType.Message.getType())) {
                list.add(new MessageEntryGenerator(o, out_dir, pack.getJavaPackName()).generate(map, metaMap));
            } else if (o.getObjectType().getType().equals(ObjectType.Enum.getType())) {
                list.add(new EnumEntryGenerator(o, out_dir, pack.getJavaPackName()).generate());
            }
        }
        for (String javaPackage : javaPack) {
            String javaDir = Joiner.on('/').join(javaPackage.split("\\."));
            File file1 = new File(out_dir + javaDir + "/" + "Serializer.java");
            if(file1.exists()){
                file1.delete();
            }

            try (InputStream is =Util.class.getResourceAsStream("/com/github/myincubator/fastproto/Serializer.java")) {
                JavaClassSource codecClass = (JavaClassSource) Roaster.parse(is);
                if(javaPackage.equals("")){
                    codecClass.setDefaultPackage();
                }else {
                    codecClass.setPackage(javaPackage);
                }
                File file=Util.genFile(out_dir,javaPackage,"Serializer");
                Util.WriterContent(file,codecClass.toString());
                list.add(file);
            }
        }
    }
}

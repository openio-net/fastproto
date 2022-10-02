
import com.github.myincubator.fastproto.config.Config;
import com.github.myincubator.fastproto.core_generator.ProtoGenerator;

import java.io.IOException;

public class Test {


    public static void main(String[] args) {
        Config config=new Config();
        config.setJavaOut("D:\\data\\git\\fastproto\\fastproto_tests\\src\\test\\java");
        config.setFileDir("D:\\data\\git\\fastproto\\fastproto_tests\\src\\main\\proto");
        config.addProtoFiles("x.proto");
        try {
            ProtoGenerator.generate(config);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}

package com.fastproto;

import com.fastproto.compile.Generate;
import com.fastproto.compile.Parse;
import com.fastproto.config.Config;

public class Boot {

    public static void boot(Config config) throws Exception {
        Parse parse=new Parse();
        parse.parse(config);
        Generate generate=new Generate();
        generate.Generate(parse.getList(),config);
    }
}

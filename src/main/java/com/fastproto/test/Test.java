package com.fastproto.test;

import com.fastproto.compile.Generate;
import com.fastproto.compile.Parse;
import com.fastproto.config.Config;

public class Test {
    public static void main(String[] args) throws Exception {
        Config config=new Config();
        config.setFileDir("D:\\asd\\");
        config.addProtoFiles("a.proto");
        Parse parse=new Parse();
        parse.parse(config);
        Generate generate=new Generate();
        generate.Generate(Parse.getList(),config);

    }
}

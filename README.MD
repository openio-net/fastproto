# Fast Proto
Fast Proto Project is based on JDK 8

## Features
1. generate the fastest possible Java code for the Protobuf protocols
2. 100% compatible with proto2 and proto3 definitions and wire protocols.
3. Zero-copy deserialization using Netty ByteBuf
4. Deserialize from direct memory
5. Zero heap allocations in serialization / deserialization
6. Java-based code generator with Maven plugin 

## Benchmark
```shell
java -jar benchmark/target/benchmarks.jar

ProtoBenchmark.fastProtoDeserialize              thrpt    3  339.536 ± 65.308  ops/us
ProtoBenchmark.fastProtoSerialize                thrpt    3    2.558 ±  0.427  ops/us
ProtoBenchmark.lightProtoDeserialize             thrpt    3  244.918 ± 28.661  ops/us
ProtoBenchmark.lightProtoSerialize               thrpt    3    4.391 ±  0.199  ops/us
ProtoBenchmark.protobufDeserialize               thrpt    3   77.450 ± 17.250  ops/us
ProtoBenchmark.protobufSerialize                 thrpt    3    2.375 ±  0.169  ops/us
SimpleBenchmark.fastProtoDeserialize             thrpt    3   29.111 ±  0.965  ops/us
SimpleBenchmark.fastProtoSerialize               thrpt    3  105.157 ±  1.588  ops/us
SimpleBenchmark.lightProtoDeserializeReadString  thrpt    3   28.141 ±  6.730  ops/us
SimpleBenchmark.lightProtoSerialize              thrpt    3  118.226 ±  7.571  ops/us
SimpleBenchmark.protobufDeserialize              thrpt    3   18.338 ±  1.289  ops/us
SimpleBenchmark.protobufSerialize                thrpt    3   22.438 ±  1.047  ops/us
StringEncodingBenchmark.jdkEncoding              thrpt   10   19.163 ±  0.130  ops/us
StringEncodingBenchmark.jdkEncodingAscii         thrpt   10   84.910 ±  1.776  ops/us
StringEncodingBenchmark.nettyEncoding            thrpt   10   26.972 ± 13.365  ops/us
StringEncodingBenchmark.nettyEncodingAscii       thrpt   10   31.964 ± 13.861  ops/us
```

## How to use
### Using java libraries

1. importing maven libraries
```yaml
        <dependency>
          <artifactId>fastproto</artifactId>
          <groupId>net.openio.fastproto</groupId>
          <artifactId>core-generator</artifactId>
          <version>1.0-SNAPSHOT</version>
        </dependency>
```


2. Enter the directory to be scanned, and the directory of the java generated code 

 (note that the directory of the generated code needs to be the same as the package name, and the generated code will not be loaded by the class loader)
```
        List<String> protoFile =new ArrayList();
        protoFile.add("google/protobuf/any.proto");
        Config config = new Config();
        config.setJavaOut("src/test/java");
        config.setFileDir("src/main/java/net/openio/fastproto");
        config.addProtoFiles(protoFile);
        try {
            ProtoGenerator.generate(config);
        } catch (Exception e) {
            //............
        }
```

### Using the maven plugin
The generated code is loaded by the class loader.
```yaml
            <plugin>
                <groupId>fastproto</groupId>
                <artifactId>fastproto-maven-plugin</artifactId>
                <version>1.0-SNAPSHOT</version>
                <configuration>
                    <javaOutDir>src/test/java</javaOutDir>
                    <sourcesDir>src/main/java/net/openio/fastproto</sourcesDir>
                    <protoFile>
                        <file>google/protobuf/any.proto</file>
                    </protoFile>
                </configuration>
                <executions>
                    <execution>
                        <goals>
                            <goal>generate</goal>
                        </goals>
                    </execution>
                </executions>
            </plugin>
```
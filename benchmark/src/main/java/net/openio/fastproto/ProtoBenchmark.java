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
package net.openio.fastproto;


import net.openio.fastproto.tests.*;
import com.google.protobuf.CodedOutputStream;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.buffer.Unpooled;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.util.concurrent.TimeUnit;

//Test class from lightproto :
// https://github.com/splunk/lightproto/blob/master/benchmark/src/main/java/com/github/splunk/lightproto/benchmark/StringEncodingBenchmark.java


@State(Scope.Benchmark)
@Warmup(iterations = 3)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@Measurement(iterations = 3)
@Fork(value = 1)
public class ProtoBenchmark {

    final static ByteBuf serialized;

    static {
        AddressBook.AddressBookBuild ab = AddressBook.newBuilder();
        Person.PersonBuild p1 = Person.newBuilder();
        p1.setName("name");
        p1.setEmail("name@example.com");
        p1.setId(5);
        Person.PhoneNumber.PhoneNumberBuild p1_pn1 = Person.PhoneNumber.newBuilder();
        p1_pn1.setNumber("xxx-zzz-yyyyy");
        p1_pn1.setType(Person.PhoneType.HOME);

        Person.PhoneNumber.PhoneNumberBuild p1_pn2 = Person.PhoneNumber.newBuilder();
        p1_pn2.setNumber("xxx-zzz-yyyyy");
        p1_pn2.setType(Person.PhoneType.MOBILE);

        Person.PersonBuild p2 = Person.newBuilder();
        p2.setName("name 2");
        p2.setEmail("name2@example.com");
        p2.setId(6);

        Person.PhoneNumber.PhoneNumberBuild p2_pn1 = Person.PhoneNumber.newBuilder();
        p2_pn1.setNumber("xxx-zzz-yyyyy");
        p2_pn1.setType(Person.PhoneType.HOME);


        AddressBook ab1=ab.build();
        serialized = Unpooled.buffer(ab1.getByteSize());
        serialized.clear();
        ab1.encode(serialized);
    }


    private final ByteBuf buffer = PooledByteBufAllocator.DEFAULT.buffer(1<<10);
    byte[] data = new byte[1<<10];
    private final ByteBuf serializeByteBuf = Unpooled.wrappedBuffer(serialized);

    @Benchmark
    public void protobufSerialize(Blackhole bh) throws Exception {
        AddressBookProtos.AddressBook.Builder pbab = AddressBookProtos.AddressBook.newBuilder();
        AddressBookProtos.Person.Builder pb_p1 = AddressBookProtos.Person.newBuilder();
        pb_p1.setName("name 1");
        pb_p1.setEmail("name1@example.com");
        pb_p1.setId(5);
        AddressBookProtos.Person.PhoneNumber.Builder pb1_pn1 = AddressBookProtos.Person.PhoneNumber.newBuilder();
        pb1_pn1.setNumber("xxx-zzz-1111");
        pb1_pn1.setType(AddressBookProtos.Person.PhoneType.HOME);

        AddressBookProtos.Person.PhoneNumber.Builder pb1_pn2 = AddressBookProtos.Person.PhoneNumber.newBuilder();
        pb1_pn2.setNumber("xxx-zzz-2222");
        pb1_pn2.setType(AddressBookProtos.Person.PhoneType.MOBILE);

        pb_p1.addPhone(pb1_pn1);
        pb_p1.addPhone(pb1_pn2);

        AddressBookProtos.Person.Builder pb_p2 = AddressBookProtos.Person.newBuilder();
        pb_p2.setName("name 2");
        pb_p2.setEmail("name2@example.com");
        pb_p2.setId(6);

        AddressBookProtos.Person.PhoneNumber.Builder pb2_pn1 = AddressBookProtos.Person.PhoneNumber.newBuilder();
        pb2_pn1.setNumber("xxx-zzz-2222");
        pb2_pn1.setType(AddressBookProtos.Person.PhoneType.HOME);

        pb_p2.addPhone(pb2_pn1);

        pbab.addPerson(pb_p1);
        pbab.addPerson(pb_p2);

        CodedOutputStream s = CodedOutputStream.newInstance(data);
        pbab.build().writeTo(s);

        bh.consume(pbab);
        bh.consume(s);
    }

    @Benchmark
    public void fastProtoSerialize(Blackhole bh) {
        AddressBook.AddressBookBuild pbab = AddressBook.newBuilder();
        Person.PersonBuild pb_p1 = Person.newBuilder();
        pb_p1.setName("name 1");
        pb_p1.setEmail("name1@example.com");
        pb_p1.setId(5);
        Person.PhoneNumber.PhoneNumberBuild pb1_pn1 = Person.PhoneNumber.newBuilder();
        pb1_pn1.setNumber("xxx-zzz-1111");
        pb1_pn1.setType(Person.PhoneType.HOME);

        Person.PhoneNumber.PhoneNumberBuild pb1_pn2 = Person.PhoneNumber.newBuilder();
        pb1_pn2.setNumber("xxx-zzz-2222");
        pb1_pn2.setType(Person.PhoneType.MOBILE);

        pb_p1.addPhone(pb1_pn1.build());
        pb_p1.addPhone(pb1_pn2.build());

        Person.PersonBuild pb_p2 = Person.newBuilder();
        pb_p2.setName("name 2");
        pb_p2.setEmail("name2@example.com");
        pb_p2.setId(6);

        Person.PhoneNumber.PhoneNumberBuild pb2_pn1 = Person.PhoneNumber.newBuilder();
        pb2_pn1.setNumber("xxx-zzz-2222");
        pb2_pn1.setType(Person.PhoneType.HOME);

        pb_p2.addPhone(pb2_pn1.build());

        pbab.addPerson(pb_p1.build());
        pbab.addPerson(pb_p2.build());


        pbab.build().encode(buffer);
        bh.consume(pbab);
        buffer.clear();
    }

    @Benchmark
    public void lightProtoSerialize(Blackhole bh) {
        com.github.splunk.lightproto.tests.AddressBook frame
            =new com.github.splunk.lightproto.tests.AddressBook();
        frame.clear();

        com.github.splunk.lightproto.tests.Person p1 = frame.addPerson();
        p1.setName("name");
        p1.setEmail("name@example.com");
        p1.setId(5);
        com.github.splunk.lightproto.tests.Person.PhoneNumber p1_pn1 = p1.addPhone();
        p1_pn1.setNumber("xxx-zzz-yyyyy");
        p1_pn1.setType(com.github.splunk.lightproto.tests.Person.PhoneType.HOME);

        com.github.splunk.lightproto.tests.Person.PhoneNumber p1_pn2 = p1.addPhone();
        p1_pn2.setNumber("xxx-zzz-yyyyy");
        p1_pn2.setType(com.github.splunk.lightproto.tests.Person.PhoneType.MOBILE);

        com.github.splunk.lightproto.tests.Person p2 = frame.addPerson();
        p2.setName("name 2");
        p2.setEmail("name2@example.com");
        p2.setId(6);

        com.github.splunk.lightproto.tests.Person.PhoneNumber p2_pn1 = p1.addPhone();
        p2_pn1.setNumber("xxx-zzz-yyyyy");
        p2_pn1.setType(com.github.splunk.lightproto.tests.Person.PhoneType.HOME);

        frame.writeTo(buffer);
        buffer.clear();

        bh.consume(frame);
    }

    @Benchmark
    public void protobufDeserialize(Blackhole bh) throws Exception {
        AddressBookProtos.AddressBook ab = AddressBookProtos.AddressBook.parseFrom(serializeByteBuf.array());
        bh.consume(ab);
        serializeByteBuf.resetReaderIndex();
    }

    @Benchmark
    public void fastProtoDeserialize(Blackhole bh) {
        AddressBook frame =AddressBook.decode(serializeByteBuf, serializeByteBuf.readableBytes());
        bh.consume(frame);
        serializeByteBuf.resetReaderIndex();
    }


    @Benchmark
    public void lightProtoDeserialize(Blackhole bh) {
        com.github.splunk.lightproto.tests.AddressBook frame =new com.github.splunk.lightproto.tests.AddressBook();
        frame.parseFrom(serializeByteBuf, serializeByteBuf.readableBytes());
        bh.consume(frame);
        serializeByteBuf.resetReaderIndex();
    }


}

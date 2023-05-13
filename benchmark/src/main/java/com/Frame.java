package com; /**
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
import com.google.protobuf.CodedOutputStream;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;

import java.nio.charset.StandardCharsets;

public final class Frame {

	private int Frame_size = 0;

	private String name;
	public final static int name_Num = 1;
	public final static int name_Tag = 10;// the value is num<<<3|wireType
	public final static int name_TagEncodeSize = 1;

	private void encode_name(ByteBuf buf) {
		Serializer.encodeVarInt32(buf, name_Tag);
		Serializer.encodeString(buf, this.name);
	}

	private static void decode_name(ByteBuf buf, Frame a_1) {
		String value_1 = null;
		value_1 = Serializer.decodeString(buf, Serializer.decodeVarInt32(buf));
		a_1.name = value_1;
	}

	private void set_name(String value_1) {
		Frame_size += name_TagEncodeSize;
		int size_1 = 0;

		size_1 += Serializer.computeVarInt32Size(ByteBufUtil.utf8Bytes(value_1));
		size_1 += ByteBufUtil.utf8Bytes(value_1);// value length
		Frame_size += size_1;
		this.name = value_1;
	}

	public String getName() {
		return this.name;
	}

	private boolean hasName() {
		return this.name != null;
	}

	private Point point;
	public final static int point_Num = 2;
	public final static int point_Tag = 18;// the value is num<<<3|wireType
	public final static int point_TagEncodeSize = 1;

	private void encode_point(ByteBuf buf) {
		Serializer.encodeVarInt32(buf, point_Tag);
		Serializer.encodeVarInt32(buf, this.point.getByteSize());
		this.point.encode(buf);
	}

	private static void decode_point(ByteBuf buf, Frame a_1) {
		Point value_1 = null;
		value_1 = Point.decode(buf, Serializer.decodeVarInt32(buf));
		a_1.point = value_1;
	}

	private void set_point(Point value_1) {
		Frame_size += point_TagEncodeSize;
		int size_1 = 0;

		size_1 += Serializer.computeVarInt32Size(value_1.getByteSize());
		size_1 += value_1.getByteSize();
		Frame_size += size_1;
		this.point = value_1;
	}

	public Point getPoint() {
		return this.point;
	}

	private boolean hasPoint() {
		return this.point != null;
	}

	public static Frame decode(ByteBuf buf) {
		Frame value_1 = new Frame();
		int f_Index = buf.readerIndex();
		while (buf.readerIndex() < buf.writerIndex()) {
			int num_1 = Serializer.decodeVarInt32(buf);
			switch (num_1) {
				case name_Tag :
					decode_name(buf, value_1);
					break;
				case point_Tag :
					decode_point(buf, value_1);
					break;
				default :
					Serializer.skipUnknownField(num_1, buf);
			}
		}
		value_1.Frame_size = buf.readerIndex() - f_Index;
		return value_1;
	}

	public void encode(ByteBuf buf) {
		if (hasName()) {
			this.encode_name(buf);
		}

		if (hasPoint()) {
			this.encode_point(buf);
		}

	}
	public static Frame decode(ByteBuf buf, int length_1) {
		Frame value_1 = new Frame();
		int f_Index = buf.readerIndex();
		while (buf.readerIndex() < f_Index + length_1) {
			int num_1 = Serializer.decodeVarInt32(buf);
			switch (num_1) {
				case name_Tag :
					decode_name(buf, value_1);
					break;
				case point_Tag :
					decode_point(buf, value_1);
					break;
				default :
					Serializer.skipUnknownField(num_1, buf);
			}
		}
		value_1.Frame_size = length_1;
		value_1.verify();
		return value_1;
	}

	public int getByteSize() {
		return this.Frame_size;
	}

	public static FrameBuild newBuilder() {
		return new FrameBuild();
	}

	private void verify() {
		if (this.name == null) {
			throw new RuntimeException("required name");
		}
		if (this.point == null) {
			throw new RuntimeException("required point");
		}
	}



	public static class FrameBuild {
		private String name;
		private Point point;

		public FrameBuild setName(String a) {
			this.name = a;
			return this;
		}

		public String getName() {
			return this.name;
		}

		public FrameBuild clearName() {
			this.name = null;
			return this;
		}

		public boolean hasName() {
			return this.name != null;
		}

		public FrameBuild setPoint(Point a) {
			this.point = a;
			return this;
		}

		public Point getPoint() {
			return this.point;
		}

		public FrameBuild clearPoint() {
			this.point = null;
			return this;
		}

		public boolean hasPoint() {
			return this.point != null;
		}

		public Frame build() {
			Frame value_1 = new Frame();
			if (this.name == null) {
				throw new RuntimeException(" name is required");
			}
			value_1.set_name(this.name);
			if (this.point == null) {
				throw new RuntimeException(" point is required");
			}
			value_1.set_point(this.point);
			return value_1;
		}
		public FrameBuild clear() {
			this.name = null;
			this.point = null;
			return this;
		}

		private FrameBuild() {
		}
	}

}

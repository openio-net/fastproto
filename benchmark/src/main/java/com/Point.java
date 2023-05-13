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
import io.netty.buffer.ByteBuf;

public final class Point {

	private int Point_size = 0;

	private Integer x;
	public final static int x_Num = 1;
	public final static int x_Tag = 8;// the value is num<<<3|wireType
	public final static int x_TagEncodeSize = 1;

	private void encode_x(ByteBuf buf) {
		Serializer.encodeVarInt32(buf, x_Tag);
		Serializer.encodeVarInt32(buf, this.x);
	}

	private static void decode_x(ByteBuf buf, Point a_1) {
		Integer value_1 = null;
		value_1 = Serializer.decodeVarInt32(buf);
		a_1.x = value_1;
	}

	private void set_x(Integer value_1) {
		Point_size += x_TagEncodeSize;
		int size_1 = 0;

		size_1 += Serializer.computeVarInt32Size(value_1);
		Point_size += size_1;
		this.x = value_1;
	}

	public Integer getX() {
		return this.x;
	}

	private boolean hasX() {
		return this.x != null;
	}

	private Integer y;
	public final static int y_Num = 2;
	public final static int y_Tag = 16;// the value is num<<<3|wireType
	public final static int y_TagEncodeSize = 1;

	private void encode_y(ByteBuf buf) {
		Serializer.encodeVarInt32(buf, y_Tag);
		Serializer.encodeVarInt32(buf, this.y);
	}

	private static void decode_y(ByteBuf buf, Point a_1) {
		Integer value_1 = null;
		value_1 = Serializer.decodeVarInt32(buf);
		a_1.y = value_1;
	}

	private void set_y(Integer value_1) {
		Point_size += y_TagEncodeSize;
		int size_1 = 0;

		size_1 += Serializer.computeVarInt32Size(value_1);
		Point_size += size_1;
		this.y = value_1;
	}

	public Integer getY() {
		return this.y;
	}

	private boolean hasY() {
		return this.y != null;
	}

	private Integer z;
	public final static int z_Num = 3;
	public final static int z_Tag = 24;// the value is num<<<3|wireType
	public final static int z_TagEncodeSize = 1;

	private void encode_z(ByteBuf buf) {
		Serializer.encodeVarInt32(buf, z_Tag);
		Serializer.encodeVarInt32(buf, this.z);
	}

	private static void decode_z(ByteBuf buf, Point a_1) {
		Integer value_1 = null;
		value_1 = Serializer.decodeVarInt32(buf);
		a_1.z = value_1;
	}

	private void set_z(Integer value_1) {
		Point_size += z_TagEncodeSize;
		int size_1 = 0;

		size_1 += Serializer.computeVarInt32Size(value_1);
		Point_size += size_1;
		this.z = value_1;
	}

	public Integer getZ() {
		return this.z;
	}

	private boolean hasZ() {
		return this.z != null;
	}

	public static Point decode(ByteBuf buf) {
		Point value_1 = new Point();
		int f_Index = buf.readerIndex();
		while (buf.readerIndex() < buf.writerIndex()) {
			int num_1 = Serializer.decodeVarInt32(buf);
			switch (num_1) {
				case x_Tag :
					decode_x(buf, value_1);
					break;
				case y_Tag :
					decode_y(buf, value_1);
					break;
				case z_Tag :
					decode_z(buf, value_1);
					break;
				default :
					Serializer.skipUnknownField(num_1, buf);
			}
		}
		value_1.Point_size = buf.readerIndex() - f_Index;
		return value_1;
	}

	public void encode(ByteBuf buf) {
		if (hasX()) {
			this.encode_x(buf);
		}

		if (hasY()) {
			this.encode_y(buf);
		}

		if (hasZ()) {
			this.encode_z(buf);
		}

	}
	public static Point decode(ByteBuf buf, int length_1) {
		Point value_1 = new Point();
		int f_Index = buf.readerIndex();
		while (buf.readerIndex() < f_Index + length_1) {
			int num_1 = Serializer.decodeVarInt32(buf);
			switch (num_1) {
				case x_Tag :
					decode_x(buf, value_1);
					break;
				case y_Tag :
					decode_y(buf, value_1);
					break;
				case z_Tag :
					decode_z(buf, value_1);
					break;
				default :
					Serializer.skipUnknownField(num_1, buf);
			}
		}
		value_1.Point_size = length_1;
		value_1.verify();
		return value_1;
	}

	public int getByteSize() {
		return this.Point_size;
	}

	public static PointBuild newBuilder() {
		return new PointBuild();
	}

	private void verify() {
		if (this.x == null) {
			throw new RuntimeException("required x");
		}
		if (this.y == null) {
			throw new RuntimeException("required y");
		}
	}

	public static class PointBuild {
		private Integer x;
		private Integer y;
		private Integer z;

		public PointBuild setX(Integer a) {
			this.x = a;
			return this;
		}

		public Integer getX() {
			return this.x;
		}

		public PointBuild clearX() {
			this.x = null;
			return this;
		}

		public boolean hasX() {
			return this.x != null;
		}

		public PointBuild setY(Integer a) {
			this.y = a;
			return this;
		}

		public Integer getY() {
			return this.y;
		}

		public PointBuild clearY() {
			this.y = null;
			return this;
		}

		public boolean hasY() {
			return this.y != null;
		}

		public PointBuild setZ(Integer a) {
			this.z = a;
			return this;
		}

		public Integer getZ() {
			return this.z;
		}

		public PointBuild clearZ() {
			this.z = null;
			return this;
		}

		public boolean hasZ() {
			return this.z != null;
		}

		public Point build() {
			Point value_1 = new Point();
			if (this.x == null) {
				throw new RuntimeException(" x is required");
			}
			value_1.set_x(this.x);
			if (this.y == null) {
				throw new RuntimeException(" y is required");
			}
			value_1.set_y(this.y);
			if (this.z != null) {
				value_1.set_z(this.z);
			}
			return value_1;
		}
		public PointBuild clear() {
			this.x = null;
			this.y = null;
			this.z = null;
			return this;
		}

		private PointBuild() {
		}


	}

}

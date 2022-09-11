package com;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;

public final class A {

	private int A_size = 0;

	private java.lang.Integer a;
	public final static int a_Num = 1;
	public final static int a_Tag = 8;// the value is num<<<3|wireType
	public final static int a_TagEncodeSize = 1;

	private void encode_a(ByteBuf buf) {
		Serializer.encodeVarInt32(buf, a_Tag);
		Serializer.encodeVarInt32(buf, this.a);
	}

	private static void decode_a(ByteBuf buf, A a_1) {
		java.lang.Integer value_1 = null;
		value_1 = Serializer.decodeVarInt32(buf);
		a_1.a = value_1;
	}

	private void set_a(java.lang.Integer value_1) {
		A_size += a_TagEncodeSize;
		int size_1 = 0;

		size_1 += Serializer.computeVarInt32Size(value_1);
		A_size += size_1;
		this.a = value_1;
	}

	public java.lang.Integer get_a() {
		return this.a;
	}

	private boolean has_a() {
		return this.a != null;
	}

	private java.util.List<java.lang.Integer> b;
	public final static int b_Num = 2;
	public final static int b_Tag = 18;// the value is num<<3|wireType
	public final static int b_TagEncodeSize = 1;
	private int b_Length = 0;

	private void set_b(java.util.List<java.lang.Integer> list_1) {
		this.b = new java.util.ArrayList<>(list_1.size());
		this.A_size += b_TagEncodeSize;// add tag length
		int length_1 = 0;
		for (java.lang.Integer value_1 : list_1) {
			length_1 += Serializer.computeVarInt32Size(value_1);
			this.b.add(value_1);
		}
		this.A_size += Serializer.computeVarInt32Size(length_1);// add length byte size
		this.A_size += length_1;
		this.b_Length = length_1;
	}
	public java.lang.Integer get_b_value(int index) {
		if (this.b == null) {
			return null;
		}

		return this.b.get(index);
	}

	public int get_b_size() {
		if (this.b == null) {
			return 0;
		}

		return this.b.size();
	}

	private static void decode_b(ByteBuf buf, A a_1) {
		if (a_1.b_Length != 0) {// has init
			a_1.b = new java.util.ArrayList<>();
		}
		a_1.b_Length = Serializer.decodeVarInt32(buf);
		int end_index = buf.readerIndex() + a_1.b_Length;
		while (buf.readerIndex() < end_index) {
			java.lang.Integer value_1 = null;
			value_1 = Serializer.decodeVarInt32(buf);
			a_1.add_b(value_1);
		}
	}

	private void encode_b(ByteBuf buf) {
		Serializer.encodeVarInt32(buf, b_Tag);
		Serializer.encodeVarInt32(buf, b_Length);
		for (java.lang.Integer value_1 : b) {
			Serializer.encodeVarInt32(buf, value_1);
		}
	}

	private void add_b(java.lang.Integer value) {
		if (this.b == null) {
			this.b = new java.util.ArrayList<>();
		}

		this.b.add(value);
	}

	public boolean has_b() {
		if (this.b == null) {
			return false;
		}
		return this.b.size() != 0;
	}

	public static A decode(ByteBuf buf) {
		A value_1 = new A();
		int f_Index = buf.readerIndex();
		while (buf.readerIndex() < buf.writerIndex()) {
			int num_1 = Serializer.decodeVarInt32(buf);
			switch (num_1) {
				case a_Tag :
					decode_a(buf, value_1);
					break;
				case b_Tag :
					decode_b(buf, value_1);
					break;
				default :
					Serializer.skipUnknownField(num_1, buf);
			}
		}
		value_1.A_size = buf.readerIndex() - f_Index;
		return value_1;
	}

	public void encode(ByteBuf buf) {
		if (has_a()) {
			this.encode_a(buf);
		}

		if (has_b()) {
			this.encode_b(buf);
		}

	}
	public static A decode(ByteBuf buf, int length_1) {
		A value_1 = new A();
		int f_Index = buf.readerIndex();
		while (buf.readerIndex() < f_Index + length_1) {
			int num_1 = Serializer.decodeVarInt32(buf);
			switch (num_1) {
				case a_Tag :
					decode_a(buf, value_1);
					break;
				case b_Tag :
					decode_b(buf, value_1);
					break;
				default :
					Serializer.skipUnknownField(num_1, buf);
			}
		}
		value_1.A_size = length_1;
		value_1.verify();
		return value_1;
	}

	public int getByteSize() {
		return this.A_size;
	}

	public static ABuild newBuilder() {
		return new ABuild();
	}

	private void verify() {
	}

	public static class ABuild {
		private java.lang.Integer a;
		private java.util.List<java.lang.Integer> b;

		public ABuild set_a(java.lang.Integer a) {
			this.a = a;
			return this;
		}

		public java.lang.Integer get_a() {
			return this.a;
		}

		public ABuild clear_a() {
			this.a = null;
			return this;
		}

		public boolean has_a() {
			return this.a != null;
		}

		public ABuild add_b_value(java.lang.Integer a) {
			if (a == null) {
				throw new RuntimeException("a is null");
			}
			if (this.b == null) {
				this.b = new java.util.ArrayList<>();
				this.b.add(a);
			} else {
				this.b.add(a);
			}
			return this;
		}

		public java.lang.Integer get_b_value(int index) {
			if (this.b == null || index >= this.b.size()) {
				throw new RuntimeException("b is null or index bigger than b size");
			}

			return this.b.get(index);
		}

		public ABuild remove_b_value(int index) {
			if (this.b == null || index >= this.b.size()) {
				throw new RuntimeException("b is null or index bigger than b size");
			}

			this.b.remove(index);
			return this;

		}

		public int size_b() {
			if (this.b == null) {
				throw new RuntimeException("b is null");
			}

			return this.b.size();
		}

		public ABuild clear_b() {
			this.b = null;
			return this;
		}

		public boolean has_b() {
			if (this.b == null) {
				return false;
			}
			return this.b.size() != 0;
		}

		public A build() {
			A value_1 = new A();
			if (this.a != null) {
				value_1.set_a(this.a);
			}
			if (this.has_b()) {
				value_1.set_b(this.b);
			}
			return value_1;
		}
		public ABuild clear() {
			this.a = null;
			this.b = null;
			return this;
		}

		private ABuild() {
		}
	}

}

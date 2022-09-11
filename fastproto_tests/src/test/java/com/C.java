package com;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;

public final class C {

	private int C_size = 0;

	private java.util.List<com.B> b;
	public final static int b_Num = 2;
	public final static int b_Tag = 18;// the value is num<<3|wireType
	public final static int b_TagEncodeSize = 1;

	private void set_b(java.util.List<com.B> list_1) {
		this.b = new java.util.ArrayList<>(list_1.size());
		this.C_size += b_TagEncodeSize * list_1.size();// add tag length
		for (com.B value_1 : list_1) {
			this.b.add(value_1);
			int length_1 = 0;
			length_1 += Serializer.computeVarInt32Size(value_1.getByteSize());
			length_1 += value_1.getByteSize();
			this.C_size += length_1;
		}
	}
	public com.B get_b_value(int index) {
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

	private static void decode_b(ByteBuf buf, C a_1) {
		com.B value_1 = null;
		value_1 = com.B.decode(buf, Serializer.decodeVarInt32(buf));
		a_1.add_b(value_1);
	}

	private void encode_b(ByteBuf buf) {
		for (com.B value_1 : b) {
			Serializer.encodeVarInt32(buf, b_Tag);
			Serializer.encodeVarInt32(buf, value_1.getByteSize());
			value_1.encode(buf);
		}
	}

	private void add_b(com.B value) {
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

	public static C decode(ByteBuf buf) {
		C value_1 = new C();
		int f_Index = buf.readerIndex();
		while (buf.readerIndex() < buf.writerIndex()) {
			int num_1 = Serializer.decodeVarInt32(buf);
			switch (num_1) {
				case b_Tag :
					decode_b(buf, value_1);
					break;
				default :
					Serializer.skipUnknownField(num_1, buf);
			}
		}
		value_1.C_size = buf.readerIndex() - f_Index;
		return value_1;
	}

	public void encode(ByteBuf buf) {
		if (has_b()) {
			this.encode_b(buf);
		}

	}
	public static C decode(ByteBuf buf, int length_1) {
		C value_1 = new C();
		int f_Index = buf.readerIndex();
		while (buf.readerIndex() < f_Index + length_1) {
			int num_1 = Serializer.decodeVarInt32(buf);
			switch (num_1) {
				case b_Tag :
					decode_b(buf, value_1);
					break;
				default :
					Serializer.skipUnknownField(num_1, buf);
			}
		}
		value_1.C_size = length_1;
		value_1.verify();
		return value_1;
	}

	public int getByteSize() {
		return this.C_size;
	}

	public static CBuild newBuilder() {
		return new CBuild();
	}

	private void verify() {
	}

	public static class CBuild {
		private java.util.List<com.B> b;

		public CBuild add_b_value(com.B a) {
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

		public com.B get_b_value(int index) {
			if (this.b == null || index >= this.b.size()) {
				throw new RuntimeException("b is null or index bigger than b size");
			}

			return this.b.get(index);
		}

		public CBuild remove_b_value(int index) {
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

		public CBuild clear_b() {
			this.b = null;
			return this;
		}

		public boolean has_b() {
			if (this.b == null) {
				return false;
			}
			return this.b.size() != 0;
		}

		public C build() {
			C value_1 = new C();
			if (this.has_b()) {
				value_1.set_b(this.b);
			}
			return value_1;
		}
		public CBuild clear() {
			this.b = null;
			return this;
		}

		private CBuild() {
		}
	}

}

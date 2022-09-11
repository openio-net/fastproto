package com;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;

public final class D {

	private int D_size = 0;

	private java.util.Map<java.lang.Integer, java.lang.Long> b;
	private java.util.Map<java.lang.Integer, java.lang.Integer> b_Length;
	public final static int b_Num = 2;
	public final static int b_Tag = 18;
	public final static int b_TagEncodeSize = 1;

	private void set_b(java.util.Map<java.lang.Integer, java.lang.Long> map) {
		if (map == null)
			return;
		b_Length = new java.util.HashMap<>(map.keySet().size());
		this.b = new java.util.HashMap<>(map.keySet().size());
		int key_Length1 = Serializer.computeVarInt32Size(8);// the number is key tag
		int value_Length1 = Serializer.computeVarInt32Size(16);// the number is value tag
		for (java.lang.Integer s_1 : map.keySet()) {
			int key_value_length = 0;
			key_value_length += key_Length1;// add the map value tag length
			// sum the key length
			key_value_length += Serializer.computeVarInt32Size(s_1);
			java.lang.Long value_1 = map.get(s_1);
			key_value_length += value_Length1;// add the map value tag length
			// sum the value length
			key_value_length += Serializer.computeVarInt64Size(value_1);
			b_Length.put(s_1, key_value_length);
			D_size += key_value_length;
			this.b.put(s_1, value_1);
			D_size += Serializer.computeVarInt64Size(key_value_length);
		}
		D_size += map.keySet().size() * b_TagEncodeSize;
	}

	public java.lang.Long get_b_value(java.lang.Integer key) {
		if (this.b == null) {
			return null;
		}

		return this.b.get(key);
	}

	public java.util.Set<java.lang.Integer> get_b_KeySet() {
		if (this.b == null) {
			return null;
		}

		return this.b.keySet();
	}

	public boolean has_b() {
		if (this.b == null) {
			return false;
		}
		return this.b.keySet().size() != 0;
	}

	private void encode_b(ByteBuf buf) {
		for (java.lang.Integer key_1 : b.keySet()) {
			Serializer.encodeVarInt32(buf, b_Tag);
			int length_1 = b_Length.get(key_1);
			Serializer.encodeVarInt32(buf, length_1);
			Serializer.encodeVarInt32(buf, 8);
			Serializer.encodeVarInt32(buf, key_1);
			Serializer.encodeVarInt32(buf, 16);
			java.lang.Long value_1 = b.get(key_1);
			Serializer.encodeVarInt64(buf, value_1);
		}
	}

	private static void decode_b(ByteBuf buf, D a_1) {
		int length_1 = Serializer.decodeVarInt32(buf);
		int end_Index = buf.readerIndex() + length_1;
		java.lang.Integer key = null;
		java.lang.Long value = null;
		int tag = Serializer.decodeVarInt32(buf);
		if (tag == 8) {
			key = Serializer.decodeVarInt32(buf);
		} else if (tag == 16) {
			value = Serializer.decodeVarInt64(buf);
		}
		tag = Serializer.decodeVarInt32(buf);
		if (tag == 8) {
			value = Serializer.decodeVarInt64(buf);
		} else if (tag == 16) {
			value = Serializer.decodeVarInt64(buf);
		}
		if (key == null || value == null) {
			throw new RuntimeException(" b decode is wrong");
		}
		a_1.put_b(key, value, length_1);
	}

	private void put_b(java.lang.Integer key, java.lang.Long value, int length) {
		if (this.b == null) {
			this.b = new java.util.HashMap<>();
			this.b_Length = new java.util.HashMap<>();
		}

		this.b_Length.put(key, length);
		this.b.put(key, value);
	}

	public static D decode(ByteBuf buf) {
		D value_1 = new D();
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
		value_1.D_size = buf.readerIndex() - f_Index;
		return value_1;
	}

	public void encode(ByteBuf buf) {
		if (has_b()) {
			this.encode_b(buf);
		}

	}
	public static D decode(ByteBuf buf, int length_1) {
		D value_1 = new D();
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
		value_1.D_size = length_1;
		value_1.verify();
		return value_1;
	}

	public int getByteSize() {
		return this.D_size;
	}

	public static DBuild newBuilder() {
		return new DBuild();
	}

	private void verify() {
	}

	public static class DBuild {

		private java.util.Map<java.lang.Integer, java.lang.Long> b;

		public DBuild put_b(java.lang.Integer key, java.lang.Long value) {
			if (key == null || value == null) {
				throw new RuntimeException("key or value is null");
			}
			if (this.b == null) {
				this.b = new java.util.HashMap<>();
			}

			this.b.put(key, value);
			return this;

		}

		public java.lang.Long get_b_value(java.lang.Integer key) {
			if (this.b == null) {
				throw new RuntimeException("b is null");
			}

			return this.b.get(key);
		}

		public java.util.Set<java.lang.Integer> get_b_KeySet() {
			if (this.b == null) {
				throw new RuntimeException("b is null");
			}

			return this.b.keySet();
		}

		public java.util.Set<java.lang.Integer> remove_b_value() {
			if (this.b == null) {
				throw new RuntimeException("b is null");
			}

			return this.b.keySet();
		}

		public DBuild clear_b() {
			this.b = null;
			return this;
		}

		public boolean has_b() {
			if (this.b == null) {
				return false;
			}
			return this.b.keySet().size() != 0;
		}

		public D build() {
			D value_1 = new D();
			if (this.has_b()) {
				value_1.set_b(this.b);
			}
			return value_1;
		}
		public DBuild clear() {
			this.b = null;
			return this;
		}

		private DBuild() {
		}
	}

}

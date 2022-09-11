package com;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;

public final class E {

	private int E_size = 0;

	private java.util.Map<java.lang.Integer, com.D> a;
	private java.util.Map<java.lang.Integer, java.lang.Integer> a_Length;
	public final static int a_Num = 1;
	public final static int a_Tag = 10;
	public final static int a_TagEncodeSize = 1;

	private void set_a(java.util.Map<java.lang.Integer, com.D> map) {
		if (map == null)
			return;
		a_Length = new java.util.HashMap<>(map.keySet().size());
		this.a = new java.util.HashMap<>(map.keySet().size());
		int key_Length1 = Serializer.computeVarInt32Size(8);// the number is key tag
		int value_Length1 = Serializer.computeVarInt32Size(18);// the number is value tag
		for (java.lang.Integer s_1 : map.keySet()) {
			int key_value_length = 0;
			key_value_length += key_Length1;// add the map value tag length
			// sum the key length
			key_value_length += Serializer.computeVarInt32Size(s_1);
			com.D value_1 = map.get(s_1);
			key_value_length += value_Length1;// add the map value tag length
			// sum the value length
			key_value_length += Serializer.computeVarInt32Size(value_1.getByteSize());
			key_value_length += value_1.getByteSize();
			a_Length.put(s_1, key_value_length);
			E_size += key_value_length;
			this.a.put(s_1, value_1);
			E_size += Serializer.computeVarInt64Size(key_value_length);
		}
		E_size += map.keySet().size() * a_TagEncodeSize;
	}

	public com.D get_a_value(java.lang.Integer key) {
		if (this.a == null) {
			return null;
		}

		return this.a.get(key);
	}

	public java.util.Set<java.lang.Integer> get_a_KeySet() {
		if (this.a == null) {
			return null;
		}

		return this.a.keySet();
	}

	public boolean has_a() {
		if (this.a == null) {
			return false;
		}
		return this.a.keySet().size() != 0;
	}

	private void encode_a(ByteBuf buf) {
		for (java.lang.Integer key_1 : a.keySet()) {
			Serializer.encodeVarInt32(buf, a_Tag);
			int length_1 = a_Length.get(key_1);
			Serializer.encodeVarInt32(buf, length_1);
			Serializer.encodeVarInt32(buf, 8);
			Serializer.encodeVarInt32(buf, key_1);
			Serializer.encodeVarInt32(buf, 18);
			com.D value_1 = a.get(key_1);
			Serializer.encodeVarInt32(buf, value_1.getByteSize());
			value_1.encode(buf);
		}
	}

	private static void decode_a(ByteBuf buf, E a_1) {
		int length_1 = Serializer.decodeVarInt32(buf);
		int end_Index = buf.readerIndex() + length_1;
		java.lang.Integer key = null;
		com.D value = null;
		int tag = Serializer.decodeVarInt32(buf);
		if (tag == 8) {
			key = Serializer.decodeVarInt32(buf);
		} else if (tag == 18) {
			value = com.D.decode(buf, Serializer.decodeVarInt32(buf));
		}
		tag = Serializer.decodeVarInt32(buf);
		if (tag == 8) {
			value = com.D.decode(buf, Serializer.decodeVarInt32(buf));
		} else if (tag == 18) {
			value = com.D.decode(buf, Serializer.decodeVarInt32(buf));
		}
		if (key == null || value == null) {
			throw new RuntimeException(" a decode is wrong");
		}
		a_1.put_a(key, value, length_1);
	}

	private void put_a(java.lang.Integer key, com.D value, int length) {
		if (this.a == null) {
			this.a = new java.util.HashMap<>();
			this.a_Length = new java.util.HashMap<>();
		}

		this.a_Length.put(key, length);
		this.a.put(key, value);
	}

	private java.util.List<com.B> b;
	public final static int b_Num = 2;
	public final static int b_Tag = 18;// the value is num<<3|wireType
	public final static int b_TagEncodeSize = 1;

	private void set_b(java.util.List<com.B> list_1) {
		this.b = new java.util.ArrayList<>(list_1.size());
		this.E_size += b_TagEncodeSize * list_1.size();// add tag length
		for (com.B value_1 : list_1) {
			this.b.add(value_1);
			int length_1 = 0;
			length_1 += Serializer.computeVarInt32Size(value_1.getByteSize());
			length_1 += value_1.getByteSize();
			this.E_size += length_1;
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

	private static void decode_b(ByteBuf buf, E a_1) {
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

	private int endOneOfNum_0 = -1;

	private java.lang.Integer c;
	public final static int c_Num = 3;
	public final static int c_Tag = 24;
	public final static int c_TagEncodeSize = 1;

	private java.lang.Long e;
	public final static int e_Num = 4;
	public final static int e_Tag = 32;
	public final static int e_TagEncodeSize = 1;

	private java.lang.Long f;
	public final static int f_Num = 5;
	public final static int f_Tag = 40;
	public final static int f_TagEncodeSize = 1;

	private void setOneOf0_c(java.lang.Integer value_1) {
		this.c = value_1;
		E_size += c_TagEncodeSize;
		endOneOfNum_0 = c_Num;
		E_size += Serializer.computeVarInt32Size(value_1);
	}
	private void setOneOf0_e(java.lang.Long value_1) {
		this.e = value_1;
		E_size += e_TagEncodeSize;
		endOneOfNum_0 = e_Num;
		E_size += Serializer.computeVarInt64Size(value_1);
	}
	private void setOneOf0_f(java.lang.Long value_1) {
		this.f = value_1;
		E_size += f_TagEncodeSize;
		endOneOfNum_0 = f_Num;
		E_size += Serializer.computeVarInt64Size(value_1);
	}

	public java.lang.Object getOneOf0Value() {
		if (endOneOfNum_0 == -1)
			return null;
		switch (endOneOfNum_0) {
			case c_Num :
				return this.c;
			case e_Num :
				return this.e;
			case f_Num :
				return this.f;
		}
		return null;
	}

	private void encodeOneOf_0(ByteBuf buf) {
		switch (endOneOfNum_0) {
			case c_Num :
				Serializer.encodeVarInt32(buf, c_Tag);
				Serializer.encodeVarInt32(buf, this.c);
				break;
			case e_Num :
				Serializer.encodeVarInt32(buf, e_Tag);
				Serializer.encodeVarInt64(buf, this.e);
				break;
			case f_Num :
				Serializer.encodeVarInt32(buf, f_Tag);
				Serializer.encodeVarInt64(buf, this.f);
				break;
		}
	}

	private static void decode_c(ByteBuf buf, E value_1) {
		if (value_1.endOneOfNum_0 != -1) {
			switch (value_1.endOneOfNum_0) {
				case c_Tag :
					value_1.c = null;
					break;
				case e_Tag :
					value_1.e = null;
					break;
				case f_Tag :
					value_1.f = null;
					break;
			}
		}
		value_1.endOneOfNum_0 = c_Num;
		java.lang.Integer value_2 = null;
		value_2 = Serializer.decodeVarInt32(buf);
		value_1.c = value_2;

	}

	private static void decode_e(ByteBuf buf, E value_1) {
		if (value_1.endOneOfNum_0 != -1) {
			switch (value_1.endOneOfNum_0) {
				case c_Tag :
					value_1.c = null;
					break;
				case e_Tag :
					value_1.e = null;
					break;
				case f_Tag :
					value_1.f = null;
					break;
			}
		}
		value_1.endOneOfNum_0 = e_Num;
		java.lang.Long value_2 = null;
		value_2 = Serializer.decodeVarInt64(buf);
		value_1.e = value_2;

	}

	private static void decode_f(ByteBuf buf, E value_1) {
		if (value_1.endOneOfNum_0 != -1) {
			switch (value_1.endOneOfNum_0) {
				case c_Tag :
					value_1.c = null;
					break;
				case e_Tag :
					value_1.e = null;
					break;
				case f_Tag :
					value_1.f = null;
					break;
			}
		}
		value_1.endOneOfNum_0 = f_Num;
		java.lang.Long value_2 = null;
		value_2 = Serializer.decodeVarInt64(buf);
		value_1.f = value_2;

	}

	public boolean hasOneOf0() {
		return endOneOfNum_0 != -1;
	}

	public static E decode(ByteBuf buf) {
		E value_1 = new E();
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
				case c_Tag :
					decode_c(buf, value_1);
					break;
				case e_Tag :
					decode_e(buf, value_1);
					break;
				case f_Tag :
					decode_f(buf, value_1);
					break;
				default :
					Serializer.skipUnknownField(num_1, buf);
			}
		}
		value_1.E_size = buf.readerIndex() - f_Index;
		return value_1;
	}

	public void encode(ByteBuf buf) {
		if (has_a()) {
			this.encode_a(buf);
		}

		if (has_b()) {
			this.encode_b(buf);
		}

		if (hasOneOf0()) {
			encodeOneOf_0(buf);
		}
	}
	public static E decode(ByteBuf buf, int length_1) {
		E value_1 = new E();
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
				case c_Tag :
					decode_c(buf, value_1);
					break;
				case e_Tag :
					decode_e(buf, value_1);
					break;
				case f_Tag :
					decode_f(buf, value_1);
					break;
				default :
					Serializer.skipUnknownField(num_1, buf);
			}
		}
		value_1.E_size = length_1;
		value_1.verify();
		return value_1;
	}

	public int getByteSize() {
		return this.E_size;
	}

	public static EBuild newBuilder() {
		return new EBuild();
	}

	private void verify() {
	}

	public static class EBuild {

		private java.util.Map<java.lang.Integer, com.D> a;

		private java.util.List<com.B> b;
		private final static int oneOfIndex0 = 0;

		// OneOfIndex为0的最后设置的值的Num
		private int endSet0Num = -1;
		private java.lang.Integer c;

		private java.lang.Long e;

		private java.lang.Long f;

		public EBuild put_a(java.lang.Integer key, com.D value) {
			if (key == null || value == null) {
				throw new RuntimeException("key or value is null");
			}
			if (this.a == null) {
				this.a = new java.util.HashMap<>();
			}

			this.a.put(key, value);
			return this;

		}

		public com.D get_a_value(java.lang.Integer key) {
			if (this.a == null) {
				throw new RuntimeException("a is null");
			}

			return this.a.get(key);
		}

		public java.util.Set<java.lang.Integer> get_a_KeySet() {
			if (this.a == null) {
				throw new RuntimeException("a is null");
			}

			return this.a.keySet();
		}

		public java.util.Set<java.lang.Integer> remove_a_value() {
			if (this.a == null) {
				throw new RuntimeException("a is null");
			}

			return this.a.keySet();
		}

		public EBuild clear_a() {
			this.a = null;
			return this;
		}

		public boolean has_a() {
			if (this.a == null) {
				return false;
			}
			return this.a.keySet().size() != 0;
		}

		public EBuild add_b_value(com.B a) {
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

		public EBuild remove_b_value(int index) {
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

		public EBuild clear_b() {
			this.b = null;
			return this;
		}

		public boolean has_b() {
			if (this.b == null) {
				return false;
			}
			return this.b.size() != 0;
		}

		public EBuild setOneOf0Valuec(java.lang.Integer a) {
			if (a == null) {
				endSet0Num = -1;
				return this;
			}
			endSet0Num = 3;
			this.c = a;
			return this;
		}

		public EBuild setOneOf0Valuee(java.lang.Long a) {
			if (a == null) {
				endSet0Num = -1;
				return this;
			}
			endSet0Num = 4;
			this.e = a;
			return this;
		}

		public EBuild setOneOf0Valuef(java.lang.Long a) {
			if (a == null) {
				endSet0Num = -1;
				return this;
			}
			endSet0Num = 5;
			this.f = a;
			return this;
		}

		public java.lang.Object getOneOf0Value() {
			if (endSet0Num == -1)
				return null;
			switch (endSet0Num) {
				case 3 :
					return this.c;
				case 4 :
					return this.e;
				case 5 :
					return this.f;
			}
			return null;
		}

		public EBuild clearOneOf0() {
			this.c = null;
			this.e = null;
			this.f = null;
			endSet0Num = -1;
			return this;
		}

		public boolean hasOneOf0() {
			return endSet0Num != -1;
		}

		public E build() {
			E value_1 = new E();
			if (this.has_a()) {
				value_1.set_a(this.a);
			}
			if (this.has_b()) {
				value_1.set_b(this.b);
			}
			if (this.endSet0Num == 3) {
				value_1.setOneOf0_c(this.c);
			}
			if (this.endSet0Num == 4) {
				value_1.setOneOf0_e(this.e);
			}
			if (this.endSet0Num == 5) {
				value_1.setOneOf0_f(this.f);
			}
			return value_1;
		}
		public EBuild clear() {
			this.a = null;
			this.b = null;
			endSet0Num = -1;
			this.c = null;
			return this;
		}

		private EBuild() {
		}
	}

}

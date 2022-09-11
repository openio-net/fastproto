package com;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;

public final class O {

	private int O_size = 0;

	private java.util.List<java.lang.Long> h;
	public final static int h_Num = 6;
	public final static int h_Tag = 48;// the value is num<<3|wireType
	public final static int h_TagEncodeSize = 1;

	private void set_h(java.util.List<java.lang.Long> list_1) {
		this.h = new java.util.ArrayList<>(list_1.size());
		this.O_size += h_TagEncodeSize * list_1.size();// add tag length
		for (java.lang.Long value_1 : list_1) {
			this.h.add(value_1);
			int length_1 = 0;
			length_1 += Serializer.computeVarInt64Size(value_1);
			this.O_size += length_1;
		}
	}
	public java.lang.Long get_h_value(int index) {
		if (this.h == null) {
			return null;
		}

		return this.h.get(index);
	}

	public int get_h_size() {
		if (this.h == null) {
			return 0;
		}

		return this.h.size();
	}

	private static void decode_h(ByteBuf buf, O a_1) {
		java.lang.Long value_1 = null;
		value_1 = Serializer.decodeVarInt64(buf);
		a_1.add_h(value_1);
	}

	private void encode_h(ByteBuf buf) {
		for (java.lang.Long value_1 : h) {
			Serializer.encodeVarInt32(buf, h_Tag);
			Serializer.encodeVarInt64(buf, value_1);
		}
	}

	private void add_h(java.lang.Long value) {
		if (this.h == null) {
			this.h = new java.util.ArrayList<>();
		}

		this.h.add(value);
	}

	public boolean has_h() {
		if (this.h == null) {
			return false;
		}
		return this.h.size() != 0;
	}

	private java.util.List<java.lang.Integer> i;
	public final static int i_Num = 7;
	public final static int i_Tag = 56;// the value is num<<3|wireType
	public final static int i_TagEncodeSize = 1;

	private void set_i(java.util.List<java.lang.Integer> list_1) {
		this.i = new java.util.ArrayList<>(list_1.size());
		this.O_size += i_TagEncodeSize * list_1.size();// add tag length
		for (java.lang.Integer value_1 : list_1) {
			this.i.add(value_1);
			int length_1 = 0;
			length_1 += Serializer.computeVarUInt32Size(value_1);
			this.O_size += length_1;
		}
	}
	public java.lang.Integer get_i_value(int index) {
		if (this.i == null) {
			return null;
		}

		return this.i.get(index);
	}

	public int get_i_size() {
		if (this.i == null) {
			return 0;
		}

		return this.i.size();
	}

	private static void decode_i(ByteBuf buf, O a_1) {
		java.lang.Integer value_1 = null;
		value_1 = Serializer.decodeVarInt32(buf);
		a_1.add_i(value_1);
	}

	private void encode_i(ByteBuf buf) {
		for (java.lang.Integer value_1 : i) {
			Serializer.encodeVarInt32(buf, i_Tag);
			Serializer.encodeVarUInt32(buf, value_1);
		}
	}

	private void add_i(java.lang.Integer value) {
		if (this.i == null) {
			this.i = new java.util.ArrayList<>();
		}

		this.i.add(value);
	}

	public boolean has_i() {
		if (this.i == null) {
			return false;
		}
		return this.i.size() != 0;
	}

	private java.util.List<java.lang.Integer> j;
	public final static int j_Num = 8;
	public final static int j_Tag = 64;// the value is num<<3|wireType
	public final static int j_TagEncodeSize = 1;

	private void set_j(java.util.List<java.lang.Integer> list_1) {
		this.j = new java.util.ArrayList<>(list_1.size());
		this.O_size += j_TagEncodeSize * list_1.size();// add tag length
		for (java.lang.Integer value_1 : list_1) {
			this.j.add(value_1);
			int length_1 = 0;
			length_1 += Serializer.computeVarUInt32Size(Serializer.encodeZigzag32(value_1));
			this.O_size += length_1;
		}
	}
	public java.lang.Integer get_j_value(int index) {
		if (this.j == null) {
			return null;
		}

		return this.j.get(index);
	}

	public int get_j_size() {
		if (this.j == null) {
			return 0;
		}

		return this.j.size();
	}

	private static void decode_j(ByteBuf buf, O a_1) {
		java.lang.Integer value_1 = null;
		value_1 = Serializer.decodeZigzag32(Serializer.decodeVarInt32(buf));
		a_1.add_j(value_1);
	}

	private void encode_j(ByteBuf buf) {
		for (java.lang.Integer value_1 : j) {
			Serializer.encodeVarInt32(buf, j_Tag);
			Serializer.encodeVarUInt32(buf, Serializer.encodeZigzag32(value_1));
		}
	}

	private void add_j(java.lang.Integer value) {
		if (this.j == null) {
			this.j = new java.util.ArrayList<>();
		}

		this.j.add(value);
	}

	public boolean has_j() {
		if (this.j == null) {
			return false;
		}
		return this.j.size() != 0;
	}

	private java.util.List<java.lang.Long> k;
	public final static int k_Num = 9;
	public final static int k_Tag = 72;// the value is num<<3|wireType
	public final static int k_TagEncodeSize = 1;

	private void set_k(java.util.List<java.lang.Long> list_1) {
		this.k = new java.util.ArrayList<>(list_1.size());
		this.O_size += k_TagEncodeSize * list_1.size();// add tag length
		for (java.lang.Long value_1 : list_1) {
			this.k.add(value_1);
			int length_1 = 0;
			length_1 += Serializer.computeVarInt64Size(Serializer.encodeZigzag64(value_1));
			this.O_size += length_1;
		}
	}
	public java.lang.Long get_k_value(int index) {
		if (this.k == null) {
			return null;
		}

		return this.k.get(index);
	}

	public int get_k_size() {
		if (this.k == null) {
			return 0;
		}

		return this.k.size();
	}

	private static void decode_k(ByteBuf buf, O a_1) {
		java.lang.Long value_1 = null;
		value_1 = Serializer.decodeZigzag64(Serializer.decodeVarInt64(buf));
		a_1.add_k(value_1);
	}

	private void encode_k(ByteBuf buf) {
		for (java.lang.Long value_1 : k) {
			Serializer.encodeVarInt32(buf, k_Tag);
			Serializer.encodeVarInt64(buf, Serializer.encodeZigzag64(value_1));
		}
	}

	private void add_k(java.lang.Long value) {
		if (this.k == null) {
			this.k = new java.util.ArrayList<>();
		}

		this.k.add(value);
	}

	public boolean has_k() {
		if (this.k == null) {
			return false;
		}
		return this.k.size() != 0;
	}

	private java.util.List<java.lang.Boolean> l;
	public final static int l_Num = 10;
	public final static int l_Tag = 80;// the value is num<<3|wireType
	public final static int l_TagEncodeSize = 1;

	private void set_l(java.util.List<java.lang.Boolean> list_1) {
		this.l = new java.util.ArrayList<>(list_1.size());
		this.O_size += l_TagEncodeSize * list_1.size();// add tag length
		for (java.lang.Boolean value_1 : list_1) {
			this.l.add(value_1);
			int length_1 = 0;
			length_1 += 1;
			this.O_size += length_1;
		}
	}
	public java.lang.Boolean get_l_value(int index) {
		if (this.l == null) {
			return null;
		}

		return this.l.get(index);
	}

	public int get_l_size() {
		if (this.l == null) {
			return 0;
		}

		return this.l.size();
	}

	private static void decode_l(ByteBuf buf, O a_1) {
		java.lang.Boolean value_1 = null;
		value_1 = Serializer.decodeBoolean(buf);
		a_1.add_l(value_1);
	}

	private void encode_l(ByteBuf buf) {
		for (java.lang.Boolean value_1 : l) {
			Serializer.encodeVarInt32(buf, l_Tag);
			Serializer.encodeBoolean(buf, value_1);
		}
	}

	private void add_l(java.lang.Boolean value) {
		if (this.l == null) {
			this.l = new java.util.ArrayList<>();
		}

		this.l.add(value);
	}

	public boolean has_l() {
		if (this.l == null) {
			return false;
		}
		return this.l.size() != 0;
	}

	public static O decode(ByteBuf buf) {
		O value_1 = new O();
		int f_Index = buf.readerIndex();
		while (buf.readerIndex() < buf.writerIndex()) {
			int num_1 = Serializer.decodeVarInt32(buf);
			switch (num_1) {
				case h_Tag :
					decode_h(buf, value_1);
					break;
				case i_Tag :
					decode_i(buf, value_1);
					break;
				case j_Tag :
					decode_j(buf, value_1);
					break;
				case k_Tag :
					decode_k(buf, value_1);
					break;
				case l_Tag :
					decode_l(buf, value_1);
					break;
				default :
					Serializer.skipUnknownField(num_1, buf);
			}
		}
		value_1.O_size = buf.readerIndex() - f_Index;
		return value_1;
	}

	public void encode(ByteBuf buf) {
		if (has_h()) {
			this.encode_h(buf);
		}

		if (has_i()) {
			this.encode_i(buf);
		}

		if (has_j()) {
			this.encode_j(buf);
		}

		if (has_k()) {
			this.encode_k(buf);
		}

		if (has_l()) {
			this.encode_l(buf);
		}

	}
	public static O decode(ByteBuf buf, int length_1) {
		O value_1 = new O();
		int f_Index = buf.readerIndex();
		while (buf.readerIndex() < f_Index + length_1) {
			int num_1 = Serializer.decodeVarInt32(buf);
			switch (num_1) {
				case h_Tag :
					decode_h(buf, value_1);
					break;
				case i_Tag :
					decode_i(buf, value_1);
					break;
				case j_Tag :
					decode_j(buf, value_1);
					break;
				case k_Tag :
					decode_k(buf, value_1);
					break;
				case l_Tag :
					decode_l(buf, value_1);
					break;
				default :
					Serializer.skipUnknownField(num_1, buf);
			}
		}
		value_1.O_size = length_1;
		value_1.verify();
		return value_1;
	}

	public int getByteSize() {
		return this.O_size;
	}

	public static OBuild newBuilder() {
		return new OBuild();
	}

	private void verify() {
	}

	public static class OBuild {
		private java.util.List<java.lang.Long> h;
		private java.util.List<java.lang.Integer> i;
		private java.util.List<java.lang.Integer> j;
		private java.util.List<java.lang.Long> k;
		private java.util.List<java.lang.Boolean> l;

		public OBuild add_h_value(java.lang.Long a) {
			if (a == null) {
				throw new RuntimeException("a is null");
			}
			if (this.h == null) {
				this.h = new java.util.ArrayList<>();
				this.h.add(a);
			} else {
				this.h.add(a);
			}
			return this;
		}

		public java.lang.Long get_h_value(int index) {
			if (this.h == null || index >= this.h.size()) {
				throw new RuntimeException("h is null or index bigger than h size");
			}

			return this.h.get(index);
		}

		public OBuild remove_h_value(int index) {
			if (this.h == null || index >= this.h.size()) {
				throw new RuntimeException("h is null or index bigger than h size");
			}

			this.h.remove(index);
			return this;

		}

		public int size_h() {
			if (this.h == null) {
				throw new RuntimeException("h is null");
			}

			return this.h.size();
		}

		public OBuild clear_h() {
			this.h = null;
			return this;
		}

		public boolean has_h() {
			if (this.h == null) {
				return false;
			}
			return this.h.size() != 0;
		}

		public OBuild add_i_value(java.lang.Integer a) {
			if (a == null) {
				throw new RuntimeException("a is null");
			}
			if (this.i == null) {
				this.i = new java.util.ArrayList<>();
				this.i.add(a);
			} else {
				this.i.add(a);
			}
			return this;
		}

		public java.lang.Integer get_i_value(int index) {
			if (this.i == null || index >= this.i.size()) {
				throw new RuntimeException("i is null or index bigger than i size");
			}

			return this.i.get(index);
		}

		public OBuild remove_i_value(int index) {
			if (this.i == null || index >= this.i.size()) {
				throw new RuntimeException("i is null or index bigger than i size");
			}

			this.i.remove(index);
			return this;

		}

		public int size_i() {
			if (this.i == null) {
				throw new RuntimeException("i is null");
			}

			return this.i.size();
		}

		public OBuild clear_i() {
			this.i = null;
			return this;
		}

		public boolean has_i() {
			if (this.i == null) {
				return false;
			}
			return this.i.size() != 0;
		}

		public OBuild add_j_value(java.lang.Integer a) {
			if (a == null) {
				throw new RuntimeException("a is null");
			}
			if (this.j == null) {
				this.j = new java.util.ArrayList<>();
				this.j.add(a);
			} else {
				this.j.add(a);
			}
			return this;
		}

		public java.lang.Integer get_j_value(int index) {
			if (this.j == null || index >= this.j.size()) {
				throw new RuntimeException("j is null or index bigger than j size");
			}

			return this.j.get(index);
		}

		public OBuild remove_j_value(int index) {
			if (this.j == null || index >= this.j.size()) {
				throw new RuntimeException("j is null or index bigger than j size");
			}

			this.j.remove(index);
			return this;

		}

		public int size_j() {
			if (this.j == null) {
				throw new RuntimeException("j is null");
			}

			return this.j.size();
		}

		public OBuild clear_j() {
			this.j = null;
			return this;
		}

		public boolean has_j() {
			if (this.j == null) {
				return false;
			}
			return this.j.size() != 0;
		}

		public OBuild add_k_value(java.lang.Long a) {
			if (a == null) {
				throw new RuntimeException("a is null");
			}
			if (this.k == null) {
				this.k = new java.util.ArrayList<>();
				this.k.add(a);
			} else {
				this.k.add(a);
			}
			return this;
		}

		public java.lang.Long get_k_value(int index) {
			if (this.k == null || index >= this.k.size()) {
				throw new RuntimeException("k is null or index bigger than k size");
			}

			return this.k.get(index);
		}

		public OBuild remove_k_value(int index) {
			if (this.k == null || index >= this.k.size()) {
				throw new RuntimeException("k is null or index bigger than k size");
			}

			this.k.remove(index);
			return this;

		}

		public int size_k() {
			if (this.k == null) {
				throw new RuntimeException("k is null");
			}

			return this.k.size();
		}

		public OBuild clear_k() {
			this.k = null;
			return this;
		}

		public boolean has_k() {
			if (this.k == null) {
				return false;
			}
			return this.k.size() != 0;
		}

		public OBuild add_l_value(java.lang.Boolean a) {
			if (a == null) {
				throw new RuntimeException("a is null");
			}
			if (this.l == null) {
				this.l = new java.util.ArrayList<>();
				this.l.add(a);
			} else {
				this.l.add(a);
			}
			return this;
		}

		public java.lang.Boolean get_l_value(int index) {
			if (this.l == null || index >= this.l.size()) {
				throw new RuntimeException("l is null or index bigger than l size");
			}

			return this.l.get(index);
		}

		public OBuild remove_l_value(int index) {
			if (this.l == null || index >= this.l.size()) {
				throw new RuntimeException("l is null or index bigger than l size");
			}

			this.l.remove(index);
			return this;

		}

		public int size_l() {
			if (this.l == null) {
				throw new RuntimeException("l is null");
			}

			return this.l.size();
		}

		public OBuild clear_l() {
			this.l = null;
			return this;
		}

		public boolean has_l() {
			if (this.l == null) {
				return false;
			}
			return this.l.size() != 0;
		}

		public O build() {
			O value_1 = new O();
			if (this.has_h()) {
				value_1.set_h(this.h);
			}
			if (this.has_i()) {
				value_1.set_i(this.i);
			}
			if (this.has_j()) {
				value_1.set_j(this.j);
			}
			if (this.has_k()) {
				value_1.set_k(this.k);
			}
			if (this.has_l()) {
				value_1.set_l(this.l);
			}
			return value_1;
		}
		public OBuild clear() {
			this.h = null;
			this.i = null;
			this.j = null;
			this.k = null;
			this.l = null;
			return this;
		}

		private OBuild() {
		}
	}

}

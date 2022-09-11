package com;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;

public final class L {

	private int L_size = 0;

	private java.util.List<java.lang.Long> h;
	public final static int h_Num = 6;
	public final static int h_Tag = 48;// the value is num<<3|wireType
	public final static int h_TagEncodeSize = 1;

	private void set_h(java.util.List<java.lang.Long> list_1) {
		this.h = new java.util.ArrayList<>(list_1.size());
		this.L_size += h_TagEncodeSize * list_1.size();// add tag length
		for (java.lang.Long value_1 : list_1) {
			this.h.add(value_1);
			int length_1 = 0;
			length_1 += Serializer.computeVarInt64Size(value_1);
			this.L_size += length_1;
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

	private static void decode_h(ByteBuf buf, L a_1) {
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
	public final static int i_Tag = 61;// the value is num<<3|wireType
	public final static int i_TagEncodeSize = 1;

	private void set_i(java.util.List<java.lang.Integer> list_1) {
		this.i = new java.util.ArrayList<>(list_1.size());
		this.L_size += i_TagEncodeSize * list_1.size();// add tag length
		for (java.lang.Integer value_1 : list_1) {
			this.i.add(value_1);
			int length_1 = 0;
			length_1 += 4;
			this.L_size += length_1;
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

	private static void decode_i(ByteBuf buf, L a_1) {
		java.lang.Integer value_1 = null;
		value_1 = Serializer.decode32(buf);
		a_1.add_i(value_1);
	}

	private void encode_i(ByteBuf buf) {
		for (java.lang.Integer value_1 : i) {
			Serializer.encodeVarInt32(buf, i_Tag);
			Serializer.encode32(buf, value_1);
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

	private java.util.List<java.lang.Long> j;
	public final static int j_Num = 8;
	public final static int j_Tag = 65;// the value is num<<3|wireType
	public final static int j_TagEncodeSize = 1;

	private void set_j(java.util.List<java.lang.Long> list_1) {
		this.j = new java.util.ArrayList<>(list_1.size());
		this.L_size += j_TagEncodeSize * list_1.size();// add tag length
		for (java.lang.Long value_1 : list_1) {
			this.j.add(value_1);
			int length_1 = 0;
			length_1 += 8;
			this.L_size += length_1;
		}
	}
	public java.lang.Long get_j_value(int index) {
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

	private static void decode_j(ByteBuf buf, L a_1) {
		java.lang.Long value_1 = null;
		value_1 = Serializer.decode64(buf);
		a_1.add_j(value_1);
	}

	private void encode_j(ByteBuf buf) {
		for (java.lang.Long value_1 : j) {
			Serializer.encodeVarInt32(buf, j_Tag);
			Serializer.encode64(buf, value_1);
		}
	}

	private void add_j(java.lang.Long value) {
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

	private java.util.List<java.lang.String> k;
	public final static int k_Num = 9;
	public final static int k_Tag = 74;// the value is num<<3|wireType
	public final static int k_TagEncodeSize = 1;

	private void set_k(java.util.List<java.lang.String> list_1) {
		this.k = new java.util.ArrayList<>(list_1.size());
		this.L_size += k_TagEncodeSize * list_1.size();// add tag length
		for (java.lang.String value_1 : list_1) {
			this.k.add(value_1);
			int length_1 = 0;
			length_1 += Serializer.computeVarInt32Size(ByteBufUtil.utf8Bytes(value_1));
			length_1 += ByteBufUtil.utf8Bytes(value_1);// value length
			this.L_size += length_1;
		}
	}
	public java.lang.String get_k_value(int index) {
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

	private static void decode_k(ByteBuf buf, L a_1) {
		java.lang.String value_1 = null;
		value_1 = Serializer.decodeString(buf, Serializer.decodeVarInt32(buf));
		a_1.add_k(value_1);
	}

	private void encode_k(ByteBuf buf) {
		for (java.lang.String value_1 : k) {
			Serializer.encodeVarInt32(buf, k_Tag);
			Serializer.encodeString(buf, value_1);
		}
	}

	private void add_k(java.lang.String value) {
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

	private java.util.List<byte[]> l;
	public final static int l_Num = 10;
	public final static int l_Tag = 82;// the value is num<<3|wireType
	public final static int l_TagEncodeSize = 1;

	private void set_l(java.util.List<byte[]> list_1) {
		this.l = new java.util.ArrayList<>(list_1.size());
		this.L_size += l_TagEncodeSize * list_1.size();// add tag length
		for (byte[] value_1 : list_1) {
			this.l.add(value_1);
			int length_1 = 0;
			length_1 += Serializer.computeVarInt32Size(value_1.length);
			length_1 += value_1.length;
			this.L_size += length_1;
		}
	}
	public byte[] get_l_value(int index) {
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

	private static void decode_l(ByteBuf buf, L a_1) {
		byte[] value_1 = null;
		value_1 = Serializer.decodeByteString(buf, Serializer.decodeVarInt32(buf));
		a_1.add_l(value_1);
	}

	private void encode_l(ByteBuf buf) {
		for (byte[] value_1 : l) {
			Serializer.encodeVarInt32(buf, l_Tag);
			Serializer.encodeByteString(buf, value_1);
		}
	}

	private void add_l(byte[] value) {
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

	public static L decode(ByteBuf buf) {
		L value_1 = new L();
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
		value_1.L_size = buf.readerIndex() - f_Index;
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
	public static L decode(ByteBuf buf, int length_1) {
		L value_1 = new L();
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
		value_1.L_size = length_1;
		value_1.verify();
		return value_1;
	}

	public int getByteSize() {
		return this.L_size;
	}

	public static LBuild newBuilder() {
		return new LBuild();
	}

	private void verify() {
	}

	public static class LBuild {
		private java.util.List<java.lang.Long> h;
		private java.util.List<java.lang.Integer> i;
		private java.util.List<java.lang.Long> j;
		private java.util.List<java.lang.String> k;
		private java.util.List<byte[]> l;

		public LBuild add_h_value(java.lang.Long a) {
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

		public LBuild remove_h_value(int index) {
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

		public LBuild clear_h() {
			this.h = null;
			return this;
		}

		public boolean has_h() {
			if (this.h == null) {
				return false;
			}
			return this.h.size() != 0;
		}

		public LBuild add_i_value(java.lang.Integer a) {
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

		public LBuild remove_i_value(int index) {
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

		public LBuild clear_i() {
			this.i = null;
			return this;
		}

		public boolean has_i() {
			if (this.i == null) {
				return false;
			}
			return this.i.size() != 0;
		}

		public LBuild add_j_value(java.lang.Long a) {
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

		public java.lang.Long get_j_value(int index) {
			if (this.j == null || index >= this.j.size()) {
				throw new RuntimeException("j is null or index bigger than j size");
			}

			return this.j.get(index);
		}

		public LBuild remove_j_value(int index) {
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

		public LBuild clear_j() {
			this.j = null;
			return this;
		}

		public boolean has_j() {
			if (this.j == null) {
				return false;
			}
			return this.j.size() != 0;
		}

		public LBuild add_k_value(java.lang.String a) {
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

		public java.lang.String get_k_value(int index) {
			if (this.k == null || index >= this.k.size()) {
				throw new RuntimeException("k is null or index bigger than k size");
			}

			return this.k.get(index);
		}

		public LBuild remove_k_value(int index) {
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

		public LBuild clear_k() {
			this.k = null;
			return this;
		}

		public boolean has_k() {
			if (this.k == null) {
				return false;
			}
			return this.k.size() != 0;
		}

		public LBuild add_l_value(byte[] a) {
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

		public byte[] get_l_value(int index) {
			if (this.l == null || index >= this.l.size()) {
				throw new RuntimeException("l is null or index bigger than l size");
			}

			return this.l.get(index);
		}

		public LBuild remove_l_value(int index) {
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

		public LBuild clear_l() {
			this.l = null;
			return this;
		}

		public boolean has_l() {
			if (this.l == null) {
				return false;
			}
			return this.l.size() != 0;
		}

		public L build() {
			L value_1 = new L();
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
		public LBuild clear() {
			this.h = null;
			this.i = null;
			this.j = null;
			this.k = null;
			this.l = null;
			return this;
		}

		private LBuild() {
		}
	}

}

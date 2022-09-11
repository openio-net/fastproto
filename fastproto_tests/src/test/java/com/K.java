package com;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;

public final class K {

	private int K_size = 0;

	private java.lang.Long h;
	public final static int h_Num = 6;
	public final static int h_Tag = 48;// the value is num<<<3|wireType
	public final static int h_TagEncodeSize = 1;

	private void encode_h(ByteBuf buf) {
		Serializer.encodeVarInt32(buf, h_Tag);
		Serializer.encodeVarInt64(buf, this.h);
	}

	private static void decode_h(ByteBuf buf, K a_1) {
		java.lang.Long value_1 = null;
		value_1 = Serializer.decodeVarInt64(buf);
		a_1.h = value_1;
	}

	private void set_h(java.lang.Long value_1) {
		K_size += h_TagEncodeSize;
		int size_1 = 0;

		size_1 += Serializer.computeVarInt64Size(value_1);
		K_size += size_1;
		this.h = value_1;
	}

	public java.lang.Long get_h() {
		return this.h;
	}

	private boolean has_h() {
		return this.h != null;
	}

	private java.lang.Integer i;
	public final static int i_Num = 7;
	public final static int i_Tag = 56;// the value is num<<<3|wireType
	public final static int i_TagEncodeSize = 1;

	private void encode_i(ByteBuf buf) {
		Serializer.encodeVarInt32(buf, i_Tag);
		Serializer.encodeVarUInt32(buf, this.i);
	}

	private static void decode_i(ByteBuf buf, K a_1) {
		java.lang.Integer value_1 = null;
		value_1 = Serializer.decodeVarInt32(buf);
		a_1.i = value_1;
	}

	private void set_i(java.lang.Integer value_1) {
		K_size += i_TagEncodeSize;
		int size_1 = 0;

		size_1 += Serializer.computeVarUInt32Size(value_1);
		K_size += size_1;
		this.i = value_1;
	}

	public java.lang.Integer get_i() {
		return this.i;
	}

	private boolean has_i() {
		return this.i != null;
	}

	private java.lang.Integer j;
	public final static int j_Num = 8;
	public final static int j_Tag = 64;// the value is num<<<3|wireType
	public final static int j_TagEncodeSize = 1;

	private void encode_j(ByteBuf buf) {
		Serializer.encodeVarInt32(buf, j_Tag);
		Serializer.encodeVarUInt32(buf, Serializer.encodeZigzag32(this.j));
	}

	private static void decode_j(ByteBuf buf, K a_1) {
		java.lang.Integer value_1 = null;
		value_1 = Serializer.decodeZigzag32(Serializer.decodeVarInt32(buf));
		a_1.j = value_1;
	}

	private void set_j(java.lang.Integer value_1) {
		K_size += j_TagEncodeSize;
		int size_1 = 0;

		size_1 += Serializer.computeVarUInt32Size(Serializer.encodeZigzag32(value_1));
		K_size += size_1;
		this.j = value_1;
	}

	public java.lang.Integer get_j() {
		return this.j;
	}

	private boolean has_j() {
		return this.j != null;
	}

	private java.lang.Long k;
	public final static int k_Num = 9;
	public final static int k_Tag = 72;// the value is num<<<3|wireType
	public final static int k_TagEncodeSize = 1;

	private void encode_k(ByteBuf buf) {
		Serializer.encodeVarInt32(buf, k_Tag);
		Serializer.encodeVarInt64(buf, Serializer.encodeZigzag64(this.k));
	}

	private static void decode_k(ByteBuf buf, K a_1) {
		java.lang.Long value_1 = null;
		value_1 = Serializer.decodeZigzag64(Serializer.decodeVarInt64(buf));
		a_1.k = value_1;
	}

	private void set_k(java.lang.Long value_1) {
		K_size += k_TagEncodeSize;
		int size_1 = 0;

		size_1 += Serializer.computeVarInt64Size(Serializer.encodeZigzag64(value_1));
		K_size += size_1;
		this.k = value_1;
	}

	public java.lang.Long get_k() {
		return this.k;
	}

	private boolean has_k() {
		return this.k != null;
	}

	private java.lang.Boolean l;
	public final static int l_Num = 10;
	public final static int l_Tag = 80;// the value is num<<<3|wireType
	public final static int l_TagEncodeSize = 1;

	private void encode_l(ByteBuf buf) {
		Serializer.encodeVarInt32(buf, l_Tag);
		Serializer.encodeBoolean(buf, this.l);
	}

	private static void decode_l(ByteBuf buf, K a_1) {
		java.lang.Boolean value_1 = null;
		value_1 = Serializer.decodeBoolean(buf);
		a_1.l = value_1;
	}

	private void set_l(java.lang.Boolean value_1) {
		K_size += l_TagEncodeSize;
		int size_1 = 0;

		size_1 += 1;
		K_size += size_1;
		this.l = value_1;
	}

	public java.lang.Boolean get_l() {
		return this.l;
	}

	private boolean has_l() {
		return this.l != null;
	}

	public static K decode(ByteBuf buf) {
		K value_1 = new K();
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
		value_1.K_size = buf.readerIndex() - f_Index;
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
	public static K decode(ByteBuf buf, int length_1) {
		K value_1 = new K();
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
		value_1.K_size = length_1;
		value_1.verify();
		return value_1;
	}

	public int getByteSize() {
		return this.K_size;
	}

	public static KBuild newBuilder() {
		return new KBuild();
	}

	private void verify() {
	}

	public static class KBuild {
		private java.lang.Long h;
		private java.lang.Integer i;
		private java.lang.Integer j;
		private java.lang.Long k;
		private java.lang.Boolean l;

		public KBuild set_h(java.lang.Long a) {
			this.h = a;
			return this;
		}

		public java.lang.Long get_h() {
			return this.h;
		}

		public KBuild clear_h() {
			this.h = null;
			return this;
		}

		public boolean has_h() {
			return this.h != null;
		}

		public KBuild set_i(java.lang.Integer a) {
			this.i = a;
			return this;
		}

		public java.lang.Integer get_i() {
			return this.i;
		}

		public KBuild clear_i() {
			this.i = null;
			return this;
		}

		public boolean has_i() {
			return this.i != null;
		}

		public KBuild set_j(java.lang.Integer a) {
			this.j = a;
			return this;
		}

		public java.lang.Integer get_j() {
			return this.j;
		}

		public KBuild clear_j() {
			this.j = null;
			return this;
		}

		public boolean has_j() {
			return this.j != null;
		}

		public KBuild set_k(java.lang.Long a) {
			this.k = a;
			return this;
		}

		public java.lang.Long get_k() {
			return this.k;
		}

		public KBuild clear_k() {
			this.k = null;
			return this;
		}

		public boolean has_k() {
			return this.k != null;
		}

		public KBuild set_l(java.lang.Boolean a) {
			this.l = a;
			return this;
		}

		public java.lang.Boolean get_l() {
			return this.l;
		}

		public KBuild clear_l() {
			this.l = null;
			return this;
		}

		public boolean has_l() {
			return this.l != null;
		}

		public K build() {
			K value_1 = new K();
			if (this.h != null) {
				value_1.set_h(this.h);
			}
			if (this.i != null) {
				value_1.set_i(this.i);
			}
			if (this.j != null) {
				value_1.set_j(this.j);
			}
			if (this.k != null) {
				value_1.set_k(this.k);
			}
			if (this.l != null) {
				value_1.set_l(this.l);
			}
			return value_1;
		}
		public KBuild clear() {
			this.h = null;
			this.i = null;
			this.j = null;
			this.k = null;
			this.l = null;
			return this;
		}

		private KBuild() {
		}
	}

}

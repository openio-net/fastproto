package com;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;

public final class J {

	private int J_size = 0;

	private java.lang.Long h;
	public final static int h_Num = 6;
	public final static int h_Tag = 48;// the value is num<<<3|wireType
	public final static int h_TagEncodeSize = 1;

	private void encode_h(ByteBuf buf) {
		Serializer.encodeVarInt32(buf, h_Tag);
		Serializer.encodeVarInt64(buf, this.h);
	}

	private static void decode_h(ByteBuf buf, J a_1) {
		java.lang.Long value_1 = null;
		value_1 = Serializer.decodeVarInt64(buf);
		a_1.h = value_1;
	}

	private void set_h(java.lang.Long value_1) {
		J_size += h_TagEncodeSize;
		int size_1 = 0;

		size_1 += Serializer.computeVarInt64Size(value_1);
		J_size += size_1;
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
	public final static int i_Tag = 61;// the value is num<<<3|wireType
	public final static int i_TagEncodeSize = 1;

	private void encode_i(ByteBuf buf) {
		Serializer.encodeVarInt32(buf, i_Tag);
		Serializer.encode32(buf, this.i);
	}

	private static void decode_i(ByteBuf buf, J a_1) {
		java.lang.Integer value_1 = null;
		value_1 = Serializer.decode32(buf);
		a_1.i = value_1;
	}

	private void set_i(java.lang.Integer value_1) {
		J_size += i_TagEncodeSize;
		int size_1 = 0;

		size_1 += 4;
		J_size += size_1;
		this.i = value_1;
	}

	public java.lang.Integer get_i() {
		return this.i;
	}

	private boolean has_i() {
		return this.i != null;
	}

	private java.lang.Long j;
	public final static int j_Num = 8;
	public final static int j_Tag = 65;// the value is num<<<3|wireType
	public final static int j_TagEncodeSize = 1;

	private void encode_j(ByteBuf buf) {
		Serializer.encodeVarInt32(buf, j_Tag);
		Serializer.encode64(buf, this.j);
	}

	private static void decode_j(ByteBuf buf, J a_1) {
		java.lang.Long value_1 = null;
		value_1 = Serializer.decode64(buf);
		a_1.j = value_1;
	}

	private void set_j(java.lang.Long value_1) {
		J_size += j_TagEncodeSize;
		int size_1 = 0;

		size_1 += 8;
		J_size += size_1;
		this.j = value_1;
	}

	public java.lang.Long get_j() {
		return this.j;
	}

	private boolean has_j() {
		return this.j != null;
	}

	private java.lang.String k;
	public final static int k_Num = 9;
	public final static int k_Tag = 74;// the value is num<<<3|wireType
	public final static int k_TagEncodeSize = 1;

	private void encode_k(ByteBuf buf) {
		Serializer.encodeVarInt32(buf, k_Tag);
		Serializer.encodeString(buf, this.k);
	}

	private static void decode_k(ByteBuf buf, J a_1) {
		java.lang.String value_1 = null;
		value_1 = Serializer.decodeString(buf, Serializer.decodeVarInt32(buf));
		a_1.k = value_1;
	}

	private void set_k(java.lang.String value_1) {
		J_size += k_TagEncodeSize;
		int size_1 = 0;

		size_1 += Serializer.computeVarInt32Size(ByteBufUtil.utf8Bytes(value_1));
		size_1 += ByteBufUtil.utf8Bytes(value_1);// value length
		J_size += size_1;
		this.k = value_1;
	}

	public java.lang.String get_k() {
		return this.k;
	}

	private boolean has_k() {
		return this.k != null;
	}

	private byte[] l;
	public final static int l_Num = 10;
	public final static int l_Tag = 82;// the value is num<<<3|wireType
	public final static int l_TagEncodeSize = 1;

	private void encode_l(ByteBuf buf) {
		Serializer.encodeVarInt32(buf, l_Tag);
		Serializer.encodeByteString(buf, this.l);
	}

	private static void decode_l(ByteBuf buf, J a_1) {
		byte[] value_1 = null;
		value_1 = Serializer.decodeByteString(buf, Serializer.decodeVarInt32(buf));
		a_1.l = value_1;
	}

	private void set_l(byte[] value_1) {
		J_size += l_TagEncodeSize;
		int size_1 = 0;

		size_1 += Serializer.computeVarInt32Size(value_1.length);
		size_1 += value_1.length;
		J_size += size_1;
		this.l = value_1;
	}

	public byte[] get_l() {
		return this.l;
	}

	private boolean has_l() {
		return this.l != null;
	}

	public static J decode(ByteBuf buf) {
		J value_1 = new J();
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
		value_1.J_size = buf.readerIndex() - f_Index;
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
	public static J decode(ByteBuf buf, int length_1) {
		J value_1 = new J();
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
		value_1.J_size = length_1;
		value_1.verify();
		return value_1;
	}

	public int getByteSize() {
		return this.J_size;
	}

	public static JBuild newBuilder() {
		return new JBuild();
	}

	private void verify() {
	}

	public static class JBuild {
		private java.lang.Long h;
		private java.lang.Integer i;
		private java.lang.Long j;
		private java.lang.String k;
		private byte[] l;

		public JBuild set_h(java.lang.Long a) {
			this.h = a;
			return this;
		}

		public java.lang.Long get_h() {
			return this.h;
		}

		public JBuild clear_h() {
			this.h = null;
			return this;
		}

		public boolean has_h() {
			return this.h != null;
		}

		public JBuild set_i(java.lang.Integer a) {
			this.i = a;
			return this;
		}

		public java.lang.Integer get_i() {
			return this.i;
		}

		public JBuild clear_i() {
			this.i = null;
			return this;
		}

		public boolean has_i() {
			return this.i != null;
		}

		public JBuild set_j(java.lang.Long a) {
			this.j = a;
			return this;
		}

		public java.lang.Long get_j() {
			return this.j;
		}

		public JBuild clear_j() {
			this.j = null;
			return this;
		}

		public boolean has_j() {
			return this.j != null;
		}

		public JBuild set_k(java.lang.String a) {
			this.k = a;
			return this;
		}

		public java.lang.String get_k() {
			return this.k;
		}

		public JBuild clear_k() {
			this.k = null;
			return this;
		}

		public boolean has_k() {
			return this.k != null;
		}

		public JBuild set_l(byte[] a) {
			this.l = a;
			return this;
		}

		public byte[] get_l() {
			return this.l;
		}

		public JBuild clear_l() {
			this.l = null;
			return this;
		}

		public boolean has_l() {
			return this.l != null;
		}

		public J build() {
			J value_1 = new J();
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
		public JBuild clear() {
			this.h = null;
			this.i = null;
			this.j = null;
			this.k = null;
			this.l = null;
			return this;
		}

		private JBuild() {
		}
	}

}

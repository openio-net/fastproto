package com;
public enum F {

	a(0),

	b(2),

	c(3),

	;
	public static F get(int tag) {
		if (tag == 0) {
			return F.a;
		}
		if (tag == 2) {
			return F.b;
		}
		if (tag == 3) {
			return F.c;
		}
		return null;
	}
	int num;

	F(int num) {
		this.num = num;
	}

	int getNum() {
		return this.num;
	}
}

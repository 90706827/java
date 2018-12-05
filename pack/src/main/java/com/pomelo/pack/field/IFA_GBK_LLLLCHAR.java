package com.pomelo.pack.field;

import org.jpos.iso.AsciiPrefixer;

public class IFA_GBK_LLLLCHAR extends ISOStringFieldPackager {
	public IFA_GBK_LLLLCHAR() {
		super(EncodingLiteralInterpreter
				.getInterpreter("GBK"), AsciiPrefixer.LLLL);
	}

	public IFA_GBK_LLLLCHAR(int len, String description) {
		super(len, description, EncodingLiteralInterpreter
				.getInterpreter("GBK"), AsciiPrefixer.LLLL);
		checkLength(len, 9999);
	}

	@Override
	public void setLength(int len) {
		checkLength(len, 9999);
		super.setLength(len);
	}
}

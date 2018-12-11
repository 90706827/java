

package com.example.demo.book2.mtpattern.ch7.pc.exmaple;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class CaseRunner {

	public static void main(String[] args) {
		AttachmentProcessor ap = new AttachmentProcessor();
		ap.init();
		InputStream in = new ByteArrayInputStream("Hello".getBytes());
		try {
			ap.saveAttachment(in, "000887282", "测试文档");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}

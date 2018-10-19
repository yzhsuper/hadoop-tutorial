package com.tutorial.hadoop;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.jupiter.api.Test;

class StringTokenizerTest {

	public static String readToString(String fileName) {
		String encoding = "UTF-8";
		File file = new File(fileName);
		Long filelength = file.length();
		byte[] filecontent = new byte[filelength.intValue()];
		try {
			FileInputStream in = new FileInputStream(file);
			in.read(filecontent);
			in.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			return new String(filecontent, encoding);
		} catch (UnsupportedEncodingException e) {
			System.err.println("The OS does not support " + encoding);
			e.printStackTrace();
			return null;
		}
	}

	@Test
	void testStringTokenizerString() {
		String line = readToString("order.txt");
//		Pattern p1 = Pattern.compile("\"(.*?)\"");
		
		String[] c = line.split("\"");
		System.out.println(c[3].trim());
//		Pattern p1 = Pattern.compile("(?<=\\\")(\\w+?)(?=\\\")");
//		Matcher m = p1.matcher(line.substring(0, 35));
//		while (m.find()) {
//			System.out.println(m.group());
//		}
//		StringTokenizer st = new StringTokenizer(line);
//		System.out.println(line);
//        while (st.hasMoreTokens()) {
//			System.out.println(st.nextToken());
//		}
	}

}

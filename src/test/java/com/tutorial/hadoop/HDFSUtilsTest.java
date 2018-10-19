package com.tutorial.hadoop;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import com.tutorial.hadoop.hdfs.HDFSUtils;

class HDFSUtilsTest {

	@Test
	void testDeleteFile() {
		try {
			HDFSUtils.deleteFile("/output/wordcount");
		} catch (IllegalArgumentException | IOException e) {
			e.printStackTrace();
		}
	}

}

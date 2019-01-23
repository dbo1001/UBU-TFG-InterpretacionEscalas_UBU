package io.csv;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

public class CSVControl {
	
	public void test () throws IOException {
		String csvPath = "ioData/test.csv";
		FileWriter writer = new FileWriter(csvPath);
		
		CSVUtil.writeLine(writer, Arrays.asList("aaa","bbb", "cc,c"));
		writer.flush();
		writer.close();
	}

}

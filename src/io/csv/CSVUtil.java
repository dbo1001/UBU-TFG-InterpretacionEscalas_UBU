package io.csv;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

public class CSVUtil {
	
	private static final char DEFAULT_SEPARATOR = ',';
	
	protected static void writeLine(Writer writer, List<String> content) throws IOException {
		writeLine(writer, content, DEFAULT_SEPARATOR);
	}
	
	private static String followCSVformat(String str) {
		String result = str;
		
		if(result.contains("\"")) result = result.replace("\"", "\"\"");
		
		return result;
	}
	
	protected static void writeLine(Writer writer, List<String> content, char separators) throws IOException {
		
		boolean first = true;
		
		if(separators == ' ') {
			separators = DEFAULT_SEPARATOR;
		}
		
		StringBuilder sb = new StringBuilder();
		
		for(String str: content) {
			
			if(!first) sb.append(separators);
			sb.append(followCSVformat(str));
			first = false;
			
		}
		
		sb.append("\n");
		writer.append(sb.toString());
	}

}

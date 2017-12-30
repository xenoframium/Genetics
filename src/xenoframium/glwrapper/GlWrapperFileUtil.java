package xenoframium.glwrapper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;

class GlWrapperFileUtil {
	public static File getClasspathFile(String path) throws IOException {
		byte[] byteBuffer = new byte[8192];
		InputStream fileReader = GlWrapperFileUtil.class.getResourceAsStream(path);

		File file = File.createTempFile(String.valueOf(fileReader.hashCode()), null);
		FileOutputStream fileWriter = new FileOutputStream(file);
		file.deleteOnExit();

		int bytesRead = fileReader.read(byteBuffer);
		while (bytesRead != -1) {
			fileWriter.write(byteBuffer, 0, bytesRead);
			bytesRead = fileReader.read(byteBuffer);
		}

		fileReader.close();
		fileWriter.close();

		return file;
	}
	
	public static String readFile(File file) throws IOException {
		StringBuilder builder = new StringBuilder();
		BufferedReader fileReader = new BufferedReader(new FileReader(file));

		String line = fileReader.readLine();
		while (line != null) {
			builder.append(line);
			builder.append("\n");
			line = fileReader.readLine();
		}

		fileReader.close();

		return builder.toString();
	}
}

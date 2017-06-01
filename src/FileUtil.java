import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class FileUtil {

	public ArrayList<String> listFileNamesInFolder(final File folder) {
		ArrayList<String> fileNamesInFolder = new ArrayList<>();
		for (final File fileEntry : folder.listFiles()) {
			if (fileEntry.isDirectory()) {
				listFileNamesInFolder(fileEntry);
			} else {
				fileNamesInFolder.add(fileEntry.getName());
			}
		}
		return fileNamesInFolder;
	}

	/*
	 * this method writes to the file at the argumented path. 
	 * If the file already exists, it appends.
	 * If the file doesn't exist, it creates a new one
	 */
	@SuppressWarnings("resource")
	public static void writeToFile(String content, String path, String fname) throws IOException {
		BufferedWriter bw = null;
		FileWriter fw = null;
		File file = new File(path + "/" + fname);

		// if file doesnt exists, then create it
		if (!file.exists()) {
			file.createNewFile();
		}

		// true = append file
		fw = new FileWriter(file.getAbsoluteFile(), true);
		bw = new BufferedWriter(fw);

		bw.write(content);

		System.out.println("Done writing to file : " + fname);

		bw.close();
		fw.close();
	}

}

import java.io.File;
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

}

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

class app {

	public static void main(String[] args) throws ClassNotFoundException {

		Preprocessing preprocess = new Preprocessing(true, false);
		RuleLearnDataset rldObj = new RuleLearnDataset();
		ArrayList<DataInstance> modalLearnDataset = new ArrayList<>();
		FileUtil futil = new FileUtil();
		String folname = "/Users/Shubham/Documents/workspace/ILP/SerializedObjects/unsplitDataset";
		File folder = new File(folname);
		for (String fname : futil.listFileNamesInFolder(folder)) {
			try {
				FileInputStream fileIn = new FileInputStream(folname + "/" + fname);
				ObjectInputStream in = new ObjectInputStream(fileIn);
				ArrayList<DataInstance> data_instances_temp = (ArrayList<DataInstance>) in.readObject();
				modalLearnDataset.addAll(rldObj.generateModalConceptDataset("possible", data_instances_temp));
				in.close();
				fileIn.close();
			} catch (IOException i) {
				i.printStackTrace();
				return;
			}
		}

		preprocess.makeModalILPFile("possible", modalLearnDataset);

		//		for (DataInstance d : modalLearnDataset) {
		//			System.out.println(d.sentence);
		//		}

		// Preprocessing preprocess = new Preprocessing();
		// FileUtil futil = new FileUtil();
		//
		//		preprocess.readAMR("/Users/Shubham/Documents/workspace/ILP/amr_anno_1.0/data/unsplit",
		//				"amr-release-1.0-bolt.txt");
		// preprocess.readAMR("/Users/Shubham/Documents/workspace/ILP/amr_anno_1.0/data",
		// "debugData");
		// preprocess.makeILPFile();

		// File folder = new File(Constants.ontoNotesFramesFolderPath);
		// ArrayList<String> fileNames = futil.listFileNamesInFolder(folder);

		// for (int i = 0; i < fileNames.size(); i++) {
		// fileNames.set(i, fileNames.get(i).split("-")[0]);
		// }

		/*
		 *    This code block generates the serialized objects of dataset ArrayList<DataInstance> and stores in the SerializedObjects folder 
		 */
		/*Preprocessing preprocess = new Preprocessing();
		FileUtil futil = new FileUtil();
		String folname = "/Users/Shubham/Documents/workspace/ILP/amr_anno_1.0/data/unsplit";
		File folder = new File(folname);
		for (String fname : futil.listFileNamesInFolder(folder)) {
			preprocess.readAMR(folname, fname);
		
			try {
				FileOutputStream fileOut = new FileOutputStream(Constants.serializedPath + "/" + fname + ".ser");
				ObjectOutputStream out = new ObjectOutputStream(fileOut);
				out.writeObject(preprocess.data_instances);
				out.close();
				fileOut.close();
			} catch (IOException i) {
				i.printStackTrace();
			}
			System.out.println("here!!!!!");
			try {
				FileInputStream fileIn = new FileInputStream(Constants.serializedPath + "/" + fname + ".ser");
				ObjectInputStream in = new ObjectInputStream(fileIn);
				ArrayList<DataInstance> data_instances_temp = (ArrayList<DataInstance>) in.readObject();
				Preprocessing.printParseTree(data_instances_temp.get(0).root);
				in.close();
				fileIn.close();
			} catch (IOException i) {
				i.printStackTrace();
				return;
			}
			preprocess.data_instances.clear();
		}*/

		/*File folder = new File(Constants.trainingDataPath);
		ArrayList<String> fileNames = listFilesForFolder(folder);
		
		for (String fileName : fileNames) {
			preprocess.readAMR(Constants.trainingDataPath, fileName);
		}*/

	}

}

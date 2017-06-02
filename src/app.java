import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

class app {

	public static void main(String[] args) throws ClassNotFoundException, IOException, InterruptedException {

		StanfordUtil su = new StanfordUtil(true, false);
		System.out.println(su.lemmatize("nonexecutive"));
		System.out.println(su.getPOS(("So, doesn't Ma Ying-jeou want his name to go down in history?")));

		Align algn = new Align();
		String folname = "/Users/Shubham/Documents/workspace/ILP/SerializedObjects/unsplitDataset";
		try {
			FileInputStream fileIn = new FileInputStream(folname + "/" + "amr-release-1.0-consensus.txt.ser");
			ObjectInputStream in = new ObjectInputStream(fileIn);
			ArrayList<DataInstance> data_instances_temp = (ArrayList<DataInstance>) in.readObject();

			for (DataInstance d : data_instances_temp) {
				algn.assignPositionsToGraph(d.root);
				HashMap<String, Integer> alignments = algn.align(d);
				for (String key : alignments.keySet())
					System.out.print(alignments.get(key) + "-" + key + " ");
				System.out.println("\n");
			}
			in.close();
			fileIn.close();
		} catch (IOException i) {
			i.printStackTrace();
			return;
		}
		//		Catvar catvar = new Catvar();
		//		catvar.analyzeCatvar();
		/*Preprocessing preprocess = new Preprocessing();
		FileUtil futil = new FileUtil();
		String folname = "/Users/Shubham/Documents/workspace/ILP/SerializedObjects/unsplitDataset";
		File folder = new File(folname);
		int ct = 0;
		int fnum = 0;
		for (String fname : futil.listFileNamesInFolder(folder)) {
			fnum++;
			try {
				FileInputStream fileIn = new FileInputStream(folname + "/" + fname);
				ObjectInputStream in = new ObjectInputStream(fileIn);
				ArrayList<DataInstance> data_instances_temp = (ArrayList<DataInstance>) in.readObject();
		
				for (DataInstance d : data_instances_temp) {
					ArrayList<String> concepts = new ArrayList<>();
					preprocess.getConcepts(d.root, concepts);
					if (concepts.contains("amr") && !su.lemmatize(d.getSentence()).contains("?")) {
						System.out.println(fnum + " " + d.getSentence());
						System.out.println(su.getPOS(d.getSentence()));
						ct++;
					}
				}
				System.out.println(ct);
				in.close();
				fileIn.close();
			} catch (IOException i) {
				i.printStackTrace();
				return;
			}
		}*/
		//		makeModalILPFile();
		//		makeModalILPFile("possible");

		//		Abstract.learnRules(Constants.ilpPath, modalConcept + ".lp");
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

		//		serializeDataset();

		/*File folder = new File(Constants.trainingDataPath);
		ArrayList<String> fileNames = listFilesForFolder(folder);
		
		for (String fileName : fileNames) {
			preprocess.readAMR(Constants.trainingDataPath, fileName);
		}*/

	}

	private static void serializeDataset() throws ClassNotFoundException {
		/*
		 *    This code block generates the serialized objects of dataset ArrayList<DataInstance> and stores in the SerializedObjects folder 
		 */
		Preprocessing preprocess = new Preprocessing();
		FileUtil futil = new FileUtil();
		String folname = "/Users/Shubham/Documents/workspace/ILP/amr_anno_1.0/data/unsplit";
		File folder = new File(folname);
		for (String fname : futil.listFileNamesInFolder(folder)) {
			preprocess.readAMR(folname, fname);

			try {
				FileOutputStream fileOut = new FileOutputStream(
						Constants.serializedPath + "/unsplitDataset/" + fname + ".ser");
				ObjectOutputStream out = new ObjectOutputStream(fileOut);
				out.writeObject(preprocess.data_instances);
				out.close();
				fileOut.close();
			} catch (IOException i) {
				i.printStackTrace();
			}
			System.out.println("here!!!!!");
			try {
				FileInputStream fileIn = new FileInputStream(
						Constants.serializedPath + "/unsplitDataset/" + fname + ".ser");
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
		}
	}

	private static void makeModalILPFile() throws ClassNotFoundException, IOException {
		Preprocessing preprocess = new Preprocessing(true, false);
		RuleLearnDataset rldObj = new RuleLearnDataset();
		ArrayList<Entry<DataInstance, ArrayList<String>>> modalLearnDataset = new ArrayList<>();
		FileUtil futil = new FileUtil();
		String folname = "/Users/Shubham/Documents/workspace/ILP/SerializedObjects/unsplitDataset";
		File folder = new File(folname);
		for (String fname : futil.listFileNamesInFolder(folder)) {
			try {
				FileInputStream fileIn = new FileInputStream(folname + "/" + fname);
				ObjectInputStream in = new ObjectInputStream(fileIn);
				ArrayList<DataInstance> data_instances_temp = (ArrayList<DataInstance>) in.readObject();
				modalLearnDataset.addAll(rldObj.generateModalConceptDataset(data_instances_temp));
				in.close();
				fileIn.close();
			} catch (IOException i) {
				i.printStackTrace();
				return;
			}
		}

		preprocess.makeModalILPFile(modalLearnDataset);
	}

	private static void makeModalILPFile(String modalConcept) throws ClassNotFoundException, IOException {
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
				modalLearnDataset.addAll(rldObj.generateModalConceptDataset(modalConcept, data_instances_temp));
				in.close();
				fileIn.close();
			} catch (IOException i) {
				i.printStackTrace();
				return;
			}
		}

		preprocess.makeModalILPFile(modalConcept, modalLearnDataset);
	}

}

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Catvar {
	private Process process;
	private StanfordUtil su;

	public Catvar() {
		su = new StanfordUtil(true, false);
	}

	/*
	 * the input word has to be lowercase
	 */
	public ArrayList<String> getCatWords(String word) throws IOException, InterruptedException {
		//		System.out.println("here");
		ArrayList<String> clusterWords = new ArrayList<>();
		int count = 0;
		word = word.toLowerCase();
		process = Runtime.getRuntime().exec(Constants.catVarPath + " " + word);
		//		System.out.println("here1");
		process.waitFor();
		//		System.out.println("here2");

		BufferedReader br = null;
		FileReader fr = null;

		try {

			fr = new FileReader(Constants.catVarClustersPath + "/" + word + ".txt");
			br = new BufferedReader(fr);

			String line;

			br = new BufferedReader(new FileReader(Constants.catVarClustersPath + "/" + word + ".txt"));

			while ((line = br.readLine()) != null) {
				if (line.startsWith("Subtotal ="))
					break;
				if (!line.startsWith("---") && !line.startsWith("CATVAR File:") && line.length() != 0) {
					String[] parts = line.split("\\s+");
					clusterWords.add(parts[0]);
				}
			}

		} catch (IOException e) {

			e.printStackTrace();

		} finally {

			try {

				if (br != null)
					br.close();

				if (fr != null)
					fr.close();

			} catch (IOException ex) {

				ex.printStackTrace();

			}

		}
		return clusterWords;

	}

	/*
	 * this method is just to manually analyze catvar
	 */
	public void analyzeCatvar() throws ClassNotFoundException, InterruptedException {
		Preprocessing preprocess = new Preprocessing(true, false);
		FileUtil futil = new FileUtil();
		String folname = "/Users/Shubham/Documents/workspace/ILP/SerializedObjects/unsplitDataset";
		File folder = new File(folname);
		for (String fname : futil.listFileNamesInFolder(folder)) {
			try {
				FileInputStream fileIn = new FileInputStream(folname + "/" + fname);
				ObjectInputStream in = new ObjectInputStream(fileIn);
				ArrayList<DataInstance> data_instances_temp = (ArrayList<DataInstance>) in.readObject();
				for (DataInstance d : data_instances_temp) {
					ArrayList<String> concepts = new ArrayList<>();
					preprocess.getConcepts(d.getRoot(), concepts);
					concepts = (ArrayList<String>) preprocess.cleanList(concepts);
					System.out.println(d.getSentence());
					System.out.println("Concepts: " + concepts);
					List<String> words = su.lemmatize(d.sentence);
					words = (ArrayList<String>) preprocess.cleanList(words);
					for (String c : concepts) {
						if (c.equals("im"))
							continue;
						//						getCatWords(c);
						ArrayList<String> cluster = getCatWords(c);
						System.out.println(c + ": " + cluster);
						for (String s : words) {
							if (cluster.contains(s)) {
								System.out.println("Match: " + s);
							}
						}
					}
					Scanner sc = new Scanner(System.in);
					sc.next();
				}
				in.close();
				fileIn.close();
			} catch (IOException i) {
				i.printStackTrace();
				return;
			}
		}
	}

}
